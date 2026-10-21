package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.SQLException;

/**
 * La classe <code>ModeleException</code> est utilisée pour signaler une erreur du modèle :
 * donnée invalide ou problème d'accès à la base de données. Son message est destiné à être
 * affiché tel quel à l'utilisateur.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du 
 */
public class ModeleException extends Exception {

    /**
     * Numéro de version de la classe, nécessaire car <code>Exception</code> est sérialisable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur d'une erreur avec un message lisible.
     *
     * @param message le message destiné à l'utilisateur
     */
    public ModeleException(String message) {
        super(message);
    }

    /**
     * Constructeur d'une erreur avec un message lisible et sa cause technique.
     *
     * @param message le message destiné à l'utilisateur
     * @param cause l'exception d'origine
     */
    public ModeleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Traduit une erreur SQL en une erreur du modèle dont le message est compréhensible, selon le
     * code d'état SQL : valeur déjà existante ou encore utilisée, valeur trop longue ou invalide,
     * connexion impossible ou erreur d'accès quelconque.
     *
     * @param action l'action en cours (par exemple « Création du contact »)
     * @param e l'exception SQL d'origine
     * @return l'erreur du modèle correspondante
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