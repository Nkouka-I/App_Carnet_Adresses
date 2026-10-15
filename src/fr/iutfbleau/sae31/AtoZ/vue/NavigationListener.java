package fr.iutfbleau.sae31.AtoZ.vue;

import java.util.List;

public interface NavigationListener {
    void clicContact(String nom, String email, List<String> etiquettes);
    void clicContactEtiquette(String nomEtiquette);
    void clicListeDiffusion(String nomListe);
}