package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

/**
 * L'interface <code>NavigationListener</code> définit le contrat de navigation de
 * l'application. Elle permet aux panneaux de signaler à la fenêtre principale
 * ce que l'utilisateur vient de sélectionner : un contact, une étiquette, une liste
 * de diffusion, ou l'un des boutons généraux du menu de gauche.
 *
 * @version 1.3
 * @author Nkouka Ildevert
 */
public interface NavigationListener {

    /**
     * Appelée lorsque l'utilisateur clique sur le bouton "Tous les contacts".
     */
    void clicTousLesContacts();

    /**
     * Appelée lorsque l'utilisateur déplie le menu des étiquettes.
     */
    void clicMenuEtiquettes();

    /**
     * Appelée lorsque l'utilisateur déplie le menu des listes de diffusion.
     */
    void clicMenuListesDiffusion();

    /**
     * Appelée lorsque l'utilisateur clique sur un contact du répertoire.
     *
     * @param contact le contact sélectionné
     */
    void clicContact(ContactVue contact);

    /**
     * Appelée lorsque l'utilisateur clique sur un contact depuis une liste de diffusion :
     * le contact est sélectionné, mais la liste reste affichée.
     *
     * @param contact le contact sélectionné
     */
    void clicContactDansListe(ContactVue contact);

    /**
     * Appelée lorsque l'utilisateur clique sur une étiquette.
     *
     * @param etiquette l'étiquette sélectionnée
     */
    void clicEtiquette(EtiquetteVue etiquette);

    /**
     * Appelée lorsque l'utilisateur clique sur une liste de diffusion.
     *
     * @param liste la liste de diffusion sélectionnée
     */
    void clicListeDiffusion(ListeDiffusionVue liste);
}
