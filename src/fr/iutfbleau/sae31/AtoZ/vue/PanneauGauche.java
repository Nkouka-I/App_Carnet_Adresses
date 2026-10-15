package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PanneauGauche extends JPanel{

    private final JButton btnNewContact;
    private final JButton btnNewEtiquette;
    private final JButton btnNewListesDiffusion;

    private final JLabel titreApp;
    private final JButton btnEtiquettes;
    private final JButton btnListesDiffusion;

    private boolean etiquettesVisibles = false;
    private boolean listesDiffusionVisibles = false;

    public PanneauGauche(NavigationListener listener){

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(220, 0)); // Limité à une largeur fixe
        setBackground(new Color(245, 245, 245));

        titreApp = new JLabel("AtoZ Carnet", JLabel.CENTER);
        btnNewContact = new JButton("+ new Contact");
        btnNewEtiquette = new JButton("+ new Etiquette");
        btnNewListesDiffusion = new JButton("+ Listes de Diffusion");

        btnEtiquettes = new JButton();
        btnEtiquettes.addActionListener(e -> {
            etiquettesVisibles = !etiquettesVisibles;
            rafraichirInterface(listener);
        });

        btnListesDiffusion = new JButton();
        btnListesDiffusion.addActionListener(e -> {
            listesDiffusionVisibles = !listesDiffusionVisibles;
            rafraichirInterface(listener);
        });

        rafraichirInterface(listener);
    }

    public void rafraichirInterface(NavigationListener listener) {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; 
        
        int ligne = 0;

        // Titre
        gbc.gridy = ligne++;
        add(titreApp, gbc);

        // 1-Bouton "+ new Contact" en premier
        gbc.gridy = ligne++;
        add(btnNewContact, gbc);

        // 2-Bouton "+ new Etiquette" avec bouton accordéon
        gbc.gridy = ligne++;
        add(btnNewEtiquette, gbc);

        btnEtiquettes.setText(etiquettesVisibles ? "▼ Étiquettes :" : "▶ Étiquettes :");
        gbc.gridy = ligne++;
        add(btnEtiquettes, gbc);

        // Fausse données - Étiquettes (affichées uniquement si visibles)
        if (etiquettesVisibles) {
            List<String> nomsEtiquettes = List.of("Client", "Famille", "Travail", "VIP");
            for (String nom : nomsEtiquettes) {
                gbc.gridy = ligne++;
                JButton btn = new JButton(nom);
                
                String nomEtiquette = nom;
                // Clic sur une étiquette spécifique
                btn.addActionListener(e -> {
                    if (listener != null) {
                        listener.clicContactEtiquette(nomEtiquette); 
                    }
                });
                
                add(btn, gbc);
            }
        }

        // 3-Bouton "+ new ListesDiffusion" avec bouton accordéon
        gbc.gridy = ligne++;
        add(btnNewListesDiffusion, gbc);

        btnListesDiffusion.setText(listesDiffusionVisibles ? "▼ Listes de Diffusion :" : "▶ Listes de Diffusion :");
        gbc.gridy = ligne++;
        add(btnListesDiffusion, gbc);

        if (listesDiffusionVisibles) {
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
        }

        // Pousse vers le haut
        gbc.gridy = ligne++;
        gbc.weighty = 1.0;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        add(filler, gbc);

        // Rafraîchissement visuel
        revalidate();
        repaint();
    }
}