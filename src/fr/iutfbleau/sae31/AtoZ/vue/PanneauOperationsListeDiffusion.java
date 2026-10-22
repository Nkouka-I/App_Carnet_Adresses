package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.CritereVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;
import java.awt.Insets;
import javax.swing.JLabel;

/**
 * La classe <code>PanneauOperationsListeDiffusion</code> est le panneau d'opérations
 * affiché lorsqu'une LISTE DE DIFFUSION est sélectionnée : il propose de la modifier ou
 * de la supprimer et liste ses critères.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauOperationsListeDiffusion extends PanneauOperationsAbstrait {

    /**
     * Liste de diffusion sélectionnée.
     */
    private final ListeDiffusionVue liste;

    /**
     * Construit le panneau et affiche ses éléments.
     *
     * @param controleur le contrôleur auquel les opérations sont demandées
     * @param liste la liste de diffusion sélectionnée
     */
    public PanneauOperationsListeDiffusion(Controleur controleur, ListeDiffusionVue liste) {
        super(controleur);
        this.liste = liste;

        construire();
    }

    /**
     * Construit le contenu du panneau : bouton "Modifier", titre et liste des
     * critères, puis bouton "Supprimer".
     */
    private void construire() {
        preparer();

        ajouterBoutonModifier("Modifier la liste", e -> modifier());

        ajouterTitre(
            "Critères de la liste ",
            JLabel.CENTER,
            new Insets(10, 10, 5, 10)
        );

        ajouterTitre(
            liste.getNom() + " :",
            JLabel.CENTER,
            new Insets(0, 10, 5, 10)  // Moins de marge en haut
        );

        for (CritereVue critere : liste.getCriteres()) {
            ajouterTexte("• " + critere, new Insets(0, 20, 2, 10));
        }

        ajouterBoutonSuppression(
            "Supprimer la liste de diffusion",
            "Voulez-vous vraiment supprimer la liste de diffusion \"" + liste.getNom() + "\" ?",
            () -> getControleur().supprimerListeDiffusion(liste)
        );

        terminer();
    }

    /**
     * Ouvre le formulaire de modification de la liste et, s'il est validé, demande la
     * modification au contrôleur.
     */
    private void modifier() {
        FenetreFormulaireListeDiffusion form = new FenetreFormulaireListeDiffusion(
            getFenetreParente(),
            "Modifier la liste de diffusion",
            liste.getNom(),
            getControleur().getEtiquettes(),
            liste.getCriteres()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f ->
            getControleur().modifierListeDiffusion(liste, f.getNomListeSaisi(), f.getCriteresList())
        );
    }
}
