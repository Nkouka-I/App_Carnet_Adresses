import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame{

	public Fenetre(){
		// on configure la fenetre
	    setTitle("AtoZ Carner");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran

        setLayout(new BorderLayout());
        PanneauGauche panneauGauche = new PanneauGauche();
        add(panneauGauche, BorderLayout.WEST);
        PanneauCentral panneauCentral = new PanneauCentral();
        add(panneauCentral, BorderLayout.CENTER);
	}
}
