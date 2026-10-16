package fr.iutfbleau.sae31.AtoZ.vue;

/**
 * L'interface <code>FormulaireListener</code> définit le contrat d'un écouteur de
 * validation de formulaire. Elle est appelée uniquement lorsque l'utilisateur a
 * validé le formulaire (et non lorsqu'il l'a annulé).
 *
 * @version 1.0
 * @author Nkouka Ildevert
 * @param <T> le type de formulaire écouté
 */
public interface FormulaireListener<T extends FenetreDialogueAbstraite> {

    /**
     * Appelée après la validation d'un formulaire, afin de récupérer les valeurs saisies.
     *
     * @param formulaire le formulaire qui vient d'être validé
     */
    void onValider(T formulaire);
}
