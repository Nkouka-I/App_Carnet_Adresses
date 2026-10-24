package fr.iutfbleau.sae31.AtoZ;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.model.Connexion;
import fr.iutfbleau.sae31.AtoZ.vue.Fenetre;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * La classe <code>Main</code> contient le point d'entrée de l'application AtoZ Carnet.
 * Elle crée le contrôleur puis affiche la fenêtre principale dans le thread dédié à Swing.
 * Elle se trouve hors du package de la vue, car elle relie les différentes couches.
 *
 * @version 1.1
 * @author Nkouka Ildevert
 */
public class Main {

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
     * Ouvre la connexion à la base de données et crée le contrôleur de l'application.
     * Si la connexion échoue, un message d'erreur est affiché et le programme s'arrête.
     *
     * @return le contrôleur relié au modèle
     */
    private static Controleur creerControleur() {
        try {
            Connection cx = Connexion.getInstance();
            return new Controleur(cx);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Erreur de connexion à la base de données : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            return null;
        }
    }
}
