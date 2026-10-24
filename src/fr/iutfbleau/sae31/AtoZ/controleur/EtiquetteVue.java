package fr.iutfbleau.sae31.AtoZ.controleur;

/**
 * L'interface <code>EtiquetteVue</code> décrit ce que la vue a besoin de connaître d'une
 * étiquette. La classe du modèle représentant une étiquette doit l'implémenter. La vue
 * identifie les étiquettes par leur identité (<code>equals</code>), jamais par leur nom.
 *
 * @version 1.0
 * @author Ildevert Nkouka
 */
public interface EtiquetteVue {

    /**
     * Renvoie le libellé de l'étiquette.
     *
     * @return le nom de l'étiquette
     */
    String getNom();
}
