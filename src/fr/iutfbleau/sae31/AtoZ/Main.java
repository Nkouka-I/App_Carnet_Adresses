package fr.iutfbleau.sae31.AtoZ;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.vue.Fenetre;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * La classe <code>Main</code> contient le point d'entrée de l'application AtoZ Carnet.
 * Elle crée le contrôleur puis affiche la fenêtre principale dans le thread dédié à Swing.
 * Elle se trouve hors du package de la vue, car elle relie les différentes couches.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class Main {

    /** Adresse de la base de données (à adapter : serveur et nom de la base). */
    private static final String URL_BASE = "jdbc:mariadb://localhost:3306/atoz";

    /** Nom de l'utilisateur de la base de données (à adapter). */
    private static final String UTILISATEUR = "utilisateur";

    /** Mot de passe de la base de données (à adapter). */
    private static final String MOT_DE_PASSE = "motdepasse";

    /**
     * Constructeur privé : la classe ne contient que le point d'entrée du programme.
     */
    private Main() { }

    /**
     * Point d'entrée du programme. Applique le look and feel du système d'exploitation
     * puis affiche la fenêtre principale.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        // Exécute la création et l'affichage de la fenêtre dans le thread dédié à Swing
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Fenetre(creerControleur()).setVisible(true);
        });
    }

    /**
     * Crée le contrôleur de l'application, relié à la base de données MariaDB.
     * Si la connexion échoue, un message est affiché et le programme s'arrête.
     *
     * @return le contrôleur relié au modèle
     */
    private static Controleur creerControleur() {
        try {
            Connection cx = DriverManager.getConnection(URL_BASE, UTILISATEUR, MOT_DE_PASSE);
            return new Controleur(cx);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Connexion à la base de données impossible :\n" + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return null;
        }
    }
}
