package fr.iutfbleau.sae31.AtoZ;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ControleurTest;
import fr.iutfbleau.sae31.AtoZ.vue.Fenetre;

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
        return new ControleurTest();
    }
}