import javax.swing.*;

public class Main {
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