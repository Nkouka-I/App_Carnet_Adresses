package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import java.util.Comparator;

/**
 * L'énumération <code>OrdreTri</code> liste les façons de trier les contacts du
 * répertoire. Les textes sont comparés sans tenir compte de la casse ni des accents. Le libellé de chaque ordre est celui affiché dans
 * la liste déroulante de tri.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public enum OrdreTri {

    /**
     * Par nom, de A à Z.
     */
    NOM_CROISSANT("Nom (A → Z)", (a, b) -> comparer(a.getNom(), b.getNom())),

    /**
     * Par nom, de Z à A.
     */
    NOM_DECROISSANT("Nom (Z → A)", (a, b) -> comparer(b.getNom(), a.getNom())),

    /**
     * Par adresse mail, de A à Z (à adresse égale, par nom).
     */
    EMAIL_CROISSANT("Email (A → Z)", (a, b) -> {
        int c = comparer(a.getEmail(), b.getEmail());
        return c != 0 ? c : comparer(a.getNom(), b.getNom());
    }),

    /**
     * Par adresse mail, de Z à A (à adresse égale, par nom).
     */
    EMAIL_DECROISSANT("Email (Z → A)", (a, b) -> {
        int c = comparer(b.getEmail(), a.getEmail());
        return c != 0 ? c : comparer(a.getNom(), b.getNom());
    }),

    /**
     * Par nombre d'étiquettes, les contacts les plus étiquetés en premier (à égalité, par nom).
     */
    NB_ETIQUETTES("Nombre d'étiquettes (décroissant)", (a, b) -> {
        int c = Integer.compare(b.getEtiquettes().size(), a.getEtiquettes().size());
        return c != 0 ? c : comparer(a.getNom(), b.getNom());
    });

    /**
     * Libellé affiché dans la liste déroulante.
     */
    private final String libelle;

    /**
     * Comparateur de contacts correspondant à cet ordre.
     */
    private final Comparator<ContactVue> comparateur;

    /**
     * Construit un ordre de tri.
     *
     * @param libelle le texte affiché
     * @param comparateur le comparateur de contacts
     */
    OrdreTri(String libelle, Comparator<ContactVue> comparateur) {
        this.libelle = libelle;
        this.comparateur = comparateur;
    }

    /**
     * Compare deux textes sans tenir compte de la casse ni des accents.
     *
     * @param a le premier texte
     * @param b le second texte
     * @return un entier négatif, nul ou positif selon l'ordre de <code>a</code> par rapport à <code>b</code>
     */
    private static int comparer(String a, String b) {
        return Texte.sansAccents(a).compareTo(Texte.sansAccents(b));
    }

    /**
     * Renvoie le comparateur de contacts de cet ordre.
     *
     * @return le comparateur
     */
    public Comparator<ContactVue> getComparateur() { return comparateur; }

    /**
     * Renvoie le libellé de l'ordre.
     *
     * @return le texte affiché dans la liste déroulante
     */
    @Override
    public String toString() { return libelle; }
}
