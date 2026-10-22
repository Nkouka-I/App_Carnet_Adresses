package fr.iutfbleau.sae31.AtoZ.model;

import fr.iutfbleau.sae31.AtoZ.controleur.CritereVue;

/**
 * La classe <code>Critere</code> représente une condition d'une liste de diffusion : un contact
 * doit avoir, ou ne doit pas avoir, une certaine étiquette.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class Critere implements CritereVue{
    /**
     * Étiquette concernée par le critère.
     */
    private final Etiquette etiquette ;
    /**
     * Vrai si le contact doit avoir l'étiquette, faux s'il ne doit pas l'avoir.
     */
    private final boolean inclus ;



    /**
     * Constructeur d'un critère.
     *
     * @param e l'étiquette concernée
     * @param b vrai si le contact doit avoir l'étiquette, faux s'il ne doit pas l'avoir
     */
    public Critere(Etiquette e , boolean b){
        this.etiquette = e;
        this.inclus = b;
    }

    /**
     * Renvoie l'étiquette concernée par le critère.
     *
     * @return l'étiquette du critère
     */
    public Etiquette getEtiquette(){
        return etiquette;
    }

    /**
     * Indique si l'étiquette est requise ou interdite.
     *
     * @return vrai si le contact doit avoir l'étiquette, faux s'il ne doit pas l'avoir
     */
    public boolean isInclus(){
        return inclus;
    }

    /**
     * Renvoie une représentation textuelle du critère, de la forme « avec x » ou « sans x ».
     *
     * @return la représentation textuelle du critère
     */
    @Override
    public String toString() {
        return (inclus ? "avec " : "sans ") + etiquette;
    }



}