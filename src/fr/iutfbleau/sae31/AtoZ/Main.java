package fr.iutfbleau.sae31.AtoZ;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.model.Connexion;
import fr.iutfbleau.sae31.AtoZ.vue.Fenetre;
import javax.swing.*;
import java.sql.Connection;


/**
 * La classe <code>Main</code> contient le point d'entrée de l'application AtoZ Carnet.
 * Elle crée le contrôleur puis affiche la fenêtre principale dans le thread dédié à Swing.
 * Elle se trouve hors du package de la vue, car elle relie les différentes couches.
 *
 * @version 1.1
 * @author Nkouka Ildevert
 */

public class Main {

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