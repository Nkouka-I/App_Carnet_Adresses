package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.CritereVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;

/**
 * La classe <code>CritereData</code> est un critère modifiable, utilisé par le formulaire
 * de liste de diffusion pendant la saisie. Elle implémente {@link CritereVue} : le
 * contrôleur la reçoit sous cette forme et crée lui-même les critères du modèle.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class CritereData implements CritereVue {

    /**
     * Étiquette concernée par le critère.
     */
    private EtiquetteVue etiquette;

    /**
     * <code>true</code> si l'étiquette doit être possédée, <code>false</code> si elle est interdite.
     */
    private boolean inclus;

    /**
     * Construit un critère.
     *
     * @param etiquette l'étiquette concernée
     * @param inclus <code>true</code> si l'étiquette est requise, <code>false</code> si elle est interdite
     */
    public CritereData(EtiquetteVue etiquette, boolean inclus) {
        this.etiquette = etiquette;
        this.inclus = inclus;
    }

    /**
     * Construit une copie d'un critère (par exemple celui d'une liste existante), pour
     * que l'annulation du formulaire ne modifie pas l'original.
     *
     * @param autre le critère à copier
     */
    public CritereData(CritereVue autre) {
        this(autre.getEtiquette(), autre.isInclus());
    }

    @Override
    public EtiquetteVue getEtiquette() { return etiquette; }

    @Override
    public boolean isInclus() { return inclus; }

    /**
     * Change l'étiquette du critère.
     *
     * @param etiquette la nouvelle étiquette
     */
    public void setEtiquette(EtiquetteVue etiquette) { this.etiquette = etiquette; }

    /**
     * Change le type du critère.
     *
     * @param inclus <code>true</code> pour inclus, <code>false</code> pour exclu
     */
    public void setInclus(boolean inclus) { this.inclus = inclus; }

    /**
     * Renvoie le texte affiché dans la liste des critères du formulaire.
     *
     * @return par exemple « Inclus : famille »
     */
    @Override
    public String toString() {
        return (inclus ? "Inclus : " : "Exclu : ") + etiquette.getNom();
    }
}
