package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.*;

public abstract class FenetreDialogueAbstraite extends JDialog {
    
    protected boolean valide = false;
    protected GridBagConstraints gbc;

    public FenetreDialogueAbstraite(Frame parent, String titre, int largeur, int hauteur) {
        super(parent, titre, true); // bloquant

        setSize(largeur, hauteur);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
    }

    /**
     * Crée et ajoute le panneau standard des boutons "Annuler" et "Valider" en bas du formulaire.
     */
    protected void ajouterBoutonsValidation(int ligne) {
        gbc.gridy = ligne;
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnAnnuler = new JButton("Annuler");
        JButton btnValider = new JButton("Valider");
        
        btnValider.setBackground(new Color(70, 130, 180));
        btnValider.setForeground(Color.WHITE);

        panelBoutons.add(btnAnnuler);
        panelBoutons.add(btnValider);
        add(panelBoutons, gbc);

        // Action par défaut pour Annuler
        btnAnnuler.addActionListener(e -> {
            valide = false;
            dispose();
        });

        // Action de validation gérée par les classes fille
        btnValider.addActionListener(e -> validerFormulaire());
    }

    /**
     * Chaque classe fille implémente sa propre logique de validation.
     */
    protected abstract void validerFormulaire();

    public boolean isValide() {
        return valide;
    }
}