package fr.iutfbleau.sae31.AtoZ.vue;

public interface FormulaireListener<T extends FenetreDialogueAbstraite> {
    void onValider(T formulaire);
}