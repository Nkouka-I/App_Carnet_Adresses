package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * La classe <code>EcouteurClicLigne</code> gère l'événement de relâchement du clic
 * de la souris sur une ligne de contact, dans le répertoire ou dans une liste de
 * diffusion, pour notifier l'écouteur de navigation.
 *
 * @version 1.3
 * @author Nkouka Ildevert
 */
public class EcouteurClicLigne implements MouseListener {

    /**
     * La ligne de contact cliquée.
     */
    private final LigneContactAbstraite ligneContact;

    /**
     * L'écouteur à notifier.
     */
    private final NavigationListener listener;

    /**
     * Indique si la ligne se trouve dans une liste de diffusion (la liste reste alors affichée).
     */
    private final boolean dansListe;

    /**
     * Construit un écouteur de clic pour une ligne de contact.
     *
     * @param ligneContact la ligne de contact concernée
     * @param listener l'écouteur de navigation
     * @param dansListe <code>true</code> si la ligne est affichée dans une liste de diffusion
     */
    public EcouteurClicLigne(LigneContactAbstraite ligneContact, NavigationListener listener, boolean dansListe) {
        this.ligneContact = ligneContact;
        this.listener = listener;
        this.dansListe = dansListe;
    }

    /**
     * Déclenché au relâchement du bouton de la souris : sélectionne le contact de la ligne.
     *
     * @param e l'événement de souris
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (listener == null) {
            return;
        }
        if (dansListe) {
            listener.clicContactDansListe(ligneContact.getContact());
        } else {
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
