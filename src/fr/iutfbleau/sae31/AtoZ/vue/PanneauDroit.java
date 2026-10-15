import java.awt.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionListener;

public class PanneauDroit extends JPanel{

    private JButton btnSupprimer = new JButton("Supprimer");
    private JButton btnModifier = new JButton("Modifier");

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
        ajouterBoutonSuppression(
            gbc, 
            ligne++,
            "Supprimer le contact",
            "Voulez-vous vraiment supprimer le contact de ?",
            "Le contact a bien été supprimé !",
            () -> {
                // TODO: Appeler le contrôleur pour supprimer le contact
                System.out.println("le contact a bien été supprimé");
            }
        );

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

        /*TODO Boucle que ajoute les differents noms de listes*/

        // Bouton Supprimer
        ajouterBoutonSuppression(
            gbc, 
            ligne++,
            "Supprimer l'étiquette",
            "Voulez-vous vraiment supprimer l'étiquette \"" + nomEtiquette + "\" ?",
            "L'étiquette a bien été supprimée !",
            () -> {
                // TODO: Appeler le contrôleur pour supprimer l'étiquette
                System.out.println("l'étiquette a bien été supprimé...");
            }
        );

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

        /*TODO Boucle que ajoute les differents noms de critere*/
        
        // Bouton Supprimer
        ajouterBoutonSuppression(
            gbc, 
            ligne++,
            "Supprimer la liste de diffusion",
            "Voulez-vous vraiment supprimer la liste de diffusion \"" + nomListe + "\" ?",
            "La liste de diffusion a bien été supprimé !",
            () -> {
                // TODO: Appeler le contrôleur
                System.out.println("La liste de diffusion a bien été supprimé");
            }
        );

        revalidate();
        repaint();
    }

    public void ajouterBoutonSuppression(GridBagConstraints gbc, int ligne, String texteBouton, String messageConfirmation, String messageSucces, Runnable actionAEffectuer) {

        btnSupprimer.setText(texteBouton);
        btnSupprimer.setForeground(Color.RED);

        // On enleve les anciens écouteurs
        for (ActionListener al : btnSupprimer.getActionListeners()) {
            btnSupprimer.removeActionListener(al);
        }

        // On ajoute le nouveau comportement
        btnSupprimer.addActionListener(e -> {

            Window fenetreParente = SwingUtilities.getWindowAncestor(this);

            // Le pop-up bloquant
            int reponse = JOptionPane.showConfirmDialog(
                fenetreParente, 
                messageConfirmation, 
                "Confirmation de suppression", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );
            
            if (reponse == JOptionPane.YES_OPTION) {
                
                // TODO: Code pour supprimer de la base de données
                if (actionAEffectuer != null) {
                    actionAEffectuer.run();
                }

                // pop-up de confirmation
                JOptionPane.showMessageDialog(
                    fenetreParente, 
                    messageSucces, 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                // Remise à zéro de l'affichage
                afficherVide();
            }
        });
        gbc.gridy = ligne;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(btnSupprimer, gbc);
    }
}