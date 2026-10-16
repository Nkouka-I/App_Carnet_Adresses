package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

/**
 * La classe <code>FenetreFormulaireContact</code> est le formulaire de création et de
 * modification d'un contact. Il permet de saisir un nom, une adresse email et de
 * cocher les étiquettes associées au contact.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class FenetreFormulaireContact extends FenetreDialogueAbstraite {

    /**
     * Champ de saisie du nom du contact.
     */
    private JTextField txtNom;

    /**
     * Champ de saisie de l'adresse email du contact.
     */
    private JTextField txtEmail;

    /**
     * Cases à cocher correspondant à chacune des étiquettes disponibles.
     */
    private List<JCheckBox> listeCheckBoxEtiquettes;

    /**
     * Construit le formulaire et pré-remplit ses champs.
     *
     * @param parent la fenêtre parente
     * @param titre le titre de la boîte de dialogue
     * @param nomInitial le nom à afficher au départ (vide pour une création)
     * @param emailInitial l'email à afficher au départ (vide pour une création)
     * @param toutesLesEtiquettes toutes les étiquettes proposées à la sélection
     * @param etiquettesDuContact les étiquettes à cocher au départ
     */
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

    /**
     * Vérifie la saisie du formulaire. Le nom ne doit pas être vide, ni contenir de saut
     * de ligne, et doit comporter au moins une lettre ou un chiffre. L'email ne doit pas
     * être vide, doit contenir un '@', aucun caractère blanc, et ne pas dépasser 254 octets.
     * En cas d'erreur, un message est affiché et le formulaire reste ouvert.
     */
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

    /**
     * Renvoie le nom saisi, sans les espaces en début et fin de chaîne.
     *
     * @return le nom du contact
     */
    public String getNomSaisi() { return txtNom.getText().trim(); }

    /**
     * Renvoie l'adresse email saisie, sans les espaces en début et fin de chaîne.
     *
     * @return l'email du contact
     */
    public String getEmailSaisi() { return txtEmail.getText().trim(); }

    /**
     * Renvoie la liste des étiquettes cochées dans le formulaire.
     *
     * @return les libellés des étiquettes sélectionnées
     */
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
