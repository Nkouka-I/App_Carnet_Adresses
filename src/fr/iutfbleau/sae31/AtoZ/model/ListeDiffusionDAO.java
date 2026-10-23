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

/**
 * La classe <code>ListeDiffusionDAO</code> est utilisée pour accéder aux listes de diffusion dans
 * la base de données : elle regroupe les requêtes SQL sur les tables <code>ListeDiffusion</code>
 * et <code>Critere</code> (opérations CRUD), ainsi que le calcul des membres d'une liste.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class ListeDiffusionDAO {

    /**
     * Connexion à la base de données utilisée pour toutes les requêtes.
     */
    private final Connection cx;

    /**
     * Constructeur d'un accès aux listes de diffusion.
     *
     * @param cx la connexion ouverte vers la base de données
     */
    public ListeDiffusionDAO(Connection cx) {
        this.cx = cx;
    }

    /**
     * Enregistre une nouvelle liste avec ses critères et renseigne son identifiant. L'opération est
     * faite dans une transaction. Les étiquettes des critères doivent déjà être enregistrées.
     *
     * @param l la liste à enregistrer (elle doit avoir au moins un critère)
     * @throws ModeleException en cas d'erreur d'accès, si le nom est déjà pris ou si la liste n'a
     * aucun critère
     */
    public void creer(ListeDiffusion l) throws ModeleException {
        verifierCriteres(l);
        boolean auto = true;
        try {
            auto = cx.getAutoCommit();
            cx.setAutoCommit(false);
            try (PreparedStatement ps = cx.prepareStatement(
                    "INSERT INTO ListeDiffusion (nom) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, l.getNom());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        l.setId(rs.getInt(1));
                    }
            }
            insererCriteres(l);
            cx.commit();
        } catch (SQLException e) {
            Transactions.annuler(cx);
            l.setId(0);
            throw ModeleException.depuis("Création de la liste", e);
        } finally {
            Transactions.restaurer(cx, auto);
        }
    }

    /**
     * Renvoie toutes les listes de diffusion avec leurs critères, triées par nom.
     *
     * @return la liste des listes de diffusion
     * @throws ModeleException en cas d'erreur d'accès à la base de données
     */
    public List<ListeDiffusion> trouverToutes() throws ModeleException {
        List<ListeDiffusion> resultat = new ArrayList<>();
        Map<Integer, ListeDiffusion> parId = new HashMap<>();
        try {
            try (PreparedStatement ps = cx.prepareStatement(
                    "SELECT id, nom FROM ListeDiffusion ORDER BY nom");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ListeDiffusion l = new ListeDiffusion(rs.getInt(1), rs.getString(2));
                    resultat.add(l);
                    parId.put(l.getId(), l);
                }
            }
            try (PreparedStatement ps = cx.prepareStatement(
                    "SELECT c.liste_id, e.id, e.libelle, c.inclus "
                    + "FROM Critere c JOIN Etiquette e ON e.id = c.etiquette_id "
                    + "ORDER BY e.libelle");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ListeDiffusion l = parId.get(rs.getInt(1));
                    if (l != null) {
                        l.ajouterCritere(new Critere(
                            new Etiquette(rs.getInt(2), rs.getString(3)), rs.getBoolean(4)));
                    }
                }
            }
        } catch (SQLException e) {
            throw ModeleException.depuis("Lecture des listes", e);
        }
        return resultat;
    }

    /**
     * Met à jour le nom et les critères d'une liste existante, dans une transaction.
     *
     * @param l la liste à modifier (elle doit avoir au moins un critère)
     * @throws ModeleException en cas d'erreur d'accès ou si la liste n'existe plus
     */
    public void modifier(ListeDiffusion l) throws ModeleException {
        verifierCriteres(l);
        boolean auto = true;
        try {
            auto = cx.getAutoCommit();
            cx.setAutoCommit(false);
            try (PreparedStatement ps = cx.prepareStatement(
                    "UPDATE ListeDiffusion SET nom = ? WHERE id = ?")) {
                ps.setString(1, l.getNom());
                ps.setInt(2, l.getId());
                if (ps.executeUpdate() == 0) {
                    throw new ModeleException("Cette liste n'existe plus.");
                }
            }
            try (PreparedStatement ps = cx.prepareStatement(
                    "DELETE FROM Critere WHERE liste_id = ?")) {
                ps.setInt(1, l.getId());
                ps.executeUpdate();
            }
            insererCriteres(l);
            cx.commit();
        } catch (ModeleException e) {
            Transactions.annuler(cx);
            throw e;
        } catch (SQLException e) {
            Transactions.annuler(cx);
            throw ModeleException.depuis("Modification de la liste", e);
        } finally {
            Transactions.restaurer(cx, auto);
        }
    }

    /**
     * Supprime une liste. Ses critères disparaissent avec elle (suppression en cascade).
     *
     * @param id l'identifiant de la liste
     * @throws ModeleException en cas d'erreur d'accès à la base de données
     */
    public void supprimer(int id) throws ModeleException {
        try (PreparedStatement ps = cx.prepareStatement(
                "DELETE FROM ListeDiffusion WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw ModeleException.depuis("Suppression de la liste", e);
        }
    }

    /**
     * Calcule les membres d'une liste : ce sont les contacts qui ont toutes les étiquettes
     * « incluses » et aucune des étiquettes « exclues ».
     *
     * @param liste la liste dont on veut les membres
     * @return les contacts de la liste, triés par nom
     * @throws ModeleException en cas d'erreur d'accès à la base de données
     */
    public List<Contact> membres(ListeDiffusion liste) throws ModeleException {
        verifierCriteres(liste);
        StringBuilder sql = new StringBuilder(
            "SELECT c.id, c.nom, c.email FROM Contact c WHERE 1 = 1");
        for (Critere cr : liste.getCritere()) {
            sql.append(cr.isInclus() ? " AND EXISTS" : " AND NOT EXISTS");
            sql.append(" (SELECT 1 FROM Contact_etiquette ce"
                + " WHERE ce.contact_id = c.id AND ce.etiquette_id = ?)");
        }
        sql.append(" ORDER BY c.nom, c.id");

        List<Contact> resultat = new ArrayList<>();
        try (PreparedStatement ps = cx.prepareStatement(sql.toString())) {
            int i = 1;
            for (Critere cr : liste.getCritere()) {
                ps.setInt(i++, cr.getEtiquette().getId());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultat.add(new Contact(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            }
            new ContactEtiquetteDAO(cx).charger(resultat);
        } catch (SQLException e) {
            throw ModeleException.depuis("Calcul des membres de la liste", e);
        }
        return resultat;
    }

    /**
     * Vérifie qu'une liste a au moins un critère.
     *
     * @param l la liste à vérifier
     * @throws ModeleException si la liste n'a aucun critère
     */
    private void verifierCriteres(ListeDiffusion l) throws ModeleException {
        if (l.getCriteres().isEmpty()) {
            throw new ModeleException("Une liste de diffusion doit avoir au moins un critère.");
        }
    }

    /**
     * Enregistre les critères d'une liste.
     *
     * @param l la liste, dont l'identifiant est connu
     * @throws SQLException en cas d'erreur SQL
     */
    private void insererCriteres(ListeDiffusion l) throws SQLException {
        try (PreparedStatement ps = cx.prepareStatement(
                "INSERT INTO Critere (liste_id, etiquette_id, inclus) VALUES (?, ?, ?)")) {
            for (Critere cr : l.getCritere()) {
                ps.setInt(1, l.getId());
                ps.setInt(2, cr.getEtiquette().getId());
                ps.setBoolean(3, cr.isInclus());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
}