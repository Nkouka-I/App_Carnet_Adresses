package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La classe <code>LigneListeDiffusion</code> représente un contact au sein 
 * d'une liste de diffusion. Elle signale à la navigation qu'un contact 
 * a été cliqué *depuis* la liste.
 *
 * @version 1.3
 * @author Nkouka Ildevert
 */
public class LigneListeDiffusion extends LigneContactAbstraite {

    public LigneListeDiffusion(ContactVue contact, NavigationListener listener) {
        super(contact, null); // On désactive le comportement par défaut de LigneContactAbstraite
        
        if (listener != null) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            // On ajoute notre propre écouteur qui ne ferme pas la liste de diffusion
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    listener.clicContactDansListe(contact);
                }
            });
        }
    }
}