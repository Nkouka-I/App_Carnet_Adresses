package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.FenetreFormulaireListeDiffusion;
import fr.iutfbleau.sae31.AtoZ.vue.FormulaireListener;

public class ControleurFormulaireListe implements FormulaireListener<FenetreFormulaireListeDiffusion> {
    private final Controleur controleur;
    private final ListeDiffusionVue listeAModifier;

    public ControleurFormulaireListe(Controleur controleur) { this(controleur, null); }
    public ControleurFormulaireListe(Controleur controleur, ListeDiffusionVue listeAModifier) {
        this.controleur = controleur;
        this.listeAModifier = listeAModifier;
    }

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