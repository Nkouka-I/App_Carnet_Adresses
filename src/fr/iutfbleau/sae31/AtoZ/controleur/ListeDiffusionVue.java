package fr.iutfbleau.sae31.AtoZ.controleur;

import java.util.List;

/**
 * L'interface <code>ListeDiffusionVue</code> décrit ce que la vue a besoin de connaître
 * d'une liste de diffusion. La classe du modèle représentant une liste doit l'implémenter.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public interface ListeDiffusionVue {

    /**
     * Renvoie le nom de la liste.
     *
     * @return le nom
     */
    String getNom();

    /**
     * Renvoie les critères d'appartenance à la liste.
     *
     * @return les critères (jamais <code>null</code>)
     */

    List<CritereVue> getCriteres();
}
