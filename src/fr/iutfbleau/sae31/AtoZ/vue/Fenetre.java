package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.List;

/**
 * La classe <code>Fenetre</code> est la fenêtre principale de l'application AtoZ Carnet.
 * Elle assemble les trois panneaux de l'interface (gauche, central et droit) et
 * coordonne leur mise à jour en réagissant aux événements de navigation.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class Fenetre extends JFrame implements NavigationListener{

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
     * Construit la fenêtre principale : configure le titre, la taille, le
     * comportement de fermeture, puis place les trois panneaux dans un
     * <code>BorderLayout</code>.
     */
	public Fenetre(){
		// on configure la fenetre
	    setTitle("AtoZ Carnet");
        setSize(1150, 700);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Empeche fermeture automatique
        addWindowListener(new EcouteurFermeture(this));

        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran

        setLayout(new BorderLayout());

        panneauGauche = new PanneauGauche(this);
        add(panneauGauche, BorderLayout.WEST);

        panneauCentral = new PanneauCentral(this);
        add(panneauCentral, BorderLayout.CENTER);

        panneauDroit = new PanneauDroit();
        add(panneauDroit, BorderLayout.EAST);
	}

    // méthodes du contrat NavigationListener

    /**
     * Réagit au clic sur un contact : affiche le répertoire au centre et les
     * opérations possibles sur ce contact (modifier, supprimer).
     *
     * @param nom le nom du contact sélectionné (<code>null</code> pour "Tous les contacts")
     * @param email l'adresse email du contact sélectionné
     * @param etiquettes les étiquettes actuellement associées au contact
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicContact(java.lang.String, java.lang.String, java.util.List)
     */
    @Override
    public void clicContact(String nom, String email, List<String> etiquettes) {
        // Mis à jour du panneau central, on affiche les contacts
        panneauCentral.afficherRepertoire();

        // Aucun contact sélectionné (bouton "Tous les contacts") : panneau droit vide
        if (nom == null){
            panneauDroit.afficherVide();
            return;
        }

        // Mis à jour du panneau Droit, on affiche les opérations liées aux contacts
        panneauDroit.afficherOperationsContact(nom, email, DonneesTest.etiquettes(), etiquettes);
    }

    /**
     * Réagit au clic sur une étiquette : affiche le répertoire au centre et les
     * opérations possibles sur cette étiquette à droite.
     *
     * @param nomEtiquette le libellé de l'étiquette sélectionnée (<code>null</code> pour le bouton accordéon)
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicContactEtiquette(java.lang.String)
     */
    @Override
    public void clicContactEtiquette(String nomEtiquette) {
        // Mis à jour du panneau central, on affiche les contacts possédant l'étiquette au centre
        //TODO Sélectionner uniquement les contacts avec l'étiquette
        panneauCentral.afficherRepertoire();

        // Bouton accordéon : panneau droit vide
        if (nomEtiquette == null){
            panneauDroit.afficherVide();
            return;
        }

        // Mis à jour du panneau Droit, on affiche les opérations liées à l'étiquette
        panneauDroit.afficherOperationsEtiquette(nomEtiquette);
    }

    /**
     * Réagit au clic sur une liste de diffusion : affiche la page dédiée à la
     * liste au centre et les opérations possibles sur cette liste à droite.
     *
     * @param nomListe le nom de la liste de diffusion sélectionnée (<code>null</code> pour le bouton accordéon)
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicListeDiffusion(java.lang.String)
     */
    @Override
    public void clicListeDiffusion(String nomListe) {
        // Mis à jour du panneau central, on affiche la page dédiée à la liste (ou l'accueil si null)
        panneauCentral.afficherListeDiffusion(nomListe);

        // Bouton accordéon : panneau droit vide
        if (nomListe == null){
            panneauDroit.afficherVide();
            return;
        }

        // Mis à jour du panneau Droit, on affiche les opérations liées aux listes de diffusion
        panneauDroit.afficherOperationsListeDiffusion(nomListe, DonneesTest.etiquettes(), DonneesTest.criteres());
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
