package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.FenetreFormulaireEtiquette;
import fr.iutfbleau.sae31.AtoZ.vue.FormulaireListener;

public class ControleurFormulaireEtiquette implements FormulaireListener<FenetreFormulaireEtiquette> {
    private final Controleur controleur;
    private final EtiquetteVue etiquetteAModifier;

    public ControleurFormulaireEtiquette(Controleur controleur) { this(controleur, null); }
    public ControleurFormulaireEtiquette(Controleur controleur, EtiquetteVue etiquetteAModifier) {
        this.controleur = controleur;
        this.etiquetteAModifier = etiquetteAModifier;
    }

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