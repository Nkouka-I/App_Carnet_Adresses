package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * La classe <code>EcouteurFermeture</code> intercepte la demande de fermeture
 * de la fenêtre principale pour déclencher une confirmation.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class EcouteurFermeture extends WindowAdapter {

    /**
     * Référence vers la fenêtre principale à contrôler.
     */
    private final Fenetre fenetre;

    /**
     * Construit l'écouteur en l'associant à la fenêtre principale.
     *
     * @param fenetre la fenêtre principale
     */
    public EcouteurFermeture(Fenetre fenetre) {
        this.fenetre = fenetre;
    }

    /**
     * Déclenché lorsque l'utilisateur tente de fermer la fenêtre.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowClosing(WindowEvent e) {
        fenetre.confirmerFermeture();
    }
}