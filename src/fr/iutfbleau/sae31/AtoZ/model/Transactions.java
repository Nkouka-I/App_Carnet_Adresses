package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * La classe <code>Transactions</code> regroupe de petites méthodes utilisées par les classes
 * d'accès aux données pour gérer les transactions JDBC.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
final class Transactions {

    /**
     * Constructeur privé : la classe ne contient que des méthodes statiques et n'est pas
     * destinée à être instanciée.
     */
    private Transactions() {
    }

    /**
     * Annule la transaction en cours, sans propager d'erreur.
     *
     * @param cx la connexion concernée
     */
    static void annuler(Connection cx) {
        try {
            cx.rollback();
        } catch (SQLException e) {
            // rien de plus à faire : l'erreur d'origine est déjà traitée
        }
    }

    /**
     * Rétablit le mode de validation automatique d'origine de la connexion.
     *
     * @param cx la connexion concernée
     * @param auto la valeur initiale du mode de validation automatique
     */
    static void restaurer(Connection cx, boolean auto) {
        try {
            cx.setAutoCommit(auto);
        } catch (SQLException e) {
            // rien de plus à faire
        }
    }
}