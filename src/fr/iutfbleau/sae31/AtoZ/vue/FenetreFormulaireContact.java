package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
     * Case à cocher de chacune des étiquettes disponibles, dans l'ordre d'affichage.
     */
    private final Map<EtiquetteVue, JCheckBox> casesEtiquettes = new LinkedHashMap<>();

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
                                  List<EtiquetteVue> toutesLesEtiquettes, List<EtiquetteVue> etiquettesDuContact) {
        super(parent, titre, 400, 360);

        // Nom
        ajouter(new JLabel("Nom :"));
        txtNom = new JTextField(nomInitial, 20);
        ajouter(txtNom);

        // Email
        ajouter(new JLabel("Email :"));
        txtEmail = new JTextField(emailInitial, 20);
        ajouter(txtEmail);

        // Étiquettes à cocher
        ajouter(new JLabel("Étiquettes :"));

        JPanel panelEtiquettes = new JPanel(new GridLayout(0, 2, 5, 5));

        if (toutesLesEtiquettes != null) {
            for (EtiquetteVue etiquette : toutesLesEtiquettes) {
                JCheckBox cb = new JCheckBox(etiquette.getNom());
                cb.setSelected(etiquettesDuContact != null && etiquettesDuContact.contains(etiquette));
                casesEtiquettes.put(etiquette, cb);
                panelEtiquettes.add(cb);
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelEtiquettes);
        scrollPane.setPreferredSize(new Dimension(320, 80));
        ajouter(scrollPane);

        // Boutons de validation
        ajouterBoutonsValidation();
    }

    /**
     * Vérifie la saisie du formulaire. Le nom ne doit pas être vide et doit comporter au
     * moins une lettre ou un chiffre. L'email ne doit pas être vide, doit contenir un '@',
     * aucun caractère blanc, et ne pas dépasser 254 octets. En cas d'erreur, un message est
     * affiché et le formulaire reste ouvert.
     */
    @Override
    protected void validerFormulaire() {
        String nom = getNomSaisi();
        String email = getEmailSaisi();

        // Validation du Nom
        if (nom.isBlank()) {
            afficherErreur("Nom invalide", "Le nom ne peut pas être vide.");
            return;
        }
        if (nom.chars().noneMatch(Character::isLetterOrDigit)) {
            afficherErreur("Nom invalide", "Le nom doit contenir au moins une lettre ou un chiffre.");
            return;
        }

        // Validation de l'Email
        if (email.isBlank()) {
            afficherErreur("Email invalide", "L'email ne peut pas être vide.");
            return;
        }
        if (!email.contains("@")) {
            afficherErreur("Email invalide", "L'adresse email doit contenir un '@'.");
            return;
        }
        if (email.chars().anyMatch(Character::isWhitespace)) {
            afficherErreur("Email invalide", "L'adresse email ne peut pas contenir de caractères blancs.");
            return;
        }
        if (email.getBytes(StandardCharsets.UTF_8).length > 254) {
            afficherErreur("Email invalide", "L'adresse email ne doit pas dépasser 254 octets.");
            return;
        }

        validerEtFermer();
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
     * @return les étiquettes sélectionnées
     */
    public List<EtiquetteVue> getEtiquettesSelectionnees() {
        List<EtiquetteVue> selection = new ArrayList<>();
        for (Map.Entry<EtiquetteVue, JCheckBox> entree : casesEtiquettes.entrySet()) {
            if (entree.getValue().isSelected()) {
                selection.add(entree.getKey());
            }
        }
        return selection;
    }
}
