import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PanneauDroit extends JPanel{

    private JButton btnModifier;
    private JButton btnSupprimer;

    public PanneauDroit(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0)); // Limité à une largeur fixe
        setBackground(new Color(245, 245, 245));

        afficherVide();
    }

    public void afficherVide() {
        removeAll();
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblInfo = new JLabel("<html><center>Sélectionnez un élément<br>pour afficher ses détails</center></html>");
        lblInfo.setForeground(new Color(120, 120, 120));
        add(lblInfo, gbc);

        revalidate();
        repaint();
    }

    /**
     * 2. Vue dédiée lorsqu'un CONTACT est sélectionné
     */
    public void afficherOperationsContact() {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int ligne = 0;

        // Boutons d'action (Modifier / Supprimer)
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 5, 10);
        btnModifier = new JButton("Modifier");
        add(btnModifier, gbc);

        // Bouton Supprimer
        gbc.gridy = ligne++;
        gbc.insets = new Insets(5, 10, 10, 10);
        btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setForeground(Color.RED);
        add(btnSupprimer, gbc);

        revalidate();
        repaint();
    }

    /**
     * 3. Vue dédiée lorsqu'une ÉTIQUETTE est sélectionnée
     */
    public void afficherOperationsEtiquette(String nomEtiquette) {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int ligne = 0;

        // Bouton Modifier
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 5, 10);
        btnModifier = new JButton("Modifier l'étiquette");
        add(btnModifier, gbc);
        
        // Titre explicatif Etiquette
        gbc.gridy = ligne++;
        JLabel lblTitre = new JLabel("<html><b>L'étiquette &quot;" + nomEtiquette + "&quot; apparaît dans les listes :</b></html>");
        lblTitre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitre, gbc);

        /*Boucle que ajoute les differents noms de listes*/

        // Bouton Supprimer
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 10, 10);
        JButton btnSupprEtiquette = new JButton("Supprimer l'étiquette");
        btnSupprEtiquette.setForeground(Color.RED);
        add(btnSupprEtiquette, gbc);

        revalidate();
        repaint();
    }

    /**
     * 4. Vue dédiée lorsqu'une LISTE DE DIFFUSION est sélectionnée
     */
    public void afficherOperationsListeDiffusion(String nomListe) {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int ligne = 0;

        // Bouton Modifier
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 5, 10);
        btnModifier = new JButton("Modifier la liste");
        add(btnModifier, gbc);

        // Titre des critères
        gbc.gridy = ligne++;
        gbc.insets = new Insets(10, 10, 5, 10);
        JLabel lblTitre = new JLabel("<html><b>Critères de la liste " + nomListe + " :</b><br></html>", JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitre, gbc);

        /*Boucle que ajoute les differents noms de critere*/
        
        // Bouton Supprimer
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 10, 10);
        JButton btnSupprEtiquette = new JButton("Supprimer la liste");
        btnSupprEtiquette.setForeground(Color.RED);
        add(btnSupprEtiquette, gbc);

        revalidate();
        repaint();
    }
}