package fr.iutfbleau.sae31.AtoZ.controleur;

/**
 * L'interface <code>CarnetListener</code> définit le contrat d'un écouteur du carnet.
 * Elle est appelée après chaque modification (création, modification ou suppression d'un
 * contact, d'une étiquette ou d'une liste de diffusion), pour que la vue se rafraîchisse.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
@FunctionalInterface
public interface CarnetListener {

    /**
     * Appelée après toute modification du carnet.
     */
    void carnetModifie();
}
