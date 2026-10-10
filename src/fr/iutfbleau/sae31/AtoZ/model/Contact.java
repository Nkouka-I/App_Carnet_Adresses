package fr.iutfbleau.sae31.Atoz.model;

import java.sql.*;
import java.util.ArrayList;
import org.mariadb.jdbc.*;

public class Contact{
    public static final int EMAIL_OCTETS_MAX = 254;
    private int id ;
    private String nom ;
    private String email;
    private final List<Etiquettes> etiquettes = new ArrayList<>();

    public Contact(String nom , String email) throws ModeleExeption{
        this(0,nom,email); // Constructeur simplifiéé c'est la BD qui donnera l'id
    }

    public Contact(int id , String nom , String email) throws ModeleExeption{
        this.id = id;
        setnom(nom);
        setemail(email);
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
        
    }

    public String getNom(){
        return this.nom;
    }

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

    public String email(){
        return this.email;
    }
    
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
        /** @return la liste (non modifiable) des étiquettes du contact */
    public List<Etiquette> getEtiquettes() {
        return Collections.unmodifiableList(etiquettes);
    }
 
    /**
     * Ajoute une étiquette si le contact ne l'a pas déjà.
     *
     * @param e étiquette à ajouter
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
     * Retire une étiquette du contact.
     *
     * @param e étiquette à retirer
     */
    public void retirerEtiquette(Etiquette e) {
        etiquettes.removeIf(x -> x.getId() == e.getId());
    }
 
    /**
     * Remplace toutes les étiquettes du contact.
     *
     * @param nouvelles nouvelles étiquettes
     */
    public void setEtiquettes(List<Etiquette> nouvelles) {
        etiquettes.clear();
        for (Etiquette e : nouvelles) {
            ajouterEtiquette(e);
        }
    }
 
    @Override
    public String toString() {
        return nom + " <" + email + ">";
    }
}
