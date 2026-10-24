package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.FenetreFormulaireListeDiffusion;
import fr.iutfbleau.sae31.AtoZ.vue.FormulaireListener;

/**
 * La classe <code>ControleurFormulaireListe</code> réagit à la validation du formulaire d'une liste
 * de diffusion : elle crée une nouvelle liste, ou modifie une liste existante, avec le nom et
 * les critères (étiquettes incluses ou exclues) saisis par l'utilisateur.
 *
 * @version 1.0
 * @author  Ildevert Nkouka
 */
public class ControleurFormulaireListe implements FormulaireListener<FenetreFormulaireListeDiffusion> {

    /**
     * Le contrôleur principal de l'application.
     */
    private final Controleur controleur;

    /**
     * La liste à modifier, ou <code>null</code> si le formulaire sert à en créer une.
     */
    private final ListeDiffusionVue listeAModifier;

    /**
     * Constructeur d'un contrôleur de formulaire destiné à la création d'une liste de diffusion.
     *
     * @param controleur le contrôleur principal de l'application
     */
    public ControleurFormulaireListe(Controleur controleur) { this(controleur, null); }

    /**
     * Constructeur d'un contrôleur de formulaire destiné à la création ou à la modification
     * d'une liste de diffusion.
     *
     * @param controleur le contrôleur principal de l'application
     * @param listeAModifier la liste à modifier, ou <code>null</code> pour en créer une
     */
    public ControleurFormulaireListe(Controleur controleur, ListeDiffusionVue listeAModifier) {
        this.controleur = controleur;
        this.listeAModifier = listeAModifier;
    }

    /**
     * Récupère le nom et les critères saisis dans le formulaire puis crée ou modifie la liste.
     *
     * @param formulaire le formulaire de liste de diffusion qui vient d'être validé
     */
    @Override
    public void onValider(FenetreFormulaireListeDiffusion formulaire) {
        String nom = formulaire.getNomListeSaisi();
        var criteres = formulaire.getCriteresList();

        if (listeAModifier == null) {
            controleur.creerListeDiffusion(nom, criteres);
        } else {
            controleur.modifierListeDiffusion(listeAModifier, nom, criteres);
        }
    }

}