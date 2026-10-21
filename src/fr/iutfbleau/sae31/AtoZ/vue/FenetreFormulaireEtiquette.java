package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.JLabel;
import javax.swing.JTextField;
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
     * Nombre maximal de caractères du libellé d'une étiquette.
     */
    private static final int LONGUEUR_MAX_LIBELLE = 100;

    /**
     * Champ de saisie du libellé de l'étiquette.
     */
    private JTextField txtLibelle;

    /**
     * Construit le formulaire et pré-remplit le libellé.
     *
     * @param parent la fenêtre parente
     * @param titre le titre de la boîte de dialogue
     * @param libelleInitial le libellé à afficher au départ (vide pour une création)
     */
    public FenetreFormulaireEtiquette(Frame parent, String titre, String libelleInitial) {
        super(parent, titre, 320, 180);

        // Libellé de l'étiquette
        ajouter(new JLabel("Nom de l'étiquette :"));
        txtLibelle = new JTextField(libelleInitial, 20);
        ajouter(txtLibelle);

        // Boutons de validation
        ajouterBoutonsValidation();
    }

    /**
     * Vérifie la saisie du formulaire. Le libellé ne doit pas être vide et ne doit pas
     * dépasser 100 caractères. En cas d'erreur, un message est affiché et le formulaire
     * reste ouvert.
     */
    @Override
    protected void validerFormulaire() {
        String libelle = getLibelleSaisi();

        if (libelle.isBlank()) {
            afficherErreur("Nom invalide", "Le nom de l'étiquette ne peut pas être vide.");
            return;
        }
        if (libelle.length() > LONGUEUR_MAX_LIBELLE) {
            afficherErreur("Nom trop long", "Le nom de l'étiquette ne peut pas dépasser " + LONGUEUR_MAX_LIBELLE + " caractères.");
            return;
        }

        validerEtFermer();
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
