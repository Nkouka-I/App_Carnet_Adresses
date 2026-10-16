package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * La classe <code>PanneauGauche</code> est le panneau de navigation de gauche de la
 * fenêtre principale. Il propose les boutons de création (contact, étiquette, liste de
 * diffusion) ainsi que deux menus déroulants (accordéons) listant les étiquettes et
 * les listes de diffusion.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauGauche extends JPanel{

    /**
     * Bouton de création d'un nouveau contact.
     */
    private final JButton btnNewContact;

    /**
     * Bouton de création d'une nouvelle étiquette.
     */
    private final JButton btnNewEtiquette;

    /**
     * Bouton de création d'une nouvelle liste de diffusion.
     */
    private final JButton btnNewListesDiffusion;

    /**
     * Titre de l'application affiché en haut du panneau.
     */
    private final JLabel titreApp;

    /**
     * Bouton qui déplie ou replie la liste des étiquettes.
     */
    private final JButton btnEtiquettes;

    /**
     * Bouton qui déplie ou replie la liste des listes de diffusion.
     */
    private final JButton btnListesDiffusion;

    /**
     * Indique si la liste des étiquettes est actuellement dépliée.
     */
    private boolean etiquettesVisibles = false;

    /**
     * Indique si la liste des listes de diffusion est actuellement dépliée.
     */
    private boolean listesDiffusionVisibles = false;

    /**
     * Construit le panneau de gauche, crée ses boutons et affiche l'interface.
     *
     * @param listener l'écouteur à notifier lors d'un clic sur une étiquette ou une liste de diffusion
     */
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

    /**
     * Reconstruit entièrement le contenu du panneau selon l'état des menus déroulants.
     * Les étiquettes et les listes de diffusion ne sont affichées que si leur menu est déplié.
     *
     * @param listener l'écouteur à notifier lors d'un clic sur une étiquette ou une liste de diffusion
     */
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
