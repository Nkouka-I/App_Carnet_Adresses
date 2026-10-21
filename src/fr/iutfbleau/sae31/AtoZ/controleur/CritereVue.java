package fr.iutfbleau.sae31.AtoZ.controleur;

/**
 * L'interface <code>CritereVue</code> décrit ce que la vue a besoin de connaître d'un
 * critère de liste de diffusion : une étiquette que les contacts doivent posséder
 * (critère inclus) ou ne pas posséder (critère exclu).
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public interface CritereVue {

    /**
     * Renvoie l'étiquette concernée par le critère.
     *
     * @return l'étiquette
     */
    EtiquetteVue getEtiquette();

    /**
     * Indique si l'étiquette doit être possédée ou non.
     *
     * @return <code>true</code> si le critère est inclus, <code>false</code> s'il est exclu
     */
    boolean isInclus();
}
