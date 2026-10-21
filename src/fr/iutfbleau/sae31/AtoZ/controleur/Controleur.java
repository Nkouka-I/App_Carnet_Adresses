package fr.iutfbleau.sae31.AtoZ.controleur;

import java.util.List;

/**
 * L'interface <code>Controleur</code> définit tout ce que la vue demande au reste de
 * l'application : lire les contacts, les étiquettes et les listes de diffusion, et les
 * créer, modifier ou supprimer. La vue ne connaît que cette interface ; chaque
 * modification est suivie d'un appel aux {@link CarnetListener} enregistrés.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public interface Controleur {

    /**
     * Renvoie tous les contacts.
     *
     * @return la liste des contacts
     */
    List<ContactVue> getContacts();

    /**
     * Renvoie les contacts possédant une étiquette.
     *
     * @param etiquette l'étiquette recherchée (<code>null</code> pour tous les contacts)
     * @return la liste des contacts correspondants
     */
    List<ContactVue> getContacts(EtiquetteVue etiquette);

    /**
     * Renvoie les contacts qui font partie d'une liste de diffusion.
     *
     * @param liste la liste de diffusion
     * @return la liste des contacts qui respectent ses critères
     */
    List<ContactVue> getContactsDe(ListeDiffusionVue liste);

    /**
     * Renvoie toutes les étiquettes.
     *
     * @return la liste des étiquettes
     */
    List<EtiquetteVue> getEtiquettes();

    /**
     * Renvoie toutes les listes de diffusion.
     *
     * @return la liste des listes de diffusion
     */
    List<ListeDiffusionVue> getListesDiffusion();

    /**
     * Renvoie les listes de diffusion dont un critère concerne une étiquette.
     *
     * @param etiquette l'étiquette recherchée
     * @return la liste des listes de diffusion qui l'utilisent
     */
    List<ListeDiffusionVue> getListesUtilisant(EtiquetteVue etiquette);

    /**
     * Crée un contact.
     *
     * @param nom le nom du contact
     * @param email l'adresse email du contact
     * @param etiquettes les étiquettes du contact
     * @return le contact créé
     */
    ContactVue creerContact(String nom, String email, List<EtiquetteVue> etiquettes);

    /**
     * Modifie un contact.
     *
     * @param contact le contact à modifier
     * @param nom le nouveau nom
     * @param email la nouvelle adresse email
     * @param etiquettes les nouvelles étiquettes
     */
    void modifierContact(ContactVue contact, String nom, String email, List<EtiquetteVue> etiquettes);

    /**
     * Supprime un contact.
     *
     * @param contact le contact à supprimer
     */
    void supprimerContact(ContactVue contact);

    /**
     * Crée une étiquette.
     *
     * @param nom le libellé de l'étiquette
     * @return l'étiquette créée
     */
    EtiquetteVue creerEtiquette(String nom);

    /**
     * Renomme une étiquette.
     *
     * @param etiquette l'étiquette à renommer
     * @param nom le nouveau libellé
     */
    void modifierEtiquette(EtiquetteVue etiquette, String nom);

    /**
     * Supprime une étiquette. Elle est aussi retirée des contacts et des critères des
     * listes de diffusion.
     *
     * @param etiquette l'étiquette à supprimer
     */
    void supprimerEtiquette(EtiquetteVue etiquette);

    /**
     * Crée une liste de diffusion.
     *
     * @param nom le nom de la liste
     * @param criteres les critères d'appartenance
     * @return la liste créée
     */
    ListeDiffusionVue creerListeDiffusion(String nom, List<CritereVue> criteres);

    /**
     * Modifie une liste de diffusion.
     *
     * @param liste la liste à modifier
     * @param nom le nouveau nom
     * @param criteres les nouveaux critères
     */
    void modifierListeDiffusion(ListeDiffusionVue liste, String nom, List<CritereVue> criteres);

    /**
     * Supprime une liste de diffusion.
     *
     * @param liste la liste à supprimer
     */
    void supprimerListeDiffusion(ListeDiffusionVue liste);

    /**
     * Enregistre un écouteur appelé après chaque modification du carnet.
     *
     * @param ecouteur l'écouteur à enregistrer
     */
    void ajouterEcouteur(CarnetListener ecouteur);
}
