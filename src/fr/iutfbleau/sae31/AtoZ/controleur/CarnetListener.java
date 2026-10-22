package fr.iutfbleau.sae31.AtoZ.controleur;

/**
 * L'interface <code>CarnetListener</code> définit le contrat d'un écouteur du carnet[cite: 11].
 */
@FunctionalInterface
public interface CarnetListener {
    void carnetModifie();
}