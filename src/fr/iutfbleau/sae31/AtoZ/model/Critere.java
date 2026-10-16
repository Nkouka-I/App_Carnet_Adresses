package fr.iutfbleau.sae31.Atoz.model;

public class Critere{
    private final Etiquette etiquette ;
    private final boolean inclus ;



    public Critere(Etiquette e , boolean b){
        this.etiquette = e;
        this.inclus = b;
    }

    public Etiquette getEtiquette(){
        return etiquette;
    }

    public boolean isInclus(){
        return inclus;
    }

    @Override
    public String toString() {
        return (inclus ? "avec " : "sans ") + etiquette;
    }



}