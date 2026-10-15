import javax.swing.*;
import java.awt.*;

public class FenetreFormulaire extends JDialog {
    
    private JTextField txtNom;
    private JTextField txtEmail;
    private JTextField txtEtiquette;
    private boolean valide = false;

    public FenetreFormulaire(Frame parent, String titre, String nomInitial, String emailInitial, String etiquetteInitiale){
        super(parent, titre, true); //bloquant

        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

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

        // Étiquette
        gbc.gridy = ligne++;
        add(new JLabel("Étiquette :"), gbc);
        gbc.gridy = ligne++;
        txtEtiquette = new JTextField(etiquetteInitiale, 20);
        add(txtEtiquette, gbc);

        gbc.gridy = ligne++;
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAnnuler = new JButton("Annuler");
        JButton btnValider = new JButton("Valider");
        btnValider.setBackground(new Color(70, 130, 180));
        btnValider.setForeground(Color.WHITE);

        panelBoutons.add(btnAnnuler);
        panelBoutons.add(btnValider);
        add(panelBoutons, gbc);

        // Actions des boutons
        btnAnnuler.addActionListener(e -> {
            valide = false;
            dispose(); // Ferme la boîte
        });

        btnValider.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            String email = txtEmail.getText().trim();

            // 1. Validation du Nom
            if (nom.isBlank()) {
                JOptionPane.showMessageDialog(this, 
                    "Erreur : Le nom ne peut pas être vide.", 
                    "Nom invalide", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (nom.contains("\n") || nom.contains("\r")) {
                JOptionPane.showMessageDialog(this, "Erreur : Le nom ne peut pas contenir de saut de ligne.", 
                "Nom invalide", 
                JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Vérification qu'il contient au moins une lettre ou un chiffre
            boolean contientCaractereValide = nom.chars().anyMatch(Character::isLetterOrDigit);
            if (!contientCaractereValide) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Erreur : Le nom doit contenir au moins une lettre ou un chiffre.", 
                    "Nom invalide",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Validation de l'Email
            if (email.isBlank()) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Erreur : L'email ne peut pas être vide.", 
                    "Email invalide", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Erreur : L'adresse email doit contenir un '@'.", 
                    "Email invalide", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Vérification des caractères blancs (espaces, tabulations...)
            if (email.chars().anyMatch(Character::isWhitespace)) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Erreur : L'adresse email ne peut pas contenir de caractères blancs.", 
                    "Email invalide", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Vérification de la taille en octets (maximum 254 octets)
            if (email.getBytes(java.nio.charset.StandardCharsets.UTF_8).length > 254) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Erreur : L'adresse email ne doit pas dépasser 254 octets.", 
                    "Email invalide", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            valide = true;
            dispose();
        });
    }

    // Méthodes pour récupérer les saisies de l'utilisateur après fermeture
    public boolean isValide() {
        return valide;
    }

    public String getNomSaisi() {
        return txtNom.getText().trim();
    }

    public String getEmailSaisi() {
        return txtEmail.getText().trim();
    }

    public String getEtiquetteSaisie() {
        return txtEtiquette.getText().trim();
    }
}