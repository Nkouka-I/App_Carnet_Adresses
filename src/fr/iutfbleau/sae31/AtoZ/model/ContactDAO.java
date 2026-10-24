package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import fr.iutfbleau.sae31.AtoZ.model.ModeleException;

/**
 * La classe <code>ContactDAO</code> est utilisée pour accéder aux contacts dans la base de
 * données : elle regroupe les requêtes SQL sur les tables <code>Contact</code> et
 * <code>Contact_etiquette</code> (opérations CRUD).
 *
 * @version 1.0
 * @author Tiago
 */
public class ContactDAO {
    /**
     * Connexion à la base de données utilisée pour toutes les requêtes.
     */
    private final Connection cx;

    /**
     * Accès à la table de liaison <code>Contact_etiquette</code> (étiquettes des contacts).
     */
    private final ContactEtiquetteDAO liaisons;

    /**
     * Constructeur d'un accès aux contacts.
     *
     * @param cx la connexion ouverte vers la base de données
     */
    public ContactDAO(Connection cx){
        this.cx = cx;
        this.liaisons = new ContactEtiquetteDAO(cx);
    }

    /**
     * Enregistre un nouveau contact avec ses étiquettes et renseigne son identifiant. L'opération
     * est faite dans une transaction : si une requête échoue, rien n'est enregistré. Les étiquettes
     * du contact doivent déjà être enregistrées.
     *
     * @param c le contact à enregistrer
     * @throws ModeleException en cas d'erreur d'accès à la base de données
     */
    public void creer(Contact c) throws ModeleException{
        boolean auto = true;
        try {
            auto = cx.getAutoCommit();
            cx.setAutoCommit(false);

            try (PreparedStatement ps = cx.prepareStatement("INSERT INTO Contact (nom, email) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, c.getNom());
                ps.setString(2, c.getEmail());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()){
                    c.setId(rs.getInt(1));
                }
                
            }
            liaisons.inserer(c);
            cx.commit();
        }catch (SQLException e){
            Transactions.annuler(cx);
            c.setId(0);
            throw ModeleException.depuis("Création du contact" , e);
        }finally{
            Transactions.restaurer(cx, auto);
        }

    }

    /**
     * Renvoie tous les contacts avec leurs étiquettes, triés par nom.
     *
     * @return la liste des contacts
     * @throws ModeleException en cas d'erreur d'accès à la base de données
     */
    public List<Contact> trouverTous() throws ModeleException {
        List<Contact> resultat = new ArrayList<>();
        try (PreparedStatement ps = cx.prepareStatement(
                "SELECT id, nom, email FROM Contact ORDER BY nom, id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(new Contact(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            liaisons.charger(resultat);
        } catch (SQLException e) {
            throw ModeleException.depuis("Lecture des contacts", e);
        }
        return resultat;
    }

    /**
     * Met à jour le nom, l'adresse mail et les étiquettes d'un contact existant, dans une
     * transaction.
     *
     * @param c le contact à modifier
     * @throws ModeleException en cas d'erreur d'accès ou si le contact n'existe plus
     */
    public void modifier(Contact c) throws ModeleException {
        boolean auto = true;
        try {
            auto = cx.getAutoCommit();
            cx.setAutoCommit(false);
            try (PreparedStatement ps = cx.prepareStatement(
                    "UPDATE Contact SET nom = ?, email = ? WHERE id = ?")) {
                ps.setString(1, c.getNom());
                ps.setString(2, c.getEmail());
                ps.setInt(3, c.getId());
                if (ps.executeUpdate() == 0) {
                    throw new ModeleException("Ce contact n'existe plus.");
                }
            }
            liaisons.supprimerDuContact(c.getId());
            liaisons.inserer(c);
            cx.commit();
        } catch (ModeleException e) {
            Transactions.annuler(cx);
            throw e;
        } catch (SQLException e) {
            Transactions.annuler(cx);
            throw ModeleException.depuis("Modification du contact", e);
        } finally {
            Transactions.restaurer(cx, auto);
        }
    }
    /**
     * Supprime un contact. Ses liens avec les étiquettes disparaissent avec lui (suppression
     * en cascade).
     *
     * @param id l'identifiant du contact
     * @throws ModeleException en cas d'erreur d'accès à la base de données
     */
    public void supprimer(int id) throws ModeleException {
        try (PreparedStatement ps = cx.prepareStatement("DELETE FROM Contact WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw ModeleException.depuis("Suppression du contact", e);
        }
    }
}
