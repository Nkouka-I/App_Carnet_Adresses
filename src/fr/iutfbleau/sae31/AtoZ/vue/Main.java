package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * La classe <code>Main</code> contient le point d'entrée de l'application AtoZ Carnet.
 * Elle crée et affiche la fenêtre principale dans le thread dédié à Swing.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class Main {

    /**
     * Point d'entrée du programme. Applique le look and feel du système d'exploitation
     * puis affiche la fenêtre principale.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Exécute la création et l'affichage de la fenêtre dans le thread dédié à Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Optionnel : Appliquer un look and feel moderne (celui du système d'exploitation)
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Instanciation et affichage de ta fenêtre principale
            Fenetre fenetre = new Fenetre();
            fenetre.setVisible(true);
        });
    }
}
