package fr.iutfbleau.sae31.AtoZ.vue;

/**
 * L'interface <code>FormulaireListener</code> définit l'action à effectuer lorsqu'un
 * formulaire a été validé par l'utilisateur. Elle reçoit le formulaire pour en lire les
 * valeurs saisies.
 *
 * @param <T> le type du formulaire
 * @version 1.0
 * @author Nkouka Ildevert
 */
public interface FormulaireListener<T> {

    /**
     * Appelée lorsque l'utilisateur valide le formulaire.
     *
     * @param formulaire le formulaire validé
     */
    void onValider(T formulaire);
}
