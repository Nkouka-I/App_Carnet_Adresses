package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.*;

/**
 * La classe <code>FenetreDialogueAbstraite</code> est la base commune de tous les
 * formulaires de l'application. Il s'agit d'une boîte de dialogue modale (bloquante)
 * qui fournit un <code>GridBagLayout</code> préconfiguré et les boutons standards
 * "Annuler" et "Valider". Chaque classe fille définit sa propre logique de validation.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public abstract class FenetreDialogueAbstraite extends JDialog {

    /**
     * Indique si le formulaire a été validé (<code>true</code>) ou annulé (<code>false</code>).
     */
    protected boolean valide = false;

    /**
     * Contraintes de placement partagées avec les classes filles pour construire le formulaire.
     */
    protected GridBagConstraints gbc;

    /**
     * Construit une boîte de dialogue modale centrée sur sa fenêtre parente.
     *
     * @param parent la fenêtre parente
     * @param titre le titre de la boîte de dialogue
     * @param largeur la largeur de la boîte de dialogue en pixels
     * @param hauteur la hauteur de la boîte de dialogue en pixels
     */
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
     * Le bouton "Annuler" ferme la fenêtre sans valider, le bouton "Valider" appelle
     * {@link #validerFormulaire()}.
     *
     * @param ligne l'indice de la ligne de la grille où placer les boutons
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
     * En cas de saisie correcte, elle doit positionner <code>valide</code> à
     * <code>true</code> puis fermer la fenêtre.
     */
    protected abstract void validerFormulaire();

    /**
     * Indique si le formulaire a été validé par l'utilisateur.
     *
     * @return <code>true</code> si le formulaire a été validé, <code>false</code> s'il a été annulé
     */
    public boolean isValide() {
        return valide;
    }
}
