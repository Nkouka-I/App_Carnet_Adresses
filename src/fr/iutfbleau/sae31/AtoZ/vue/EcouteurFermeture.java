package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * La classe <code>EcouteurFermeture</code> intercepte la demande de fermeture de la
 * fenêtre principale pour demander une confirmation à l'utilisateur avant de quitter.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class EcouteurFermeture implements WindowListener {

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
     * Déclenché quand l'utilisateur demande la fermeture de la fenêtre : affiche la
     * confirmation de fermeture.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowClosing(WindowEvent e) {
        fenetre.confirmerFermeture();
    }

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowOpened(WindowEvent e) { }

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowClosed(WindowEvent e) { }

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowIconified(WindowEvent e) { }

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowDeiconified(WindowEvent e) { }

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowActivated(WindowEvent e) { }

    /**
     * Non utilisé.
     *
     * @param e l'événement de fenêtre
     */
    @Override
    public void windowDeactivated(WindowEvent e) { }
}
