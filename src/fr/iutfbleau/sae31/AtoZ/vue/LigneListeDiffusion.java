package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;

/**
 * La classe <code>LigneListeDiffusion</code> représente un contact au sein
 * d'une liste de diffusion. Un clic sur la ligne sélectionne le contact sans quitter la
 * liste : l'écouteur de navigation est prévenu par <code>clicContactDansListe</code>.
 *
 * @version 1.4
 * @author Nkouka Ildevert
 */
public class LigneListeDiffusion extends LigneContactAbstraite {

    /**
     * Construit la ligne d'un contact de liste de diffusion.
     *
     * @param contact le contact à afficher
     * @param listener l'écouteur à notifier lors d'un clic sur la ligne (peut être <code>null</code>)
     */
    public LigneListeDiffusion(ContactVue contact, NavigationListener listener) {
        super(contact, listener, true);
    }
}
