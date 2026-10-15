package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

public class FenetreFormulaireContact extends FenetreDialogueAbstraite {
    
    private JTextField txtNom;
    private JTextField txtEmail;
    private List<JCheckBox> listeCheckBoxEtiquettes;

    public FenetreFormulaireContact(Frame parent, String titre, String nomInitial, String emailInitial, 
                                  List<String> toutesLesEtiquettes, List<String> etiquettesDuContact) {
        super(parent, titre, 400, 360);

        int ligne = 0;

        // Nom
        gbc.gridy = ligne++;
        add(new JLabel("Nom :"), gbc);
        gbc.gridy = ligne++;
        txtNom = new JTextField(nomInitial, 20);
        add(txtNom, gbc);

        // Email
        gbc.gridy = ligne++;
        add(new JLabel("Email :"), gbc);
        gbc.gridy = ligne++;
        txtEmail = new JTextField(emailInitial, 20);
        add(txtEmail, gbc);

        // Étiquettes  à cocher
        gbc.gridy = ligne++;
        add(new JLabel("Étiquettes :"), gbc);
        
        JPanel panelEtiquettes = new JPanel(new GridLayout(0, 2, 5, 5));
        listeCheckBoxEtiquettes = new ArrayList<>();
        
        if (toutesLesEtiquettes != null) {
            for (String etiquette : toutesLesEtiquettes) {
                JCheckBox cb = new JCheckBox(etiquette);
                if (etiquettesDuContact != null && etiquettesDuContact.contains(etiquette)) {
                    cb.setSelected(true);
                }
                listeCheckBoxEtiquettes.add(cb);
                panelEtiquettes.add(cb);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(panelEtiquettes);
        scrollPane.setPreferredSize(new Dimension(320, 80));
        gbc.gridy = ligne++;
        add(scrollPane, gbc);

        // Boutons de validation de base
        ajouterBoutonsValidation(ligne);
    }

    @Override
    protected void validerFormulaire() {
        String nom = txtNom.getText().trim();
        String email = txtEmail.getText().trim();

        // Validation du Nom
        if (nom.isBlank()) {
            JOptionPane.showMessageDialog(this, "Erreur : Le nom ne peut pas être vide.", "Nom invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nom.contains("\n") || nom.contains("\r")) {
            JOptionPane.showMessageDialog(this, "Erreur : Le nom ne peut pas contenir de saut de ligne.", "Nom invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean contientCaractereValide = nom.chars().anyMatch(Character::isLetterOrDigit);
        if (!contientCaractereValide) {
            JOptionPane.showMessageDialog(this, "Erreur : Le nom doit contenir au moins une lettre ou un chiffre.", "Nom invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validation de l'Email
        if (email.isBlank()) {
            JOptionPane.showMessageDialog(this, "Erreur : L'email ne peut pas être vide.", "Email invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.contains("@")) {
            JOptionPane.showMessageDialog(this, "Erreur : L'adresse email doit contenir un '@'.", "Email invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (email.chars().anyMatch(Character::isWhitespace)) {
            JOptionPane.showMessageDialog(this, "Erreur : L'adresse email ne peut pas contenir de caractères blancs.", "Email invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (email.getBytes(StandardCharsets.UTF_8).length > 254) {
            JOptionPane.showMessageDialog(this, "Erreur : L'adresse email ne doit pas dépasser 254 octets.", "Email invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }

        valide = true;
        dispose();
    }

    public String getNomSaisi() { return txtNom.getText().trim(); }
    public String getEmailSaisi() { return txtEmail.getText().trim(); }
    
    public List<String> getEtiquettesSelectionnees() {
        List<String> selection = new ArrayList<>();
        for (JCheckBox cb : listeCheckBoxEtiquettes) {
            if (cb.isSelected()) {
                selection.add(cb.getText());
            }
        }
        return selection;
    }
}