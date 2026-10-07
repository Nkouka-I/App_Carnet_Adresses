import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame{

	public Fenetre(int x){
		// on configure la fenetre
	    setSize(600, 400);
	    setLocation(20, 20);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	}
}
