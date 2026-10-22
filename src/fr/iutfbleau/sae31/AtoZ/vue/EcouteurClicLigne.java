package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * La classe <code>EcouteurClicLigne</code> gère l'événement de relâchement du clic
 * de la souris sur une ligne de contact (répertoire ou liste de diffusion).
 *
 * @version 1.2
 * @author Nkouka Ildevert
 */
public class EcouteurClicLigne implements MouseListener {

    private final LigneContactAbstraite ligneContact;
    private final NavigationListener listener;

    public EcouteurClicLigne(LigneContactAbstraite ligneContact, NavigationListener listener) {
        this.ligneContact = ligneContact;
        this.listener = listener;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (listener != null) {
            listener.clicContact(ligneContact.getContact());
        }
    }

    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
}