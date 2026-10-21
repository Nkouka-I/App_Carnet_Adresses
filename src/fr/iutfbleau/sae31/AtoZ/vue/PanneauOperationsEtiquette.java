package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;
import java.awt.Insets;
import java.util.List;
import javax.swing.JLabel;

/**
 * La classe <code>PanneauOperationsEtiquette</code> est le panneau d'opérations affiché
 * lorsqu'une ÉTIQUETTE est sélectionnée : il propose de la modifier ou de la supprimer
 * et liste les listes de diffusion où elle apparaît.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauOperationsEtiquette extends PanneauOperationsAbstrait {

    /**
     * Étiquette sélectionnée.
     */
    private final EtiquetteVue etiquette;

    /**
     * Construit le panneau et affiche ses éléments.
     *
     * @param controleur le contrôleur auquel les opérations sont demandées
     * @param etiquette l'étiquette sélectionnée
     */
    public PanneauOperationsEtiquette(Controleur controleur, EtiquetteVue etiquette) {
        super(controleur);
        this.etiquette = etiquette;

        construire();
    }

    /**
     * Construit le contenu du panneau : bouton "Modifier", titre explicatif, listes
     * où l'étiquette apparaît, puis bouton "Supprimer".
     */
    private void construire() {
        preparer();

        ajouterBoutonModifier("Modifier l'étiquette", e -> modifier());

        ajouterTitre(
            "L'étiquette \"" + etiquette.getNom() + "\"",
            JLabel.CENTER,
            new Insets(20, 10, 2, 10)
        );

        ajouterTitre(
            "apparaît dans les listes :",
            JLabel.CENTER,
            new Insets(0, 10, 5, 10)
        );

        List<ListeDiffusionVue> listes = getControleur().getListesUtilisant(etiquette);
        if (listes.isEmpty()) {
            ajouterTexte("(aucune liste)", new Insets(0, 20, 2, 10));
        }
        for (ListeDiffusionVue liste : listes) {
            ajouterTexte("• " + liste.getNom(), new Insets(0, 20, 2, 10));
        }

        ajouterBoutonSuppression(
            "Supprimer l'étiquette",
            "Voulez-vous vraiment supprimer l'étiquette \"" + etiquette.getNom() + "\" ?",
            "L'étiquette a bien été supprimée !",
            () -> getControleur().supprimerEtiquette(etiquette)
        );

        terminer();
    }

    /**
     * Ouvre le formulaire de modification de l'étiquette et, s'il est validé, demande
     * le renommage au contrôleur.
     */
    private void modifier() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
            getFenetreParente(),
            "Modifier l'étiquette",
            etiquette.getNom()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f ->
            getControleur().modifierEtiquette(etiquette, f.getLibelleSaisi())
        );
    }
}
