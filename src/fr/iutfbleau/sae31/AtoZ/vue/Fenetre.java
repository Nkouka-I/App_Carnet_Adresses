package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * La classe <code>Fenetre</code> est la fenêtre principale de l'application AtoZ Carnet.
 * Elle assemble les trois panneaux de l'interface (gauche, central et droit), mémorise
 * ce qui est sélectionné et met les panneaux à jour, soit quand l'utilisateur navigue,
 * soit quand le carnet est modifié.
 *
 * @version 1.2
 * @author Nkouka Ildevert
 */
public class Fenetre extends JFrame implements NavigationListener {

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
     * Indique si la page des listes de diffusion est affichée au centre (au lieu du répertoire).
     */
    private boolean modeListes = false;

    /**
     * Construit la fenêtre principale : configure le titre, la taille, le
     * comportement de fermeture, place les trois panneaux dans un
     * <code>BorderLayout</code> et s'abonne aux modifications du carnet.
     *
     * @param controleur le contrôleur auquel la vue demande les données
     */
    public Fenetre(Controleur controleur) {
        this.controleur = controleur;

        setTitle("AtoZ Carnet");
        setSize(1150, 700);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new EcouteurFermeture(this));

        setMinimumSize(new Dimension(820, 520));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Style.FOND_CENTRAL);

        // Chaque panneau est placé dans une zone de défilement : si la fenêtre devient
        // trop petite, des barres de défilement apparaissent
        panneauGauche = new PanneauGauche(this, controleur);
        add(ZoneDefilante.autour(panneauGauche, Style.LARGEUR_PANNEAU_GAUCHE, 0, -1), BorderLayout.WEST);

        panneauCentral = new PanneauCentral(this, controleur);
        add(ZoneDefilante.autour(panneauCentral, 0, 560, 440), BorderLayout.CENTER);

        panneauDroit = new PanneauDroit(controleur);
        add(ZoneDefilante.autour(panneauDroit, Style.LARGEUR_PANNEAU_DROIT, 0, -1), BorderLayout.EAST);

        controleur.ajouterEcouteur(this::actualiser);
    }

    /**
     * Réagit au clic sur "Tous les contacts" : affiche tout le répertoire et vide le
     * panneau de droite.
     */
    @Override
    public void clicTousLesContacts() {
        afficherTousLesContacts();
    }

    /**
     * Réagit au dépliage du menu des étiquettes : affiche tout le répertoire et vide le
     * panneau de droite.
     */
    @Override
    public void clicMenuEtiquettes() {
        afficherTousLesContacts();
    }

    /**
     * Réagit au dépliage du menu des listes de diffusion : affiche la page d'accueil des
     * listes et vide le panneau de droite.
     */
    @Override
    public void clicMenuListesDiffusion() {
        deselectionner();
        modeListes = true;

        panneauCentral.afficherListeDiffusion(null);
        panneauDroit.afficherVide();
        majSelection();
    }

    /**
     * Réagit au clic sur un contact du répertoire : affiche le répertoire au centre et les
     * opérations possibles sur ce contact (modifier, supprimer).
     *
     * @param contact le contact sélectionné
     */
    @Override
    public void clicContact(ContactVue contact) {
        deselectionner();
        contactSelectionne = contact;
        modeListes = false;

        panneauCentral.afficherRepertoire();
        panneauDroit.afficherOperationsContact(contact);
        majSelection();
    }

    /**
     * Réagit au clic sur un contact depuis une liste de diffusion : le contact est
     * sélectionné et ses opérations s'affichent à droite, mais la liste reste affichée
     * au centre.
     *
     * @param contact le contact sélectionné
     */
    @Override
    public void clicContactDansListe(ContactVue contact) {
        contactSelectionne = contact;
        // modeListes et listeSelectionnee restent inchangés : la liste reste affichée
        panneauDroit.afficherOperationsContact(contact);
        majSelection();
    }

    /**
     * Réagit au clic sur une étiquette : affiche au centre les contacts qui la possèdent
     * et, à droite, les opérations possibles sur cette étiquette.
     *
     * @param etiquette l'étiquette sélectionnée
     */
    @Override
    public void clicEtiquette(EtiquetteVue etiquette) {
        deselectionner();
        etiquetteSelectionnee = etiquette;
        filtreEtiquette = etiquette;
        modeListes = false;

        panneauCentral.afficherRepertoire(etiquette);
        panneauDroit.afficherOperationsEtiquette(etiquette);
        majSelection();
    }

    /**
     * Réagit au clic sur une liste de diffusion : affiche la page dédiée à la
     * liste au centre et les opérations possibles sur cette liste à droite.
     *
     * @param liste la liste de diffusion sélectionnée
     */
    @Override
    public void clicListeDiffusion(ListeDiffusionVue liste) {
        deselectionner();
        listeSelectionnee = liste;
        modeListes = true;

        panneauCentral.afficherListeDiffusion(liste);
        panneauDroit.afficherOperationsListeDiffusion(liste);
        majSelection();
    }

    /**
     * Affiche tout le répertoire (sans filtre) et vide le panneau de droite.
     */
    private void afficherTousLesContacts() {
        deselectionner();
        filtreEtiquette = null;
        modeListes = false;

        panneauCentral.afficherRepertoire(null);
        panneauDroit.afficherVide();
        majSelection();
    }

    /**
     * Met en évidence ce qui est sélectionné : le contact au centre, et dans le menu
     * de gauche le bouton de la vue affichée (tous les contacts, étiquette ou liste).
     */
    private void majSelection() {
        panneauCentral.selectionnerContact(contactSelectionne);
        panneauGauche.marquer(
            !modeListes && filtreEtiquette == null,
            modeListes ? null : filtreEtiquette,
            listeSelectionnee
        );
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
        filtreEtiquette = controleur.etiquetteActuelle(filtreEtiquette);
        etiquetteSelectionnee = controleur.etiquetteActuelle(etiquetteSelectionnee);
        contactSelectionne = controleur.contactActuel(contactSelectionne);
        listeSelectionnee = controleur.listeActuelle(listeSelectionnee);

        panneauCentral.actualiser(filtreEtiquette, listeSelectionnee);
        majSelection();

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

        if (reponse == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}
