package fr.iutfbleau.sae31.AtoZ.model;

/**
 * L'énumération <code>OrdreTri</code> liste les façons de trier les contacts du répertoire.
 * Le tri est réalisé par la base de données (clause <code>ORDER BY</code> d'une requête SQL),
 * sans tenir compte de la casse ni des accents. Le libellé de chaque ordre est celui affiché
 * dans la liste déroulante de tri.
 *
 * @version 1.1
 * @author Ildevert Nkouka , Tiago Alves--Le DU
 */
public enum OrdreTri {

    /**
     * Par nom, de A à Z.
     */
    NOM_CROISSANT("Nom (A → Z)", texte("c.nom") + " ASC"),

    /**
     * Par nom, de Z à A.
     */
    NOM_DECROISSANT("Nom (Z → A)", texte("c.nom") + " DESC"),

    /**
     * Par adresse mail, de A à Z (à adresse égale, par nom).
     */
    EMAIL_CROISSANT("Email (A → Z)", texte("c.email") + " ASC, " + texte("c.nom") + " ASC"),

    /**
     * Par adresse mail, de Z à A (à adresse égale, par nom).
     */
    EMAIL_DECROISSANT("Email (Z → A)", texte("c.email") + " DESC, " + texte("c.nom") + " ASC"),

    /**
     * Par nombre d'étiquettes, les contacts les plus étiquetés en premier (à égalité, par nom).
     */
    NB_ETIQUETTES("Nombre d'étiquettes (décroissant)",
        "(SELECT COUNT(*) FROM Contact_etiquette ce WHERE ce.contact_id = c.id) DESC, "
        + texte("c.nom") + " ASC");

    /**
     * Libellé affiché dans la liste déroulante.
     */
    private final String libelle;

    /**
     * Clause <code>ORDER BY</code> correspondant à cet ordre (la table des contacts est
     * désignée par l'alias <code>c</code>).
     */
    private final String clauseSql;

    /**
     * Constructeur d'un ordre de tri.
     *
     * @param libelle le texte affiché
     * @param clauseSql la clause <code>ORDER BY</code> correspondante
     */
    OrdreTri(String libelle, String clauseSql) {
        this.libelle = libelle;
        this.clauseSql = clauseSql;
    }

    /**
     * Renvoie l'expression SQL qui compare une colonne de texte sans tenir compte de la casse
     * ni des accents.
     *
     * @param colonne la colonne à comparer
     * @return l'expression SQL correspondante
     */
    private static String texte(String colonne) {
        return "CONVERT(" + colonne + " USING utf8mb4) COLLATE utf8mb4_general_ci";
    }

    /**
     * Renvoie la clause <code>ORDER BY</code> de cet ordre.
     *
     * @return la clause SQL (sans le mot <code>ORDER BY</code>)
     */
    public String getClauseSql() { return clauseSql; }

    /**
     * Renvoie le libellé de l'ordre.
     *
     * @return le texte affiché dans la liste déroulante
     */
    @Override
    public String toString() { return libelle; }

}