package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.*;
import javax.swing.*;
import java.util.List;

public class Fenetre extends JFrame implements NavigationListener{

    private final PanneauCentral panneauCentral;
    private final PanneauGauche panneauGauche;
    private final PanneauDroit panneauDroit;

	public Fenetre(){
		// on configure la fenetre
	    setTitle("AtoZ Carner");
        setSize(1150, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
    /** (non-Javadoc)
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicContactEtiquette(java.lang.String)
     */
    @Override
    public void clicContact(String nom, String email, List<String> etiquettes) {
        // Mis à jour du panneau central, on affiche les contacts
        panneauCentral.afficherRepertoire();

        //Donnée fake
        //TODO oeoe
        List<String> toutesLesEtiquettes = List.of("Client", "Famille", "Travail", "VIP");

        // Mis à jour du panneau Droit, on affiche les opérations liée aux contacts
        panneauDroit.afficherOperationsContact(nom ,email ,toutesLesEtiquettes, etiquettes);
    }

    /** (non-Javadoc)
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicContactEtiquette(java.lang.String)
     */
    @Override
    public void clicContactEtiquette(String nomEtiquette) {
        // Mis à jour du panneau central, on affiche les contacts possédant l'étiquette au centre
        //TODO Selectionné uniquement contact avec étiquette
        panneauCentral.afficherRepertoire();

        // Mis à jour du panneau Droit, on affiche les opérations liée aux étiquetteq
        panneauDroit.afficherOperationsEtiquette(nomEtiquette);
    }

    /** (non-Javadoc)
     * @see fr.iutfbleau.sae31.AtoZ.vue.NavigationListener#clicListeDiffusion(java.lang.String)
     */
    @Override
    public void clicListeDiffusion(String nomListe) {
        // Mis à jour du panneau central, on affiche la page central dedié a la liste au centre
        panneauCentral.afficherListeDiffusion(nomListe);

        // Donnée fake
        List<String> toutesLesEtiquettes = List.of("Client", "Famille", "Travail", "VIP");
        List<FenetreFormulaireListeDiffusion.CritereData> criteresActuels = List.of(
            new FenetreFormulaireListeDiffusion.CritereData("Client", true)
        );

        // Mis à jour du panneau Droit, on affiche les opérations liée aux Lists de diffusions
        panneauDroit.afficherOperationsListeDiffusion(nomListe, toutesLesEtiquettes, criteresActuels);
    }
}
