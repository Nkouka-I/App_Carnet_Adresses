package fr.iutfbleau.sae31.AtoZ.model;

/**
 * La classe <code>Etiquette</code> représente une étiquette (un qualificatif) que l'on peut
 * associer à un contact, par exemple « client » ou « 2023 ».
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class Etiquette{
    /**
     * Longueur maximale du libellé d'une étiquette, exprimée en caractères.
     */
    public static final int LIBELLE_MAX = 100;
    /**
     * Identifiant de l'étiquette en base de données (0 tant qu'elle n'est pas enregistrée).
     */
    private int id ; 
    /**
     * Libellé de l'étiquette.
     */
    private String libelle ;


    /**
     * Constructeur destiné à la création d'une nouvelle étiquette, pas encore enregistrée en base :
     * son identifiant vaut 0 et sera attribué par la base de données.
     *
     * @param libelle le libellé de l'étiquette
     * @throws ModeleException si le libellé est invalide
     */
    public Etiquette(String libelle) throws ModeleException{
        this(0,libelle);

    }

    /**
     * Constructeur destiné à la création d'une étiquette dont l'identifiant en base est connu.
     * Le libellé est vérifié lors de la création.
     *
     * @param id l'identifiant de l'étiquette
     * @param libelle le libellé de l'étiquette
     * @throws ModeleException si le libellé est invalide
     */
    public Etiquette(int id , String libelle) throws ModeleException{
        this.id = id;
        setLibelle(libelle);
    }

    /**
     * Renvoie l'identifiant de l'étiquette.
     *
     * @return l'identifiant de l'étiquette (0 si elle n'est pas encore enregistrée)
     */
    public int getId(){
        return this.id;
    }

    /**
     * Modifie l'identifiant de l'étiquette. Cette méthode est appelée après l'enregistrement
     * en base, lorsque l'identifiant généré est connu.
     *
     * @param id le nouvel identifiant
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Renvoie le libellé de l'étiquette.
     *
     * @return le libellé de l'étiquette
     */
    public String getLibelle(){
        return this.libelle;
    }

    /**
     * Modifie le libellé de l'étiquette. Le libellé ne doit pas être vide (une suite d'espaces
     * est considérée comme vide) et ne doit pas dépasser {@link #LIBELLE_MAX} caractères.
     * Les espaces de début et de fin sont retirés avant l'enregistrement.
     *
     * @param libelle le nouveau libellé
     * @throws ModeleException si le libellé ne respecte pas ces règles
     */
    public void setLibelle(String libelle) throws ModeleException{
        if (libelle == null || libelle.trim().isEmpty()){
            throw new ModeleException("Le libéllé de l'étiquette ne doit pas etre vide");
        }
        if (libelle.length() > LIBELLE_MAX){
            throw new ModeleException("La taille du libelle ne doit pas dépasser"+ LIBELLE_MAX +"caractères");
        }
        this.libelle = libelle.trim();
    }
    /**
     * Renvoie une représentation textuelle de l'étiquette, c'est-à-dire son libellé.
     *
     * @return le libellé de l'étiquette
     */
    @Override
    public String toString(){
        return libelle;
    }

}