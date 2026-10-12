import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanneauGauche extends JPanel{

    private JButton btnNouveauContact;
    private JList<String> listeEtiquettes;

    private JLabel titreApp;
    
    private JLabel lblEtiquettes;
    private JLabel lblListesDiffusion;

    public PanneauGauche(){

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0)); // Limité a une largeur fixe
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Une seule colonne
        
        int ligne = 0;

        // Titre
        gbc.gridy = ligne++;
        titreApp = new JLabel("AtoZ Carnet", JLabel.CENTER);
        add(titreApp, gbc);

        // 1-Bouton "+ new Contact" en premier
        gbc.gridy = ligne++;
        btnNouveauContact = new JButton("+ new Contact");
        add(btnNouveauContact, gbc);

        // 2-Section Étiquettes en deuxieme
        gbc.gridy = ligne++;
        lblEtiquettes = new JLabel("Étiquettes :");
        add(lblEtiquettes, gbc);

        // fausse données
        List<String> nomsEtiquettes = List.of("Client", "Famille", "Travail", "VIP");
        for (String nom : nomsEtiquettes) {
            gbc.gridy = ligne++;
            JButton btn = new JButton(nom);
            // btn.addActionListener(...);
            add(btn, gbc);
        }

        // 3-Section Listes de Diffusion
        gbc.gridy = ligne++;
        lblListesDiffusion = new JLabel("Listes de Diffusion :");
        lblListesDiffusion.setFont(new Font("Arial", Font.BOLD, 12));
        add(lblListesDiffusion, gbc);

        // fausse données
        List<String> nomsListes = List.of("Projet", "News");
        for (String nom : nomsListes) {
            gbc.gridy = ligne++;
            JButton btn = new JButton(nom);
            // btn.addActionListener(...);
            add(btn, gbc);
        }

        gbc.weighty = 1.0;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        add(filler, gbc);
        
    }
}