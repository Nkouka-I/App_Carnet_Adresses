package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.FenetreFormulaireEtiquette;
import fr.iutfbleau.sae31.AtoZ.vue.FormulaireListener;

/**
 * La classe <code>ControleurFormulaireEtiquette</code> réagit à la validation du formulaire d'une
 * étiquette : elle crée une nouvelle étiquette, ou renomme une étiquette existante, avec le
 * libellé saisi par l'utilisateur.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class ControleurFormulaireEtiquette implements FormulaireListener<FenetreFormulaireEtiquette> {

    /**
     * Le contrôleur principal de l'application.
     */
    private final Controleur controleur;

    /**
     * L'étiquette à modifier, ou <code>null</code> si le formulaire sert à en créer une.
     */
    private final EtiquetteVue etiquetteAModifier;

    /**
     * Constructeur d'un contrôleur de formulaire destiné à la création d'une étiquette.
     *
     * @param controleur le contrôleur principal de l'application
     */
    public ControleurFormulaireEtiquette(Controleur controleur) { this(controleur, null); }

    /**
     * Constructeur d'un contrôleur de formulaire destiné à la création ou à la modification
     * d'une étiquette.
     *
     * @param controleur le contrôleur principal de l'application
     * @param etiquetteAModifier l'étiquette à modifier, ou <code>null</code> pour en créer une
     */
    public ControleurFormulaireEtiquette(Controleur controleur, EtiquetteVue etiquetteAModifier) {
        this.controleur = controleur;
        this.etiquetteAModifier = etiquetteAModifier;
    }

    /**
     * Récupère le libellé saisi dans le formulaire puis crée ou modifie l'étiquette.
     *
     * @param formulaire le formulaire d'étiquette qui vient d'être validé
     */
    @Override
    public void onValider(FenetreFormulaireEtiquette formulaire) {
        String libelle = formulaire.getLibelleSaisi();
        if (etiquetteAModifier == null) {
            controleur.creerEtiquette(libelle);
        } else {
            controleur.modifierEtiquette(etiquetteAModifier, libelle);
        }
    }

}