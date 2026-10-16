package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.Frame;

/**
 * La classe <code>FenetreFormulaireEtiquette</code> est le formulaire de création et de
 * modification d'une étiquette. Il ne comporte qu'un champ : le libellé de l'étiquette.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class FenetreFormulaireEtiquette extends FenetreDialogueAbstraite {

    /**
     * Champ de saisie du libellé de l'étiquette.
     */
    private JTextField txtLibelle;

    /**
     * Vérifie la saisie du formulaire. Le libellé ne doit pas être vide et ne doit pas
     * dépasser 100 caractères. En cas d'erreur, un message est affiché et le formulaire
     * reste ouvert.
     */
    @Override
    protected void validerFormulaire() {
        String libelle = txtLibelle.getText().trim();

        if (libelle.isBlank()) {
            JOptionPane.showMessageDialog(this, "Erreur : Le nom de l'étiquette ne peut pas être vide.", "Nom invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (libelle.length() > 100) {
            JOptionPane.showMessageDialog(this, "Erreur : Le nom de l'étiquette ne peut pas dépasser 100 caractères.", "Nom trop long", JOptionPane.ERROR_MESSAGE);
            return;
        }

        valide = true;
        dispose();
    }

    /**
     * Construit le formulaire et pré-remplit le libellé.
     *
     * @param parent la fenêtre parente
     * @param titre le titre de la boîte de dialogue
     * @param libelleInitial le libellé à afficher au départ (vide pour une création)
     */
    public FenetreFormulaireEtiquette(Frame parent, String titre, String libelleInitial) {
        super(parent, titre, 320, 180);

        int ligne = 0;

        // Libellé de l'étiquette
        gbc.gridy = ligne++;
        add(new JLabel("Nom de l'étiquette :"), gbc);
        
        gbc.gridy = ligne++;
        txtLibelle = new JTextField(libelleInitial, 20);
        add(txtLibelle, gbc);

        // Boutons de validation
        ajouterBoutonsValidation(ligne);
    }

    /**
     * Renvoie le libellé saisi, sans les espaces en début et fin de chaîne.
     *
     * @return le libellé de l'étiquette
     */
    public String getLibelleSaisi() {
        return txtLibelle.getText().trim();
    }
}
