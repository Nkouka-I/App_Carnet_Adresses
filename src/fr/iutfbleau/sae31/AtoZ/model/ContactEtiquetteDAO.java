package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La classe <code>ContactEtiquetteDAO</code> gère la table de liaison
 * <code>Contact_etiquette</code>, qui permet d'associer plusieurs étiquettes à un contact
 * (et une même étiquette à plusieurs contacts).
 * <p>
 * Ses méthodes ne gèrent pas elles-mêmes les transactions : elles sont appelées par
 * {@link ContactDAO} (création et modification d'un contact) ou par
 * {@link ListeDiffusionDAO} (lecture des membres d'une liste), qui s'en occupent.
 *
 * @version 1.0
 * @author Tiago
 */
class ContactEtiquetteDAO {

    /**
     * Connexion à la base de données utilisée pour toutes les requêtes.
     */
    private final Connection cx;

    /**
     * Constructeur d'un accès à la table de liaison.
     *
     * @param cx la connexion ouverte vers la base de données
     */
    ContactEtiquetteDAO(Connection cx) {
        this.cx = cx;
    }

    /**
     * Enregistre les liens entre un contact et toutes ses étiquettes (une ligne par étiquette).
     *
     * @param c le contact, dont l'identifiant est connu, et dont les étiquettes sont déjà enregistrées
     * @throws SQLException en cas d'erreur SQL
     */
    void inserer(Contact c) throws SQLException {
        try (PreparedStatement ps = cx.prepareStatement(
                "INSERT INTO Contact_etiquette (contact_id, etiquette_id) VALUES (?, ?)")) {
            for (Etiquette e : c.getEtiquette()) {
                ps.setInt(1, c.getId());
                ps.setInt(2, e.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    /**
     * Supprime tous les liens d'un contact avec ses étiquettes (utilisé avant de les réécrire
     * lors de la modification du contact).
     *
     * @param contactId l'identifiant du contact
     * @throws SQLException en cas d'erreur SQL
     */
    void supprimerDuContact(int contactId) throws SQLException {
        try (PreparedStatement ps = cx.prepareStatement(
                "DELETE FROM Contact_etiquette WHERE contact_id = ?")) {
            ps.setInt(1, contactId);
            ps.executeUpdate();
        }
    }

    /**
     * Renseigne les étiquettes des contacts donnés, avec une seule requête.
     *
     * @param contacts les contacts déjà lus en base de données
     * @throws SQLException en cas d'erreur SQL
     * @throws ModeleException si une étiquette lue en base est invalide
     */
    void charger(List<Contact> contacts) throws SQLException, ModeleException {
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
