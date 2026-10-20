package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.*;
import javax.swing.*;
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
	    setTitle("AtoZ Carner");
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
     * @param nom le nom du contact sélectionné
     * @param email l'adresse email du contact sélectionné
     * @param etiquettes les étiquettes actuellement associées au contact
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicContact(java.lang.String, java.lang.String, java.util.List)
     */
    @Override
    public void clicContact(String nom, String email, List<String> etiquettes) {
        // Mis à jour du panneau central, on affiche les contacts
        panneauCentral.afficherRepertoire();

        // Mis à jour du panneau Droit, on affiche rien si on clic sur bouton acordeon
        if (nom == null){
            panneauDroit.afficherVide();
            return;
        }

        //Donnée fake
        //TODO oeoe
        List<String> toutesLesEtiquettes = List.of("Client", "Famille", "Travail", "VIP");

        // Mis à jour du panneau Droit, on affiche les opérations liée aux contacts
        panneauDroit.afficherOperationsContact(nom ,email ,toutesLesEtiquettes, etiquettes);
    }

    /**
     * Réagit au clic sur une étiquette : affiche le répertoire au centre et les
     * opérations possibles sur cette étiquette à droite.
     *
     * @param nomEtiquette le libellé de l'étiquette sélectionnée
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicContactEtiquette(java.lang.String)
     */
    @Override
    public void clicContactEtiquette(String nomEtiquette) {
        // Mis à jour du panneau central, on affiche les contacts possédant l'étiquette au centre
        //TODO Selectionné uniquement contact avec étiquette
        panneauCentral.afficherRepertoire();

        // Mis à jour du panneau Droit, on affiche rien si on clic sur bouton acordeon
        if (nomEtiquette == null){
            panneauDroit.afficherVide();
            return;
        }

        // Mis à jour du panneau Droit, on affiche les opérations liée aux étiquetteq
        panneauDroit.afficherOperationsEtiquette(nomEtiquette);
    }

    /**
     * Réagit au clic sur une liste de diffusion : affiche la page dédiée à la
     * liste au centre et les opérations possibles sur cette liste à droite.
     *
     * @param nomListe le nom de la liste de diffusion sélectionnée
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicListeDiffusion(java.lang.String)
     */
    @Override
    public void clicListeDiffusion(String nomListe) {
        // Mis à jour du panneau central, on affiche la page central dedié a la liste au centre
        panneauCentral.afficherListeDiffusion(nomListe);

        // Donnée fake
        List<String> toutesLesEtiquettes = List.of("Client", "Famille", "Travail", "VIP");
        List<CritereData> criteresActuels = List.of(
            new CritereData("Client", true)
        );

        // Mis à jour du panneau Droit, on affiche rien si on clic sur bouton acordeon
        if (nomListe == null){
            panneauCentral.afficherListeDiffusion(null);
            panneauDroit.afficherVide();
            return;
        }

        // Mis à jour du panneau Droit, on affiche les opérations liée aux Lists de diffusions
        panneauDroit.afficherOperationsListeDiffusion(nomListe, toutesLesEtiquettes, criteresActuels);
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
