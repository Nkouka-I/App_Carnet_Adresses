package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

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
     * Étiquettes proposées, dans l'ordre d'affichage.
     */
    private final List<EtiquetteVue> etiquettesProposees = new ArrayList<>();

    /**
     * Case à cocher de chaque étiquette proposée (même ordre que {@link #etiquettesProposees}).
     */
    private final List<JCheckBox> casesEtiquettes = new ArrayList<>();

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
        super(parent, titre, 400, 480);

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
                etiquettesProposees.add(etiquette);
                casesEtiquettes.add(cb);
                panelEtiquettes.add(cb);
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelEtiquettes);
        scrollPane.setPreferredSize(new Dimension(320, 200));
        scrollPane.setMinimumSize(new Dimension(320, 200));
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
        if (nom.indexOf('\n') >= 0 || nom.indexOf('\r') >= 0) {
            afficherErreur("Nom invalide", "Le nom ne peut pas contenir de saut de ligne.");
            return;
        }
        if (!contientLettreOuChiffre(nom)) {
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
        if (contientBlanc(email)) {
            afficherErreur("Email invalide", "L'adresse email ne peut pas contenir de caractères blancs.");
            return;
        }
        if (longueurUtf8(email) > 254) {
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
        for (int i = 0; i < etiquettesProposees.size(); i++) {
            if (casesEtiquettes.get(i).isSelected()) {
                selection.add(etiquettesProposees.get(i));
            }
        }
        return selection;
    }

    /**
     * Indique si un texte contient au moins une lettre ou un chiffre.
     *
     * @param texte le texte à examiner
     * @return <code>true</code> s'il y a au moins une lettre ou un chiffre
     */
    private static boolean contientLettreOuChiffre(String texte) {
        for (int i = 0; i < texte.length(); i++) {
            if (Character.isLetterOrDigit(texte.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indique si un texte contient un caractère blanc (espace, tabulation, saut de ligne...).
     *
     * @param texte le texte à examiner
     * @return <code>true</code> s'il y a au moins un caractère blanc
     */
    private static boolean contientBlanc(String texte) {
        for (int i = 0; i < texte.length(); i++) {
            if (Character.isWhitespace(texte.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calcule la taille d'un texte en octets une fois encodé en UTF-8 (un caractère occupe
     * de 1 à 4 octets selon sa valeur).
     *
     * @param texte le texte à mesurer
     * @return le nombre d'octets
     */
    private static int longueurUtf8(String texte) {
        int octets = 0;
        int i = 0;
        while (i < texte.length()) {
            int code = texte.codePointAt(i);
            if (code < 0x80) {
                octets += 1;
            } else if (code < 0x800) {
                octets += 2;
            } else if (code < 0x10000) {
                octets += 3;
            } else {
                octets += 4;
            }
            i += Character.charCount(code);
        }
        return octets;
    }
}
