package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.NavigationListener;

/**
 * Le <code>ControleurNavigation</code> gère les actions de navigation de l'utilisateur
 * (clics sur les contacts, étiquettes, listes de diffusion et menus).
 */
public class ControleurNavigation implements NavigationListener {

    private final Controleur controleur;
    private NavigationListener vueDelegate;

    /**
     * Construit le contrôleur de navigation.
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

    @Override
    public void clicTousLesContacts() {
        if (vueDelegate != null) {
            vueDelegate.clicTousLesContacts();
        }
    }

    @Override
    public void clicMenuEtiquettes() {
        if (vueDelegate != null) {
            vueDelegate.clicMenuEtiquettes();
        }
    }

    @Override
    public void clicMenuListesDiffusion() {
        if (vueDelegate != null) {
            vueDelegate.clicMenuListesDiffusion();
        }
    }

    @Override
    public void clicContact(ContactVue contact) {
        if (vueDelegate != null) {
            vueDelegate.clicContact(contact);
        }
    }

    @Override
    public void clicEtiquette(EtiquetteVue etiquette) {
        if (vueDelegate != null) {
            vueDelegate.clicEtiquette(etiquette);
        }
    }

    @Override
    public void clicListeDiffusion(ListeDiffusionVue liste) {
        if (vueDelegate != null) {
            vueDelegate.clicListeDiffusion(liste);
        }
    }
}