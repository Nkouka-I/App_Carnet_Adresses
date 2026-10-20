package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.Insets;
import javax.swing.JLabel;

/**
 * La classe <code>PanneauOperationsEtiquette</code> est le panneau d'opérations affiché
 * lorsqu'une ÉTIQUETTE est sélectionnée : il propose de la modifier ou de la supprimer
 * et introduit la liste des listes de diffusion où elle apparaît.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauOperationsEtiquette extends PanneauOperationsAbstrait {

    /**
     * Libellé actuel de l'étiquette.
     */
    private String nomEtiquette;

    /**
     * Construit le panneau et affiche ses éléments.
     *
     * @param apresSuppression l'action à exécuter après une suppression réussie
     * @param nomEtiquette le libellé de l'étiquette sélectionnée
     */
    public PanneauOperationsEtiquette(Runnable apresSuppression, String nomEtiquette) {
        super(apresSuppression);
        this.nomEtiquette = nomEtiquette;

        construire();
    }

    /**
     * Construit (ou reconstruit) le contenu du panneau : bouton "Modifier", titre
     * explicatif, puis bouton "Supprimer".
     */
    private void construire() {
        preparer();

        ajouterBoutonModifier("Modifier l'étiquette", e -> modifier());

        ajouterTitre(
            "L'étiquette \"" + nomEtiquette + "\"",
            JLabel.CENTER,
            new Insets(20, 10, 2, 10)
        );

        ajouterTitre(
            "apparaît dans les listes :",
            JLabel.CENTER,
            new Insets(0, 10, 5, 10)
        );

        /*TODO Boucle que ajoute les differents noms de listes*/

        ajouterBoutonSuppression(
            "Supprimer l'étiquette",
            "Voulez-vous vraiment supprimer l'étiquette \"" + nomEtiquette + "\" ?",
            "L'étiquette a bien été supprimée !",
            () -> {
                // TODO: Appeler le contrôleur pour supprimer l'étiquette
                System.out.println("l'étiquette a bien été supprimé...");
            }
        );

        terminer();
    }

    /**
     * Ouvre le formulaire de modification de l'étiquette et, s'il est validé, met à jour
     * le libellé affiché.
     */
    private void modifier() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
            getFenetreParente(),
            "Modifier l'étiquette",
            nomEtiquette
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            nomEtiquette = f.getLibelleSaisi();

            // TODO: Appeler le contrôleur
            System.out.println("Étiquette mise à jour : " + nomEtiquette);

            // Rafraîchir l'affichage
            construire();
        });
    }
}
