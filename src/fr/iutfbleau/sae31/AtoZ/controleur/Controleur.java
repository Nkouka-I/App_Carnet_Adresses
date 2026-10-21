package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.model.Contact;
import fr.iutfbleau.sae31.AtoZ.model.ContactDAO;
import fr.iutfbleau.sae31.AtoZ.model.Etiquette;
import fr.iutfbleau.sae31.AtoZ.model.EtiquetteDAO;
import fr.iutfbleau.sae31.AtoZ.model.ListeDiffusion;
import fr.iutfbleau.sae31.AtoZ.model.ListeDiffusionDAO;
import fr.iutfbleau.sae31.AtoZ.model.ModeleException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;

/**
 * La classe <code>Controleur</code> fait le lien entre la vue et le modèle. Elle présente à la
 * vue les contacts, les étiquettes et les listes de diffusion du {@link Carnet} (la copie en
 * mémoire des données de la base) et exécute les modifications demandées par la vue grâce aux
 * classes d'accès aux données.
 * <p>
 * Après chaque modification réussie, les données sont relues dans la base puis les écouteurs
 * enregistrés avec {@link #ajouterEcouteur(Runnable)} sont prévenus. Les erreurs (donnée
 * invalide, base inaccessible) ne sont jamais renvoyées à la vue : elles sont affichées dans
 * une boîte de dialogue.
 *
 * @version 1.1
 * @author Tiago
 */
public class Controleur {

    /**
     * Accès aux étiquettes dans la base de données.
     */
    private final EtiquetteDAO etiquetteDAO;

    /**
     * Accès aux contacts dans la base de données.
     */
    private final ContactDAO contactDAO;

    /**
     * Accès aux listes de diffusion dans la base de données.
     */
    private final ListeDiffusionDAO listeDAO;

    /**
     * Copie en mémoire des données de la base.
     */
    private final Carnet carnet;

    /**
     * Écouteurs prévenus après chaque modification des données.
     */
    private final List<Runnable> ecouteurs = new ArrayList<>();

