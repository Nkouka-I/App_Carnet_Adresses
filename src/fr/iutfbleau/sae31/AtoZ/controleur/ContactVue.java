package fr.iutfbleau.sae31.AtoZ.controleur;

import java.util.List;

/**
 * L'interface <code>ContactVue</code> décrit ce que la vue a besoin de connaître d'un
 * contact. La classe du modèle représentant un contact doit l'implémenter.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public interface ContactVue {

    /**
     * Renvoie le nom du contact.
     *
     * @return le nom
     */
    String getNom();

    /**
     * Renvoie l'adresse email du contact.
     *
     * @return l'email
     */
    String getEmail();

    /**
     * Renvoie les étiquettes du contact.
     *
     * @return les étiquettes (liste vide si le contact n'en a pas, jamais <code>null</code>)
     */
    List<EtiquetteVue> getEtiquettes();
}
