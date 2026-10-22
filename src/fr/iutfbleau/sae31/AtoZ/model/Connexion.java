package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.mariadb.jdbc.*;

/**
 * La classe <code>Connexion</code> est utilisée pour ouvrir la connexion à la base de données
 * du projet.
 *
 * @version 1.1
 * @author Ildevert Nkouka , Tiago Alves--Le Du
 */
public class Connexion{

    /**
     * Adresse JDBC de la base de données.
     */
    private static final String URL = "jdbc:mariadb://dwarves.iut-fbleau.fr/alvesled";
    /**
     * Identifiant utilisé pour se connecter à la base de données.
     */
    private static final String USER = "alvesled";
    /**
     * Mot de passe utilisé pour se connecter à la base de données.
     */
    private static final String PASSWORD = "Tiago2006";

    /**
     * Ouvre et renvoie une connexion à la base de données. Une nouvelle connexion est créée
     * à chaque appel : il est conseillé de l'appeler une seule fois et de transmettre la
     * même connexion aux classes d'accès aux données.
     *
     * @return la connexion à la base de données
     * @throws ModeleException si la connexion échoue
     */
    public static Connection getInstance() throws ModeleException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw ModeleException.depuis("Connexion", e);
        }
    }
}