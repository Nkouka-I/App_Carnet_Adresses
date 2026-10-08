import javax.swing.*;
import java.awt.*;
import org.mariadb.jdbc.*;
import java.sql.*;

public class Connexion{

    private static final String URL = "jdbc:mariadb://dwarves.iut-fbleau.fr/nkouka";
    private static final String USER = "nkouka";
    private static final String PASSWORD = "Mdpdo";

    /**
     * Ouvre et retourne une connexion à la base de données.
     * @return Connection l'objet de connexion SQL
     * @throws SQLException si la connexion échoue
     */
    public static Connection getInstance() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
