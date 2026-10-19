package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import fr.iutfbleau.sae31.AtoZ.model.ModeleException;

public class Contactdao {
    private final Connexion cnx;

    public Contactdao(Connexion cnx){
        this.cnx = cnx;
    }

    public void creer(Contact c) throws ModeleException{
        boolean auto = true;
        try {
            auto = cnx.getAutoCommit();
            cnx.setAutoCommit(false);

            try (PreparedStatement ps = cnx.prepareStatement("INSERT INTO Contact (nom, email) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, c.getNom());
                ps.setString(2, c.getEmail());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()){
                        c.setId(rs.getInt(1));
                    }
                }
            }
            insererLiaisons(c);
            cnx.commit();
        }catch (SQLException e){
            Transactions.annuler(cnx);
            c.setId(0);
            throw ModeleException.depuis("Création du contact" , e);
        }finally{
            Transactions.restaurer(cnx, auto);
        }

    }

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
            try (PreparedStatement ps = cx.prepareStatement(
                    "DELETE FROM Contact_etiquette WHERE contact_id = ?")) {
                ps.setInt(1, c.getId());
                ps.executeUpdate();
            }
            insererLiaisons(c);
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
     * Supprime un contact (ses liens avec les étiquettes disparaissent avec lui).
     *
     * @param id identifiant du contact
     * @throws ModeleException en cas d'erreur d'accès
     */
    public void supprimer(int id) throws ModeleException {
        try (PreparedStatement ps = cx.prepareStatement("DELETE FROM Contact WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw ModeleException.depuis("Suppression du contact", e);
        }
    }
 
    /**
     * Enregistre les liens entre un contact et ses étiquettes.
     *
     * @param c contact dont l'identifiant est connu
     * @throws SQLException en cas d'erreur SQL
     */
    private void insererLiaisons(Contact c) throws SQLException {
        try (PreparedStatement ps = cx.prepareStatement(
                "INSERT INTO Contact_etiquette (contact_id, etiquette_id) VALUES (?, ?)")) {
            for (Etiquette e : c.getEtiquettes()) {
                ps.setInt(1, c.getId());
                ps.setInt(2, e.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
 
    /**
     * Renseigne les étiquettes des contacts donnés, avec une seule requête.
     *
     * @param contacts contacts déjà lus en base
     * @throws SQLException    en cas d'erreur SQL
     * @throws ModeleException si une étiquette en base est invalide
     */
    void chargerEtiquettes(List<Contact> contacts) throws SQLException, ModeleException {
        Map<Integer, Contact> parId = new HashMap<>();
        for (Contact c : contacts) {
            parId.put(c.getId(), c);
        }
        try (PreparedStatement ps = cx.prepareStatement(
                "SELECT ce.contact_id, e.id, e.libelle "
                + "FROM Contact_etiquette ce JOIN Etiquette e ON e.id = ce.etiquette_id "
                + "ORDER BY e.libelle");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Contact c = parId.get(rs.getInt(1));
                if (c != null) {
                    c.ajouterEtiquette(new Etiquette(rs.getInt(2), rs.getString(3)));
                }
            }
        }
    }




                
}
