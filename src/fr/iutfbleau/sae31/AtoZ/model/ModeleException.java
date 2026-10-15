package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.SQLException;

/**
 * Erreur remontée par le modèle (donnée invalide ou problème d'accès à la base).
 * Son message est destiné à être affiché tel quel à l'utilisateur.
 */
public class ModeleException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Crée une erreur avec un message lisible.
     *
     * @param message message destiné à l'utilisateur
     */
    public ModeleException(String message) {
        super(message);
    }

    /**
     * Crée une erreur avec un message lisible et sa cause technique.
     *
     * @param message message destiné à l'utilisateur
     * @param cause   exception d'origine
     */
    public ModeleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Traduit une {@link SQLException} en message compréhensible.
     *
     * @param action action en cours (ex. « Création du contact »)
     * @param e      exception SQL d'origine
     * @return l'exception du modèle correspondante
     */
    public static ModeleException depuis(String action, SQLException e) {
        String etat = e.getSQLState();
        if (etat != null && etat.startsWith("23")) {
            return new ModeleException(
                action + " : cette valeur existe déjà ou est encore utilisée.", e);
        }
        if (etat != null && etat.startsWith("22")) {
            return new ModeleException(
                action + " : une valeur est trop longue ou invalide.", e);
        }
        if (etat != null && etat.startsWith("08")) {
            return new ModeleException(
                "Connexion au serveur de base de données impossible.", e);
        }
        return new ModeleException(
            action + " : erreur d'accès à la base de données.", e);
    }
}
