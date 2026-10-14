import java.awt.*;
import javax.swing.*;

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

        panneauCentral = new PanneauCentral();
        add(panneauCentral, BorderLayout.CENTER);

        panneauDroit = new PanneauDroit();
        add(panneauDroit, BorderLayout.EAST);
	}

    // --- Implémentation des méthodes du contrat NavigationListener

    @Override
    public void clicRepertoire(String nomEtiquette) {
        panneauCentral.afficherRepertoire();
        panneauDroit.afficherOperationsEtiquette(nomEtiquette);
    }

    @Override
    public void clicListeDiffusion(String nomListe) {
        panneauCentral.afficherListeDiffusion(nomListe);
        panneauDroit.afficherOperationsListeDiffusion(nomListe);
    }
}
