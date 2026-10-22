package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * La classe <code>LigneListeDiffusion</code> représente graphiquement une ligne de contact
 * au sein d'une liste de diffusion, affichant son nom, son email et un bouton pour
 * copier son adresse individuellement.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class LigneListeDiffusion extends JPanel {

    /**
     * Construit une ligne de contact pour une liste de diffusion.
     *
     * @param nom le nom du contact
     * @param email l'adresse email du contact
     */
    public LigneListeDiffusion(String nom, String email) {
        setLayout(new GridBagLayout());
        setBackground(Style.FOND_LIGNE);
        Style.bordureLigne(this, false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;

        // Nom du contact
        gbc.gridx = 0;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(nom), gbc);

        // Adresse email
        gbc.gridx = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(email), gbc);

        // Bouton de copie individuel
        gbc.gridx = 2;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        JButton btnCopierContact = new JButton("Copier");

        btnCopierContact.addActionListener(e -> PressePapiers.copier(btnCopierContact, email));

        add(btnCopierContact, gbc);
    }
}
