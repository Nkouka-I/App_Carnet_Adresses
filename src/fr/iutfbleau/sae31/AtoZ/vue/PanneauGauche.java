import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PanneauGauche extends JPanel{

    private final JButton btnNewContact;
    private final JButton btnNewEtiquette;
    private final JButton btnNewListesDiffusion;

    private final JLabel titreApp;
    private final JLabel lblEtiquettes;
    private final JLabel lblListesDiffusion;

    public PanneauGauche(NavigationListener listener){

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0)); // Limité à une largeur fixe
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; 
        
        int ligne = 0;

        // Titre
        gbc.gridy = ligne++;
        titreApp = new JLabel("AtoZ Carnet", JLabel.CENTER);
        add(titreApp, gbc);

        // 1-Bouton "+ new Contact" en premier
        gbc.gridy = ligne++;
        btnNewContact = new JButton("+ new Contact");
        add(btnNewContact, gbc);

        // 2-Section Étiquettes
        gbc.gridy = ligne++;
        lblEtiquettes = new JLabel("Étiquettes :");
        add(lblEtiquettes, gbc);

        gbc.gridy = ligne++;
        btnNewEtiquette = new JButton("+ new Etiquette");
        add(btnNewEtiquette, gbc);
        
        // Correction : on applique l'écouteur sur btnNewEtiquette
        btnNewEtiquette.addActionListener(e -> {
            if (listener != null) {
                listener.clicRepertoire(); 
            }
        });

        // Fausse données - Étiquettes
        List<String> nomsEtiquettes = List.of("Client", "Famille", "Travail", "VIP");
        for (String nom : nomsEtiquettes) {
            gbc.gridy = ligne++;
            JButton btn = new JButton(nom);
            
            // Clic sur une étiquette spécifique
            btn.addActionListener(e -> {
                if (listener != null) {
                    listener.clicRepertoire(); 
                }
            });
            
            add(btn, gbc);
        }

        // 3-Section Listes de Diffusion
        gbc.gridy = ligne++;
        lblListesDiffusion = new JLabel("Listes de Diffusion :");
        add(lblListesDiffusion, gbc);

        gbc.gridy = ligne++;
        btnNewListesDiffusion = new JButton("+ Listes de Diffusion");
        add(btnNewListesDiffusion, gbc);

        // Fausse données - Listes de diffusion
        List<String> nomsListes = List.of("Projet", "News");
        for (String nom : nomsListes) {
            gbc.gridy = ligne++;
            JButton btn = new JButton(nom);
            
            String nomListeActuel = nom;
            // Clic sur une liste de diffusion
            btn.addActionListener(e -> {
                if (listener != null) {
                    listener.clicListeDiffusion(nomListeActuel);
                }
            });
            
            add(btn, gbc);
        }

        // Ressort vertical
        gbc.gridy = ligne++;
        gbc.weighty = 1.0;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        add(filler, gbc);
    }
}