package fr.iutfbleau.sae31.AtoZ.vue;

import java.util.List;

/**
 * L'interface <code>NavigationListener</code> définit le contrat de navigation de
 * l'application. Elle permet aux panneaux de signaler à la fenêtre principale
 * qu'un contact, une étiquette ou une liste de diffusion vient d'être sélectionné.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public interface NavigationListener {

    /**
     * Appelée lorsque l'utilisateur clique sur un contact.
     *
     * @param nom le nom du contact sélectionné
     * @param email l'adresse email du contact sélectionné
     * @param etiquettes les étiquettes associées au contact
     */
    void clicContact(String nom, String email, List<String> etiquettes);

    /**
     * Appelée lorsque l'utilisateur clique sur une étiquette.
     *
     * @param nomEtiquette le libellé de l'étiquette sélectionnée
     */
    void clicContactEtiquette(String nomEtiquette);

    /**
     * Appelée lorsque l'utilisateur clique sur une liste de diffusion.
     *
     * @param nomListe le nom de la liste de diffusion sélectionnée
     */
    void clicListeDiffusion(String nomListe);
}