    /**
     * Action exécutée pour signaler une erreur à l'utilisateur (une boîte de dialogue par défaut).
     */
    private Consumer<String> gestionnaireErreurs = message ->
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);

    /**
     * Construit le contrôleur et charge les données depuis la base. Si la lecture échoue,
     * l'erreur est signalée à l'utilisateur et le carnet reste vide.
     *
     * @param cx la connexion ouverte vers la base de données
     */
    public Controleur(Connection cx) {
        etiquetteDAO = new EtiquetteDAO(cx);
        contactDAO = new ContactDAO(cx);
        listeDAO = new ListeDiffusionDAO(cx);
        carnet = new Carnet(etiquetteDAO, contactDAO, listeDAO);

        try {
            carnet.recharger();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    // ------------------------------------------------------------------ lecture

    /**
     * Renvoie toutes les étiquettes, triées par nom.
     *
     * @return la liste (non modifiable) des étiquettes
     */
    public List<EtiquetteVue> getEtiquettes() {
        return carnet.getEtiquettes();
    }

    /**
     * Renvoie tous les contacts, triés par nom.
     *
     * @return la liste (non modifiable) des contacts
     */
    public List<ContactVue> getContacts() {
        return carnet.getContacts();
    }

    /**
     * Renvoie les contacts qui possèdent une étiquette donnée, triés par nom.
     *
     * @param filtre l'étiquette recherchée (<code>null</code> pour obtenir tous les contacts)
     * @return la liste des contacts correspondants
     */
    public List<ContactVue> getContacts(EtiquetteVue filtre) {
        if (filtre == null) {
            return carnet.getContacts();
        }
        List<ContactVue> resultat = new ArrayList<>();
        for (ContactVue contact : carnet.getContacts()) {
            if (contact.getEtiquettes().contains(filtre)) {
                resultat.add(contact);
            }
        }
        return resultat;
    }

    /**
     * Renvoie toutes les listes de diffusion, triées par nom.
     *
     * @return la liste (non modifiable) des listes de diffusion
     */
    public List<ListeDiffusionVue> getListesDiffusion() {
        return carnet.getListes();
    }

    /**
     * Calcule les membres d'une liste de diffusion : les contacts qui ont toutes les étiquettes
     * requises et aucune des étiquettes interdites. Le calcul est refait à chaque appel.
     *
     * @param liste la liste de diffusion
     * @return les contacts de la liste, triés par nom (liste vide en cas d'erreur)
     */
    public List<ContactVue> getContactsDe(ListeDiffusionVue liste) {
        try {
            return carnet.membres(liste);
        } catch (ModeleException e) {
            signaler(e);
            return new ArrayList<>();
        }
    }

    // ------------------------------------------------------------------ contacts

    /**
     * Crée un contact et l'enregistre dans la base de données.
     *
     * @param nom le nom du contact
     * @param email l'adresse mail du contact
     * @param etiquettesDuContact les étiquettes du contact
     * @return le contact créé, ou <code>null</code> si la création a échoué
     */
    public ContactVue creerContact(String nom, String email, List<EtiquetteVue> etiquettesDuContact) {
        try {
            Contact c = new Contact(nom, email);
            for (Etiquette e : carnet.modelesDe(etiquettesDuContact)) {
                c.ajouterEtiquette(e);
            }
            contactDAO.creer(c);
            carnet.recharger();
            notifier();
            return carnet.contactDeId(c.getId());
        } catch (ModeleException e) {
            signaler(e);
            return null;
        }
    }

    /**
     * Modifie un contact existant dans la base de données.
     *
     * @param contact le contact à modifier
     * @param nom le nouveau nom
     * @param email la nouvelle adresse mail
     * @param etiquettesDuContact les nouvelles étiquettes du contact
     */
    public void modifierContact(ContactVue contact, String nom, String email,
                                List<EtiquetteVue> etiquettesDuContact) {
        try {
            Contact c = new Contact(carnet.modeleDe(contact).getId(), nom, email);
            for (Etiquette e : carnet.modelesDe(etiquettesDuContact)) {
                c.ajouterEtiquette(e);
            }
            contactDAO.modifier(c);
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    /**
     * Supprime un contact de la base de données.
     *
     * @param contact le contact à supprimer
     */
    public void supprimerContact(ContactVue contact) {
        try {
            contactDAO.supprimer(carnet.modeleDe(contact).getId());
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    // ---------------------------------------------------------------- étiquettes

    /**
     * Crée une étiquette et l'enregistre dans la base de données.
     *
     * @param libelle le nom de l'étiquette
     * @return l'étiquette créée, ou <code>null</code> si la création a échoué
     */
    public EtiquetteVue creerEtiquette(String libelle) {
        try {
            Etiquette e = new Etiquette(libelle);
            etiquetteDAO.creer(e);
            carnet.recharger();
            notifier();
            return carnet.etiquetteDeId(e.getId());
        } catch (ModeleException e) {
            signaler(e);
            return null;
        }
    }

    /**
     * Modifie le nom d'une étiquette existante dans la base de données.
     *
     * @param etiquette l'étiquette à modifier
     * @param libelle le nouveau nom
     */
    public void modifierEtiquette(EtiquetteVue etiquette, String libelle) {
        try {
            etiquetteDAO.modifier(new Etiquette(carnet.modeleDe(etiquette).getId(), libelle));
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    /**
     * Supprime une étiquette de la base de données. Elle disparaît des contacts qui l'avaient.
     * Si elle est le seul critère d'une liste de diffusion, la suppression est refusée, car
     * une liste doit toujours avoir au moins un critère.
     *
     * @param etiquette l'étiquette à supprimer
     * @return <code>true</code> si l'étiquette a été supprimée, <code>false</code> sinon
     */
    public boolean supprimerEtiquette(EtiquetteVue etiquette) {
        try {
            ListeDiffusionVue concernee = carnet.listeAvecSeulCritere(etiquette);
            if (concernee != null) {
                signaler("Impossible de supprimer l'étiquette « " + etiquette.getNom()
                    + " » : elle est le seul critère de la liste « " + concernee.getNom()
                    + " ». Modifiez ou supprimez d'abord cette liste.");
                return false;
            }
            etiquetteDAO.supprimer(carnet.modeleDe(etiquette).getId());
            carnet.recharger();
            notifier();
            return true;
        } catch (ModeleException e) {
            signaler(e);
            return false;
        }
    }

    // ------------------------------------------------------ listes de diffusion

    /**
     * Crée une liste de diffusion et l'enregistre dans la base de données.
     *
     * @param nom le nom de la liste
     * @param criteres les critères de la liste (au moins un)
     * @return la liste créée, ou <code>null</code> si la création a échoué
     */
    public ListeDiffusionVue creerListeDiffusion(String nom, List<CritereVue> criteres) {
        try {
            ListeDiffusion l = new ListeDiffusion(nom);
            l.setCriteres(carnet.modelesDeCriteres(criteres));
            listeDAO.creer(l);
            carnet.recharger();
            notifier();
            return carnet.listeDeId(l.getId());
        } catch (ModeleException e) {
            signaler(e);
            return null;
        }
    }

    /**
     * Modifie une liste de diffusion existante dans la base de données.
     *
     * @param liste la liste à modifier
     * @param nom le nouveau nom
     * @param criteres les nouveaux critères (au moins un)
     */
    public void modifierListeDiffusion(ListeDiffusionVue liste, String nom, List<CritereVue> criteres) {
        try {
            ListeDiffusion l = new ListeDiffusion(carnet.modeleDe(liste).getId(), nom);
            l.setCriteres(carnet.modelesDeCriteres(criteres));
            listeDAO.modifier(l);
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    /**
     * Supprime une liste de diffusion de la base de données.
     *
     * @param liste la liste à supprimer
     */
    public void supprimerListeDiffusion(ListeDiffusionVue liste) {
        try {
            listeDAO.supprimer(carnet.modeleDe(liste).getId());
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    // ------------------------------------------------------------ communication

    /**
     * Enregistre un écouteur prévenu après chaque modification des données, afin que la vue
     * se mette à jour.
     *
     * @param ecouteur l'action à exécuter après chaque modification
     */
    public void ajouterEcouteur(Runnable ecouteur) {
        ecouteurs.add(ecouteur);
    }

    /**
     * Remplace la façon de signaler les erreurs à l'utilisateur (une boîte de dialogue par défaut).
     *
     * @param gestionnaire l'action qui reçoit le message d'erreur à afficher
     */
    public void setGestionnaireErreurs(Consumer<String> gestionnaire) {
        this.gestionnaireErreurs = gestionnaire;
    }

    /**
     * Prévient tous les écouteurs que les données ont changé.
     */
    private void notifier() {
        for (Runnable ecouteur : ecouteurs) {
            ecouteur.run();
        }
    }

    /**
     * Signale une erreur du modèle à l'utilisateur.
     *
     * @param e l'erreur, dont le message est affiché
     */
    private void signaler(ModeleException e) {
        signaler(e.getMessage());
    }

    /**
     * Signale une erreur à l'utilisateur.
     *
     * @param message le message à afficher
     */
    private void signaler(String message) {
        gestionnaireErreurs.accept(message);
    }
}