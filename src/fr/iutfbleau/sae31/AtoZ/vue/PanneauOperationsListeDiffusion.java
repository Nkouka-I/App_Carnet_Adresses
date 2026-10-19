package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.Insets;
import java.util.List;
import javax.swing.JLabel;

/**
 * La classe <code>PanneauOperationsListeDiffusion</code> est le panneau d'opérations
 * affiché lorsqu'une LISTE DE DIFFUSION est sélectionnée : il propose de la modifier ou
 * de la supprimer et introduit la liste de ses critères.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauOperationsListeDiffusion extends PanneauOperationsAbstrait {

    /**
     * Nom actuel de la liste de diffusion.
     */
    private String nomListe;

    /**
     * Toutes les étiquettes disponibles pour définir un critère.
     */
    private final List<String> toutesLesEtiquettes;

    /**
     * Critères actuellement définis pour la liste.
     */
    private List<CritereData> criteresActuels;

    /**
     * Construit le panneau et affiche ses éléments.
     *
     * @param apresSuppression l'action à exécuter après une suppression réussie
     * @param nomListe le nom de la liste de diffusion sélectionnée
     * @param toutesLesEtiquettes toutes les étiquettes disponibles pour définir un critère
     * @param criteresActuels les critères actuellement définis pour la liste
     */
    public PanneauOperationsListeDiffusion(Runnable apresSuppression, String nomListe,
                                           List<String> toutesLesEtiquettes,
                                           List<CritereData> criteresActuels) {
        super(apresSuppression);
        this.nomListe = nomListe;
        this.toutesLesEtiquettes = toutesLesEtiquettes;
        this.criteresActuels = criteresActuels;

        construire();
    }

    /**
     * Construit (ou reconstruit) le contenu du panneau : bouton "Modifier", titre des
     * critères, puis bouton "Supprimer".
     */
    private void construire() {
        preparer();

        ajouterBoutonModifier("Modifier la liste", e -> modifier());

        ajouterTitre(
            "<html><b>Critères de la liste " + nomListe + " :</b><br></html>",
            JLabel.CENTER,
            new Insets(10, 10, 5, 10)
        );

        /*TODO Boucle que ajoute les differents noms de critere*/

        ajouterBoutonSuppression(
            "Supprimer la liste de diffusion",
            "Voulez-vous vraiment supprimer la liste de diffusion \"" + nomListe + "\" ?",
            "La liste de diffusion a bien été supprimé !",
            () -> {
                // TODO: Appeler le contrôleur
                System.out.println("La liste de diffusion a bien été supprimé");
            }
        );

        terminer();
    }

    /**
     * Ouvre le formulaire de modification de la liste et, s'il est validé, met à jour
     * le nom et les critères affichés.
     */
    private void modifier() {
        FenetreFormulaireListeDiffusion form = new FenetreFormulaireListeDiffusion(
            getFenetreParente(), 
            "Modifier la liste de diffusion", 
            nomListe, 
            toutesLesEtiquettes, 
            criteresActuels
        );

        ouvrirFormulaire(form, f -> {
            nomListe = f.getNomListeSaisi();
            criteresActuels = f.getCriteresList();

            // TODO: Appeler le contrôleur / BDD pour mettre à jour
            System.out.println("Liste mise à jour : " + nomListe);

            // Rafraîchir l'affichage
            construire();
        });
    }
}
