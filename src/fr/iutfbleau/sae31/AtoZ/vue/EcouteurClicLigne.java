package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * La classe <code>EcouteurClicLigne</code> gère l'événement de relâchement du clic
 * de la souris sur une ligne de contact pour notifier l'écouteur de navigation.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class EcouteurClicLigne implements MouseListener {

    /**
     * La ligne de contact cliquée.
     */
    private final LigneContact ligneContact;

    /**
     * L'écouteur à notifier.
     */
    private final NavigationListener listener;

    /**
     * Construit un écouteur de clic pour une ligne de contact.
     *
     * @param ligneContact la ligne de contact concernée
     * @param listener l'écouteur de navigation
     */
    public EcouteurClicLigne(LigneContact ligneContact, NavigationListener listener) {
        this.ligneContact = ligneContact;
        this.listener = listener;
    }

    /**
     * Déclenché au relâchement du bouton de la souris : sélectionne le contact de la ligne.
     *
     * @param e l'événement de souris
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (listener != null) {
            listener.clicContact(ligneContact.getContact());
        }
    }

    /**
     * Non utilisé : seul le relâchement du clic est géré.
     *
     * @param e l'événement de souris
     */
    @Override
    public void mouseClicked(MouseEvent e) { }

    /**
     * Non utilisé : seul le relâchement du clic est géré.
     *
     * @param e l'événement de souris
     */
    @Override
    public void mousePressed(MouseEvent e) { }

    /**
     * Non utilisé : seul le relâchement du clic est géré.
     *
     * @param e l'événement de souris
     */
    @Override
    public void mouseEntered(MouseEvent e) { }

    /**
     * Non utilisé : seul le relâchement du clic est géré.
     *
     * @param e l'événement de souris
     */
    @Override
    public void mouseExited(MouseEvent e) { }
}
