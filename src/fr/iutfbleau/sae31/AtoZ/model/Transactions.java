package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Petites aides pour la gestion des transactions JDBC.
 */
final class Transactions {

    private Transactions() {
    }

    /**
     * Annule la transaction en cours, sans propager d'erreur.
     *
     * @param cx connexion concernée
     */
    static void annuler(Connection cx) {
        try {
            cx.rollback();
        } catch (SQLException e) {
            // rien de plus à faire : l'erreur d'origine est déjà traitée
        }
    }

    /**
     * Rétablit le mode de validation automatique d'origine.
     *
     * @param cx   connexion concernée
     * @param auto valeur initiale de l'auto-commit
     */
    static void restaurer(Connection cx, boolean auto) {
        try {
            cx.setAutoCommit(auto);
        } catch (SQLException e) {
            // rien de plus à faire
        }
    }
}