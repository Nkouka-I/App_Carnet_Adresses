package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import javax.swing.*;

/**
 * La classe <code>PanneauListeDiffusion</code> est la vue dédiée à une liste de
 * diffusion : son titre, un tableau de ses contacts et un bouton pour copier tous
 * les contacts. Sans liste sélectionnée, elle affiche un message d'accueil.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauListeDiffusion extends JPanel {

    /**
     * Construit un panneau vide, prêt à afficher une liste de diffusion.
     */
    public PanneauListeDiffusion() {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
    }

    /**
     * Affiche la page d'une liste de diffusion. Le contenu précédent du panneau est effacé.
     *
     * @param nomListe le nom de la liste de diffusion à afficher
     *                 (<code>null</code> pour afficher le message d'accueil des listes)
     */
    public void afficher(String nomListe) {
        removeAll(); // Efface tout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 		// 5 pixels de marge tout autour de chaque élément
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int l = 0;

        // Si on clique sur le bouton global de l'accordéon (nomListe == null)
        if (nomListe == null) {
            gbc.gridy = l++;
            JLabel lblTitre = new JLabel("Gestion des Listes de Diffusion");
            lblTitre.setFont(new Font("Arial", Font.BOLD, 16));
            add(lblTitre, gbc);

            gbc.gridy = l++;
            JLabel lblInfo = new JLabel("Sélectionnez une liste de diffusion dans le menu de gauche pour afficher ses détails.");
            add(lblInfo, gbc);

            revalidate();
            repaint();
            return;
        }

        // Titre de la liste
        gbc.gridy = l++;
        JLabel lblTitre = new JLabel("Liste : " + nomListe);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitre, gbc);

        // Tableau des contacts de la liste
        gbc.gridy = l++;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        String[] colonnes = {"Nom", "Prénom", "Adresse mail"};
        Object[][] donnees = {
            {"Dupont", "Jean", "jean.dupont@mail.com"},
            {"Curie", "Marie", "marie.curie@mail.com"}
        };
        JTable table = new JTable(donnees, colonnes);
        add(new JScrollPane(table), gbc);

        // Bouton "[ Copier tout ]" en bas à droite
        gbc.gridy = l++;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JButton btnCopierTout = new JButton("[ Copier tout ]");
        btnCopierTout.setBackground(new Color(70, 130, 180));
        btnCopierTout.setForeground(Color.WHITE);
        add(btnCopierTout, gbc);

        // Rafraîchit l'affichage
        revalidate();
        repaint();
    }
}
