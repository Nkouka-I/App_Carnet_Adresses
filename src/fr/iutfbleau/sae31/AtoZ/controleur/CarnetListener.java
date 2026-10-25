package fr.iutfbleau.sae31.AtoZ.controleur;

/**
 * L'interface <code>CarnetListener</code> définit le contrat d'un écouteur du carnet :
 * elle est appelée à chaque fois que le contenu du carnet (contacts, étiquettes ou listes
 * de diffusion) a été modifié, afin que l'affichage puisse se mettre à jour.
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