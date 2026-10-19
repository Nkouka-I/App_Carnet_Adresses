package fr.iutfbleau.sae31.AtoZ.vue;

/**
 * La classe <code>CritereData</code> représente un critère d'appartenance à une liste
 * de diffusion : une étiquette que le contact doit avoir ou ne doit pas avoir.
 * Un critère peut être modifié après sa création.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class CritereData {

    /**
     * Libellé de l'étiquette concernée par le critère.
     */
    private String etiquette;

    /**
     * <code>true</code> si le contact doit avoir l'étiquette, <code>false</code> s'il
     * ne doit pas l'avoir.
     */
    private boolean inclus;

    /**
     * Construit un critère.
     *
     * @param etiquette le libellé de l'étiquette concernée
     * @param inclus <code>true</code> si l'étiquette est requise, <code>false</code> si elle est interdite
     */
    public CritereData(String etiquette, boolean inclus) {
        this.etiquette = etiquette;
        this.inclus = inclus;
    }

    /**
     * Construit une copie d'un critère existant, afin de pouvoir la modifier sans
     * toucher à l'original.
     *
     * @param autre le critère à copier
     */
    public CritereData(CritereData autre) {
        this(autre.etiquette, autre.inclus);
    }

    /**
     * Renvoie le libellé de l'étiquette concernée par le critère.
     *
     * @return le libellé de l'étiquette
     */
    public String getEtiquette() {
        return etiquette;
    }

    /**
     * Change l'étiquette concernée par le critère.
     *
     * @param etiquette le nouveau libellé de l'étiquette
     */
    public void setEtiquette(String etiquette) {
        this.etiquette = etiquette;
    }

    /**
     * Indique si l'étiquette est requise ou interdite.
     *
     * @return <code>true</code> si le contact doit avoir l'étiquette, <code>false</code> s'il ne doit pas l'avoir
     */
    public boolean isInclus() {
        return inclus;
    }

    /**
     * Change le type du critère.
     *
     * @param inclus <code>true</code> si l'étiquette est requise, <code>false</code> si elle est interdite
     */
    public void setInclus(boolean inclus) {
        this.inclus = inclus;
    }

    /**
     * Renvoie une description lisible du critère, utilisée pour l'affichage dans la liste.
     *
     * @return le texte "DOIT avoir : ..." ou "NE DOIT PAS avoir : ..." suivi de l'étiquette
     */
    @Override
    public String toString() {
        return (inclus ? "DOIT avoir : " : "NE DOIT PAS avoir : ") + etiquette;
    }
}
