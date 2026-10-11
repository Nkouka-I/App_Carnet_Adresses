package fr.iutfbleau.sae31.Atoz.model;

import java.sql.*;
import java.util.ArrayList;
import org.mariadb.jdbc.*;

/**
 * La classe <code>Contact</code> représente un contact du carnet d'adresses : un nom,
 * une adresse mail et un ensemble d'étiquettes.
 * <p>
 * Le nom et l'adresse mail sont vérifiés à chaque modification, de sorte qu'un contact
 * invalide ne puisse pas exister. L'identifiant est attribué par la base de données
 * (il vaut 0 tant que le contact n'est pas enregistré).
 *
 * @version 1.0
 * @author Tiago
 */
public class Contact{
    /**
     * Taille maximale de l'adresse mail, exprimée en octets (et non en caractères).
     */
    public static final int EMAIL_OCTETS_MAX = 254;
    /**
     * Identifiant du contact en base de données (0 tant qu'il n'est pas enregistré).
     */
    private int id ;
    /**
     * Nom du contact.
     */
    private String nom ;
    /**
     * Adresse mail du contact.
     */
    private String email;
    /**
     * Liste des étiquettes associées au contact (zéro, une ou plusieurs).
     */
    private final List<Etiquettes> etiquettes = new ArrayList<>();

    /**
     * Constructeur destiné à la création d'un nouveau contact, pas encore enregistré en base :
     * son identifiant vaut 0 et sera attribué par la base de données.
     *
     * @param nom le nom du contact
     * @param email l'adresse mail du contact
     * @throws ModeleExeption si le nom ou l'adresse mail est invalide
     */
    public Contact(String nom , String email) throws ModeleExeption{
        this(0,nom,email); // Constructeur simplifiéé c'est la BD qui donnera l'id
    }

    /**
     * Constructeur destiné à la création d'un contact dont l'identifiant en base est connu.
     * Le nom et l'adresse mail sont vérifiés lors de la création.
     *
     * @param id l'identifiant du contact
     * @param nom le nom du contact
     * @param email l'adresse mail du contact
     * @throws ModeleExeption si le nom ou l'adresse mail est invalide
     */
    public Contact(int id , String nom , String email) throws ModeleExeption{
        this.id = id;
        setnom(nom);
        setemail(email);
    }

    /**
     * Renvoie l'identifiant du contact.
     *
     * @return l'identifiant du contact (0 s'il n'est pas encore enregistré)
     */
    public int getId(){
        return this.id;
    }

    /**
     * Modifie l'identifiant du contact. Cette méthode est appelée après l'enregistrement
     * en base, lorsque l'identifiant généré est connu.
     *
     * @param id le nouvel identifiant
     */
    public void setId(int id){
        this.id = id;
        
    }

    /**
     * Renvoie le nom du contact.
     *
     * @return le nom du contact
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * Modifie le nom du contact. Le nom ne doit pas être vide, ne doit pas contenir de saut
     * de ligne et doit contenir au moins une lettre ou un chiffre.
     *
     * @param nom le nouveau nom
     * @throws ModeleExeption si le nom ne respecte pas ces règles
     */
    public void setNom(String nom) throws ModeleExeption{
        if (nom == null || nom.isEmpty()){
            throw new ModeleExeption("Le nom ne doit pas etre vide");
        }
        if (nom.indexOf('\n')>=0 || nom.indexOf('\r') >= 0){
            throw new ModeleExeption("Le nom ne doit pas contenir de saut de ligne");
        }
        boolean alphanumerique = false;
        for (int i = 0; i < nom.length() && !alphanumerique; i++) {
            alphanumerique = Character.isLetterOrDigit(nom.charAt(i));
        }
        if (!alphanumerique) {
            throw new ModeleException("Le nom doit contenir au moins une lettre ou un chiffre.");
        }
        this.nom = nom;
    }

    /**
     * Renvoie l'adresse mail du contact.
     *
     * @return l'adresse mail du contact
     */
    public String email(){
        return this.email;
    }
    
    /**
     * Modifie l'adresse mail du contact. L'adresse ne doit pas être vide, ne doit contenir
     * aucun caractère blanc, doit contenir le caractère « @ » (pas en première position)
     * et ne doit pas dépasser {@link #EMAIL_OCTETS_MAX} octets en UTF-8.
     *
     * @param email la nouvelle adresse mail
     * @throws ModeleException si l'adresse ne respecte pas ces règles
     */
    public void setEmail(String email){
        if (email == null || email.isEmpty()){
            throw new ModeleException("L'adresse mail ne doit pas etre vide");

        }
        for (int i = 0 ; i< email.length(); i++){
            if (Character.isWhitespace(email.charAt(i))){
                throw new ModeleException("L'adresse mail ne doit pas contenir d'espace");
            }

        }
        if (email.indexOf('@') <= 0){
            throw new ModeleException("L'adresse mail doit contenir un @");   
        }

        if (email.getBytes(StandardCharsets.UTF_8).length > EMAIL_OCTETS_MAX) {
            throw new ModeleException("L'adresse mail ne doit pas dépasser "
                + EMAIL_OCTETS_MAX + " octets.");
        }
        this.email = email;


    }
    /**
     * Renvoie la liste des étiquettes du contact. La liste renvoyée n'est pas modifiable :
     * pour changer les étiquettes, il faut utiliser les méthodes de la classe.
     *
     * @return la liste (non modifiable) des étiquettes du contact
     */
    public List<Etiquette> getEtiquettes() {
        return Collections.unmodifiableList(etiquettes);
    }
 
    /**
     * Ajoute une étiquette au contact si celui-ci ne l'a pas déjà. Deux étiquettes enregistrées
     * sont considérées comme identiques lorsqu'elles ont le même identifiant.
     *
     * @param e l'étiquette à ajouter
     */
    public void ajouterEtiquette(Etiquette e) {
        for (Etiquette existante : etiquettes) {
            if (existante.getId() == e.getId() && e.getId() != 0) {
                return;
            }
        }
        etiquettes.add(e);
    }
 
    /**
     * Retire une étiquette du contact (l'étiquette est repérée par son identifiant).
     *
     * @param e l'étiquette à retirer
     */
    public void retirerEtiquette(Etiquette e) {
        etiquettes.removeIf(x -> x.getId() == e.getId());
    }
 
    /**
     * Remplace toutes les étiquettes du contact par celles de la liste donnée.
     * Les doublons éventuels sont ignorés.
     *
     * @param nouvelles les nouvelles étiquettes du contact
     */
    public void setEtiquettes(List<Etiquette> nouvelles) {
        etiquettes.clear();
        for (Etiquette e : nouvelles) {
            ajouterEtiquette(e);
        }
    }
 
    /**
     * Renvoie une représentation textuelle du contact, de la forme « nom &lt;email&gt; ».
     *
     * @return la représentation textuelle du contact
     */
    @Override
    public String toString() {
        return nom + " <" + email + ">";
    }
}