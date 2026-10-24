package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.NavigationListener;

/**
 * La classe <code>ControleurNavigation</code> gère les actions de navigation de l'utilisateur
 * (clics sur les contacts, étiquettes, listes de diffusion et menus). Elle relaie chaque
 * action vers la vue enregistrée avec <code>setVueDelegate</code> : tant qu'aucune vue n'est
 * enregistrée, les actions sont ignorées.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class ControleurNavigation implements NavigationListener {

    /**
     * Le contrôleur principal de l'application.
     */
    private final Controleur controleur;

    /**
     * La vue (ou la fenêtre) qui effectue l'affichage graphique, <code>null</code> tant
     * qu'elle n'a pas été enregistrée.
     */
    private NavigationListener vueDelegate;

    /**
     * Constructeur du contrôleur de navigation.
     *
     * @param controleur le contrôleur principal de l'application
     */
    public ControleurNavigation(Controleur controleur) {
        this.controleur = controleur;
    }

    /**
     * Enregistre la vue (ou la fenêtre) qui effectuera l'affichage graphique.
     *
     * @param vueDelegate l'écouteur de la vue
     */
    public void setVueDelegate(NavigationListener vueDelegate) {
        this.vueDelegate = vueDelegate;
    }

    /**
     * Relaye le clic sur « Tous les contacts » vers la vue.
     */
    @Override
    public void clicTousLesContacts() {
        if (vueDelegate != null) {
            vueDelegate.clicTousLesContacts();
        }
    }

    /**
     * Relaye le clic sur le menu des étiquettes vers la vue.
     */
    @Override
    public void clicMenuEtiquettes() {
        if (vueDelegate != null) {
            vueDelegate.clicMenuEtiquettes();
        }
    }

    /**
     * Relaye le clic sur le menu des listes de diffusion vers la vue.
     */
    @Override
    public void clicMenuListesDiffusion() {
        if (vueDelegate != null) {
            vueDelegate.clicMenuListesDiffusion();
        }
    }

    /**
     * Relaye le clic sur un contact du répertoire vers la vue.
     *
     * @param contact le contact sur lequel l'utilisateur a cliqué
     */
    @Override
    public void clicContact(ContactVue contact) {
        if (vueDelegate != null) {
            vueDelegate.clicContact(contact);
        }
    }

    /**
     * Relaye le clic sur un contact affiché dans une liste de diffusion vers la vue.
     *
     * @param contact le contact sur lequel l'utilisateur a cliqué
     */
    @Override
    public void clicContactDansListe(ContactVue contact) {
        if (vueDelegate != null) {
            vueDelegate.clicContactDansListe(contact);
        }
    }

    /**
     * Relaye le clic sur une étiquette vers la vue (filtre des contacts par étiquette).
     *
     * @param etiquette l'étiquette sur laquelle l'utilisateur a cliqué
     */
    @Override
    public void clicEtiquette(EtiquetteVue etiquette) {
        if (vueDelegate != null) {
            vueDelegate.clicEtiquette(etiquette);
        }
    }

    /**
     * Relaye le clic sur une liste de diffusion vers la vue.
     *
     * @param liste la liste de diffusion sur laquelle l'utilisateur a cliqué
     */
    @Override
    public void clicListeDiffusion(ListeDiffusionVue liste) {
        if (vueDelegate != null) {
            vueDelegate.clicListeDiffusion(liste);
        }
    }

}