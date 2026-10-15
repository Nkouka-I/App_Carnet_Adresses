package fr.iutfbleau.sae31.Atoz.model;

import javax.swing.*;
import java.awt.*;
import org.mariadb.jdbc.*;
import java.sql.*;

public class Connexion{

    private static final String URL = "jdbc:mariadb://dwarves.iut-fbleau.fr/alvesled";
    private static final String USER = "alvesled";
    private static final String PASSWORD = "Tiago2006";

    /**
     * Ouvre et retourne une connexion à la base de données.
     * @return Connection l'objet de connexion SQL
     * @throws SQLException si la connexion échoue
     */
    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
