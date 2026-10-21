package fr.iutfbleau.sae31.AtoZ;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ControleurTest;
import fr.iutfbleau.sae31.AtoZ.vue.Fenetre;
import javax.swing.*;

/**
 * La classe <code>Main</code> contient le point d'entrée de l'application AtoZ Carnet.
 * Elle crée le contrôleur puis affiche la fenêtre principale dans le thread dédié à Swing.
 * Elle se trouve hors du package de la vue, car elle relie les différentes couches.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class Main {

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
     * Crée le contrôleur de l'application.
     *
     * @return le contrôleur relié au modèle
     */
    private static Controleur creerControleur() {
        // TODO: remplacer ControleurTest (données en mémoire) par le contrôleur relié au vrai modèle
        return new ControleurTest();
    }
}
