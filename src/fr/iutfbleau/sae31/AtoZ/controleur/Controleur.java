package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.model.Contact;
import fr.iutfbleau.sae31.AtoZ.model.ContactDAO;
import fr.iutfbleau.sae31.AtoZ.model.Etiquette;
import fr.iutfbleau.sae31.AtoZ.model.EtiquetteDAO;
import fr.iutfbleau.sae31.AtoZ.model.ListeDiffusion;
import fr.iutfbleau.sae31.AtoZ.model.ListeDiffusionDAO;
import fr.iutfbleau.sae31.AtoZ.model.ModeleException;
import fr.iutfbleau.sae31.AtoZ.model.OrdreTri;
import fr.iutfbleau.sae31.AtoZ.model.Carnet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * La classe <code>Controleur</code> fait le lien entre la vue et le modèle : elle fournit à la
 * vue les contacts, étiquettes et listes de diffusion à afficher, et enregistre en base de
 * données les actions de l'utilisateur. Après chaque modification, le carnet est rechargé puis
 * les écouteurs sont prévenus pour que l'affichage soit mis à jour. Les objets du carnet étant
 * recréés à chaque rechargement, la vue doit les comparer avec les méthodes
 * <code>meme...</code> et <code>contient...</code> plutôt qu'avec <code>equals</code>.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du, Ildevert Nkouka
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
     * Copie en mémoire des contacts, étiquettes et listes de diffusion.
     */
    private final Carnet carnet;

    /**
     * Les actions à exécuter après chaque modification du carnet.
     */
    private final List<Runnable> ecouteurs = new ArrayList<>();

    /**
     * Les erreurs signalées avant qu'un gestionnaire d'erreurs ne soit installé (par exemple
     * une erreur de chargement dans le constructeur). Elles sont transmises au gestionnaire
     * dès qu'il est installé.
     */
    private final List<String> erreursEnAttente = new ArrayList<>();

    /**
     * L'action qui affiche un message d'erreur. Le contrôleur ne connaît pas l'interface
     * graphique : c'est la vue qui installe son gestionnaire. En attendant, les messages sont
     * gardés en attente.
     */
    private Consumer<String> gestionnaireErreurs = message -> {
        System.err.println("Erreur : " + message);
        erreursEnAttente.add(message);
    };

    /**
     * Constructeur du contrôleur, qui charge le carnet depuis la base de données (une erreur
     * éventuelle est signalée à l'utilisateur).
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

    /**
     * Renvoie toutes les étiquettes du carnet.
     *
     * @return la liste des étiquettes
     */
    public List<EtiquetteVue> getEtiquettes() { return carnet.getEtiquettes(); }

    /**
     * Renvoie tous les contacts du carnet.
     *
     * @return la liste des contacts
     */
    public List<ContactVue> getContacts() { return carnet.getContacts(); }

    /**
     * Renvoie les contacts qui possèdent une étiquette donnée (tri par étiquette).
     *
     * @param filtre l'étiquette recherchée, ou <code>null</code> pour obtenir tous les contacts
     * @return les contacts ayant cette étiquette
     */
    public List<ContactVue> getContacts(EtiquetteVue filtre) {
        if (filtre == null) { return carnet.getContacts(); }
        return carnet.contactsAvecEtiquette(filtre);
    }

    /**
     * Renvoie les libellés des ordres de tri proposés pour les contacts, dans l'ordre où ils
     * doivent être affichés.
     *
     * @return les libellés des ordres de tri
     */
    public List<String> getOrdresTri() {
        List<String> libelles = new ArrayList<>();
        for (OrdreTri ordre : OrdreTri.values()) { libelles.add(ordre.toString()); }
        return libelles;
    }

    /**
     * Renvoie les contacts triés par la base de données, éventuellement limités à ceux qui
     * possèdent une étiquette. En cas d'erreur, celle-ci est signalée et une liste vide est
     * renvoyée.
     *
     * @param filtre l'étiquette que les contacts doivent avoir (<code>null</code> pour tous les contacts)
     * @param ordre la position de l'ordre de tri choisi dans la liste de <code>getOrdresTri</code>
     * @return les contacts triés
     */
    public List<ContactVue> getContactsTries(EtiquetteVue filtre, int ordre) {
        OrdreTri[] ordres = OrdreTri.values();
        OrdreTri choisi = (ordre >= 0 && ordre < ordres.length) ? ordres[ordre] : ordres[0];
        try {
            return carnet.contactsTries(filtre, choisi);
        } catch (ModeleException e) {
            signaler(e);
            return new ArrayList<>();
        }
    }

    /**
     * Garde les contacts qui correspondent à une recherche, en conservant leur ordre.
     *
     * @param contacts les contacts parmi lesquels chercher
     * @param recherche le texte saisi (vide pour tout garder)
     * @return une nouvelle liste des contacts retenus
     */
    public List<ContactVue> rechercher(List<ContactVue> contacts, String recherche) {
        return carnet.rechercher(contacts, recherche);
    }

    // --- Comparaisons par identifiant (les objets sont recréés à chaque rechargement) ---

    /**
     * Indique si deux vues désignent la même étiquette (même identifiant en base).
     *
     * @param a la première étiquette
     * @param b la seconde étiquette
     * @return <code>true</code> si elles désignent la même étiquette
     */
    public boolean memeEtiquette(EtiquetteVue a, EtiquetteVue b) { return carnet.memeEtiquette(a, b); }

    /**
     * Indique si deux vues désignent le même contact (même identifiant en base).
     *
     * @param a le premier contact
     * @param b le second contact
     * @return <code>true</code> s'ils désignent le même contact
     */
    public boolean memeContact(ContactVue a, ContactVue b) { return carnet.memeContact(a, b); }

    /**
     * Indique si deux vues désignent la même liste de diffusion (même identifiant en base).
     *
     * @param a la première liste
     * @param b la seconde liste
     * @return <code>true</code> si elles désignent la même liste
     */
    public boolean memeListe(ListeDiffusionVue a, ListeDiffusionVue b) { return carnet.memeListe(a, b); }

    /**
     * Indique si une liste contient une étiquette donnée, en comparant par identifiant.
     *
     * @param liste la liste dans laquelle chercher (peut être <code>null</code>)
     * @param e l'étiquette recherchée
     * @return <code>true</code> si l'étiquette figure dans la liste
     */
    public boolean contientEtiquette(List<EtiquetteVue> liste, EtiquetteVue e) {
        if (liste != null) { for (EtiquetteVue x : liste) { if (carnet.memeEtiquette(x, e)) { return true; } } }
        return false;
    }

    /**
     * Indique si une liste contient un contact donné, en comparant par identifiant.
     *
     * @param liste la liste dans laquelle chercher (peut être <code>null</code>)
     * @param c le contact recherché
     * @return <code>true</code> si le contact figure dans la liste
     */
    public boolean contientContact(List<ContactVue> liste, ContactVue c) {
        if (liste != null) { for (ContactVue x : liste) { if (carnet.memeContact(x, c)) { return true; } } }
        return false;
    }

    // --- Version actuelle d'un objet après rechargement (null s'il n'existe plus) ---

    /**
     * Renvoie la version à jour d'une étiquette après un rechargement du carnet.
     *
     * @param e l'étiquette (éventuellement périmée)
     * @return l'étiquette à jour, ou <code>null</code> si elle n'existe plus
     */
    public EtiquetteVue etiquetteActuelle(EtiquetteVue e) { return carnet.etiquetteActuelle(e); }

    /**
     * Renvoie la version à jour d'un contact après un rechargement du carnet.
     *
     * @param c le contact (éventuellement périmé)
     * @return le contact à jour, ou <code>null</code> s'il n'existe plus
     */
    public ContactVue contactActuel(ContactVue c) { return carnet.contactActuel(c); }

    /**
     * Renvoie la version à jour d'une liste de diffusion après un rechargement du carnet.
     *
     * @param l la liste (éventuellement périmée)
     * @return la liste à jour, ou <code>null</code> si elle n'existe plus
     */
    public ListeDiffusionVue listeActuelle(ListeDiffusionVue l) { return carnet.listeActuelle(l); }

    /**
     * Renvoie toutes les listes de diffusion du carnet.
     *
     * @return la liste des listes de diffusion
     */
    public List<ListeDiffusionVue> getListesDiffusion() { return carnet.getListes(); }

    /**
     * Renvoie les listes de diffusion qui utilisent une étiquette dans leurs critères.
     * En cas d'erreur, celle-ci est signalée et une liste vide est renvoyée.
     *
     * @param etiquette l'étiquette concernée
     * @return les listes qui utilisent cette étiquette
     */
    public List<ListeDiffusionVue> getListesUtilisant(EtiquetteVue etiquette) {
        try {
            return carnet.listesUtilisant(etiquette);
        } catch (ModeleException e) {
            signaler(e);
            return new ArrayList<>();
        }
    }

    /**
     * Renvoie les contacts membres d'une liste de diffusion, c'est-à-dire ceux qui respectent
     * ses critères. En cas d'erreur, celle-ci est signalée et une liste vide est renvoyée.
     *
     * @param liste la liste de diffusion
     * @return les contacts membres de la liste
     */
    public List<ContactVue> getContactsDe(ListeDiffusionVue liste) {
        try {
            return carnet.membres(liste);
        } catch (ModeleException e) {
            signaler(e);
            return new ArrayList<>();
        }
    }

    /**
     * Crée un contact, l'enregistre en base de données puis prévient les écouteurs.
     * En cas d'erreur, celle-ci est signalée.
     *
     * @param nom le nom du contact
     * @param email l'adresse mail du contact
     * @param etiquettesDuContact les étiquettes à associer au contact
     * @return le contact créé, ou <code>null</code> en cas d'erreur
     */
    public ContactVue creerContact(String nom, String email, List<EtiquetteVue> etiquettesDuContact) {
        try {
            Contact c = new Contact(nom, email);
            for (Etiquette e : carnet.modelesDe(etiquettesDuContact)) { c.ajouterEtiquette(e); }
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
     * Modifie un contact (nom, adresse mail et étiquettes) puis prévient les écouteurs.
     * En cas d'erreur, celle-ci est signalée.
     *
     * @param contact le contact à modifier
     * @param nom le nouveau nom
     * @param email la nouvelle adresse mail
     * @param etiquettesDuContact les nouvelles étiquettes du contact
     */
    public void modifierContact(ContactVue contact, String nom, String email, List<EtiquetteVue> etiquettesDuContact) {
        try {
            Contact c = new Contact(carnet.modeleDe(contact).getId(), nom, email);
            for (Etiquette e : carnet.modelesDe(etiquettesDuContact)) { c.ajouterEtiquette(e); }
            contactDAO.modifier(c);
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    /**
     * Supprime un contact puis prévient les écouteurs. En cas d'erreur, celle-ci est signalée.
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

    /**
     * Crée une étiquette, l'enregistre en base de données puis prévient les écouteurs.
     * En cas d'erreur, celle-ci est signalée.
     *
     * @param libelle le libellé de la nouvelle étiquette
     * @return l'étiquette créée, ou <code>null</code> en cas d'erreur
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
     * Change le libellé d'une étiquette puis prévient les écouteurs. En cas d'erreur, celle-ci
     * est signalée.
     *
     * @param etiquette l'étiquette à modifier
     * @param libelle le nouveau libellé
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
     * Supprime une étiquette puis prévient les écouteurs. La suppression est refusée (et un
     * message est affiché) si l'étiquette est le seul critère d'une liste de diffusion.
     *
     * @param etiquette l'étiquette à supprimer
     * @return <code>true</code> si l'étiquette a été supprimée, <code>false</code> sinon
     */
    public boolean supprimerEtiquette(EtiquetteVue etiquette) {
        try {
            ListeDiffusionVue concernee = carnet.listeAvecSeulCritere(etiquette);
            if (concernee != null) {
                signaler("Impossible de supprimer l'étiquette : elle est le seul critère de la liste " + concernee.getNom());
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

    /**
     * Crée une liste de diffusion, l'enregistre en base de données puis prévient les écouteurs.
     * En cas d'erreur, celle-ci est signalée.
     *
     * @param nom le nom de la liste
     * @param criteres les critères (étiquettes incluses ou exclues) de la liste
     * @return la liste créée, ou <code>null</code> en cas d'erreur
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
     * Modifie le nom et les critères d'une liste de diffusion puis prévient les écouteurs.
     * En cas d'erreur, celle-ci est signalée.
     *
     * @param liste la liste à modifier
     * @param nom le nouveau nom
     * @param criteres les nouveaux critères
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
     * Supprime une liste de diffusion puis prévient les écouteurs. En cas d'erreur, celle-ci
     * est signalée.
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

    /**
     * Enregistre une action à exécuter après chaque modification du carnet.
     *
     * @param ecouteur l'action à exécuter
     */
    public void ajouterEcouteur(Runnable ecouteur) { ecouteurs.add(ecouteur); }

    /**
     * Installe la façon d'afficher les messages d'erreur (c'est la vue qui s'en charge). Les
     * erreurs déjà signalées et restées en attente lui sont transmises aussitôt.
     *
     * @param gestionnaire l'action qui reçoit le message d'erreur à afficher
     */
    public void setGestionnaireErreurs(Consumer<String> gestionnaire) {
        this.gestionnaireErreurs = gestionnaire;
        List<String> attente = new ArrayList<>(erreursEnAttente);
        erreursEnAttente.clear();
        for (String message : attente) {
            gestionnaire.accept(message);
        }
    }

    /**
     * Prévient tous les écouteurs que le carnet a été modifié.
     */
    private void notifier() { for (Runnable ecouteur : ecouteurs) { ecouteur.run(); } }

    /**
     * Signale une erreur du modèle à l'utilisateur.
     *
     * @param e l'erreur à signaler
     */
    private void signaler(ModeleException e) { signaler(e.getMessage()); }

    /**
     * Affiche un message d'erreur à l'utilisateur avec le gestionnaire d'erreurs.
     *
     * @param message le message à afficher
     */
    private void signaler(String message) { gestionnaireErreurs.accept(message); }

}