package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;
import java.awt.*;
import javax.swing.*;

/**
 * La classe <code>Fenetre</code> est la fenêtre principale de l'application AtoZ Carnet.
 * Elle assemble les trois panneaux de l'interface (gauche, central et droit), mémorise
 * ce qui est sélectionné et met les panneaux à jour, soit quand l'utilisateur navigue,
 * soit quand le carnet est modifié.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class Fenetre extends JFrame implements NavigationListener{

    /**
     * Contrôleur auquel la vue demande les données et les modifications.
     */
    private final Controleur controleur;

    /**
     * Panneau central affichant le répertoire de contacts ou une liste de diffusion.
     */
    private final PanneauCentral panneauCentral;

    /**
     * Panneau de gauche contenant les boutons de création et le menu de navigation.
     */
    private final PanneauGauche panneauGauche;

    /**
     * Panneau de droite affichant les opérations disponibles sur l'élément sélectionné.
     */
    private final PanneauDroit panneauDroit;

    /**
     * Étiquette qui filtre le répertoire (<code>null</code> : tous les contacts).
     */
    private EtiquetteVue filtreEtiquette;

    /**
     * Contact sélectionné (<code>null</code> si aucun).
     */
    private ContactVue contactSelectionne;

    /**
     * Étiquette sélectionnée (<code>null</code> si aucune).
     */
    private EtiquetteVue etiquetteSelectionnee;

    /**
     * Liste de diffusion sélectionnée (<code>null</code> si aucune).
     */
    private ListeDiffusionVue listeSelectionnee;

    /**
     * Construit la fenêtre principale : configure le titre, la taille, le
     * comportement de fermeture, place les trois panneaux dans un
     * <code>BorderLayout</code> et s'abonne aux modifications du carnet.
     *
     * @param controleur le contrôleur auquel la vue demande les données
     */
	public Fenetre(Controleur controleur){
        this.controleur = controleur;

		// on configure la fenetre
	    setTitle("AtoZ Carnet");
        setSize(1150, 700);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Empeche fermeture automatique
        addWindowListener(new EcouteurFermeture(this));

        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran

        setLayout(new BorderLayout());

        panneauGauche = new PanneauGauche(this, controleur);
        add(panneauGauche, BorderLayout.WEST);

        panneauCentral = new PanneauCentral(this, controleur);
        add(panneauCentral, BorderLayout.CENTER);

        panneauDroit = new PanneauDroit(controleur);
        add(panneauDroit, BorderLayout.EAST);

        controleur.ajouterEcouteur(this::actualiser);
	}

    // méthodes du contrat NavigationListener

    /**
     * Réagit au clic sur "Tous les contacts" : affiche tout le répertoire et vide le
     * panneau de droite.
     *
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicTousLesContacts()
     */
    @Override
    public void clicTousLesContacts() {
        afficherTousLesContacts();
    }

    /**
     * Réagit au dépliage du menu des étiquettes : affiche tout le répertoire et vide le
     * panneau de droite.
     *
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicMenuEtiquettes()
     */
    @Override
    public void clicMenuEtiquettes() {
        afficherTousLesContacts();
    }

    /**
     * Réagit au dépliage du menu des listes de diffusion : affiche la page d'accueil des
     * listes et vide le panneau de droite.
     *
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicMenuListesDiffusion()
     */
    @Override
    public void clicMenuListesDiffusion() {
        deselectionner();
        panneauCentral.afficherListeDiffusion(null);
        panneauDroit.afficherVide();
    }

    /**
     * Réagit au clic sur un contact : affiche le répertoire au centre et les
     * opérations possibles sur ce contact (modifier, supprimer).
     *
     * @param contact le contact sélectionné
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicContact(fr.iutfbleau.sae31.AtoZ.controleur.ContactVue)
     */
    @Override
    public void clicContact(ContactVue contact) {
        deselectionner();
        contactSelectionne = contact;

        panneauCentral.afficherRepertoire();
        panneauDroit.afficherOperationsContact(contact);
    }

    /**
     * Réagit au clic sur une étiquette : affiche au centre les contacts qui la possèdent
     * et, à droite, les opérations possibles sur cette étiquette.
     *
     * @param etiquette l'étiquette sélectionnée
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicEtiquette(fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue)
     */
    @Override
    public void clicEtiquette(EtiquetteVue etiquette) {
        deselectionner();
        etiquetteSelectionnee = etiquette;
        filtreEtiquette = etiquette;

        panneauCentral.afficherRepertoire(etiquette);
        panneauDroit.afficherOperationsEtiquette(etiquette);
    }

    /**
     * Réagit au clic sur une liste de diffusion : affiche la page dédiée à la
     * liste au centre et les opérations possibles sur cette liste à droite.
     *
     * @param liste la liste de diffusion sélectionnée
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicListeDiffusion(fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue)
     */
    @Override
    public void clicListeDiffusion(ListeDiffusionVue liste) {
        deselectionner();
        listeSelectionnee = liste;

        panneauCentral.afficherListeDiffusion(liste);
        panneauDroit.afficherOperationsListeDiffusion(liste);
    }

    /**
     * Affiche tout le répertoire (sans filtre) et vide le panneau de droite.
     */
    private void afficherTousLesContacts() {
        deselectionner();
        filtreEtiquette = null;

        panneauCentral.afficherRepertoire(null);
        panneauDroit.afficherVide();
    }

    /**
     * Oublie la sélection courante (le filtre du répertoire n'est pas modifié).
     */
    private void deselectionner() {
        contactSelectionne = null;
        etiquetteSelectionnee = null;
        listeSelectionnee = null;
    }

    /**
     * Met toute l'interface à jour après une modification du carnet. Les éléments
     * sélectionnés qui ont été supprimés sont oubliés, puis chaque panneau est
     * reconstruit à partir des données actuelles.
     */
    private void actualiser() {
        if (filtreEtiquette != null && !controleur.getEtiquettes().contains(filtreEtiquette)) {
            filtreEtiquette = null;
        }
        if (etiquetteSelectionnee != null && !controleur.getEtiquettes().contains(etiquetteSelectionnee)) {
            etiquetteSelectionnee = null;
        }
        if (contactSelectionne != null && !controleur.getContacts().contains(contactSelectionne)) {
            contactSelectionne = null;
        }
        if (listeSelectionnee != null && !controleur.getListesDiffusion().contains(listeSelectionnee)) {
            listeSelectionnee = null;
        }

        panneauGauche.rafraichir();
        panneauCentral.actualiser(filtreEtiquette, listeSelectionnee);

        if (contactSelectionne != null) {
            panneauDroit.afficherOperationsContact(contactSelectionne);
        } else if (etiquetteSelectionnee != null) {
            panneauDroit.afficherOperationsEtiquette(etiquetteSelectionnee);
        } else if (listeSelectionnee != null) {
            panneauDroit.afficherOperationsListeDiffusion(listeSelectionnee);
        } else {
            panneauDroit.afficherVide();
        }
    }

    /**
     * Affiche un pop-up de confirmation avant de quitter l'application.
     */
    protected void confirmerFermeture() {
        int reponse = JOptionPane.showConfirmDialog(
            this,
            "Voulez-vous vraiment quitter l'application ?",
            "Confirmation de fermeture",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        // Si clique sur "Oui"
        if (reponse == JOptionPane.YES_OPTION) {
            dispose(); // Destruction de la fenêtre
            System.exit(0); // Quitte l'application au cas ou
        }
    }
}
