package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Accès à la table {@code Etiquette} (opérations CRUD).
 */
public class EtiquetteDAO {

    private final Connection cx;

    /**
     * Crée un accès aux étiquettes.
     *
     * @param cx connexion ouverte vers la base
     */
    public EtiquetteDAO(Connection cx) {
        this.cx = cx;
    }

    /**
     * Enregistre une nouvelle étiquette et renseigne son identifiant.
     *
     * @param e étiquette à créer
     * @throws ModeleException en cas d'erreur (ex. libellé déjà existant)
     */
    public void creer(Etiquette e) throws ModeleException {
        try (PreparedStatement ps = cx.prepareStatement(
                "INSERT INTO Etiquette (libelle) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getLibelle());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                e.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            throw ModeleException.depuis("Création de l'étiquette", ex);
        }
    }

    /**
     * Retourne toutes les étiquettes, triées par libellé.
     *
     * @return les étiquettes
     * @throws ModeleException en cas d'erreur d'accès
     */
    public List<Etiquette> trouverToutes() throws ModeleException {
        List<Etiquette> resultat = new ArrayList<>();
        try (PreparedStatement ps = cx.prepareStatement(
                "SELECT id, libelle FROM Etiquette ORDER BY libelle");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultat.add(new Etiquette(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            throw ModeleException.depuis("Lecture des étiquettes", ex);
        }
        return resultat;
    }

    /**
     * Met à jour le libellé d'une étiquette existante.
     *
     * @param e étiquette à modifier
     * @throws ModeleException en cas d'erreur ou si l'étiquette n'existe plus
     */
    public void modifier(Etiquette e) throws ModeleException {
        try (PreparedStatement ps = cx.prepareStatement(
                "UPDATE Etiquette SET libelle = ? WHERE id = ?")) {
            ps.setString(1, e.getLibelle());
            ps.setInt(2, e.getId());
            if (ps.executeUpdate() == 0) {
                throw new ModeleException("Cette étiquette n'existe plus.");
            }
        } catch (SQLException ex) {
            throw ModeleException.depuis("Modification de l'étiquette", ex);
        }
    }

    /**
     * Supprime une étiquette. Elle disparaît des contacts qui l'avaient et des critères
     * des listes de diffusion qui l'utilisaient (suppression en cascade) : une liste peut
     * donc se retrouver sans aucun critère.
     *
     * @param id identifiant de l'étiquette
     * @throws ModeleException en cas d'erreur d'accès
     */
    public void supprimer(int id) throws ModeleException {
        try (PreparedStatement ps = cx.prepareStatement("DELETE FROM Etiquette WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw ModeleException.depuis("Suppression de l'étiquette", ex);
        }
    }
}