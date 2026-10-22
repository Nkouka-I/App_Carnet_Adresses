package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

/**
 * L'interface <code>NavigationListener</code> définit le contrat de navigation de
 * l'application. Elle permet aux panneaux de signaler à la fenêtre principale
 * ce que l'utilisateur vient de sélectionner.
 *
 * @version 1.3
 * @author Nkouka Ildevert
 */
public interface NavigationListener {

    void clicTousLesContacts();

    void clicMenuEtiquettes();

    void clicMenuListesDiffusion();

    void clicContact(ContactVue contact);
    
    /**
     * Appelée lorsque l'utilisateur clique sur un contact depuis une liste de diffusion.
     *
     * @param contact le contact sélectionné
     */
    void clicContactDansListe(ContactVue contact);

    void clicEtiquette(EtiquetteVue etiquette);

    void clicListeDiffusion(ListeDiffusionVue liste);
}