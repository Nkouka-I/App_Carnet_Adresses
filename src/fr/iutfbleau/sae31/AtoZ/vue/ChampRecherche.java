package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * La classe <code>ChampRecherche</code> est un champ de saisie qui affiche un texte
 * d'indication grisé tant qu'il est vide (le texte disparaît dès que l'utilisateur tape).
 * Chaque fois que le texte change après une frappe au clavier, le champ prévient ses
 * <code>ActionListener</code> : on peut ainsi filtrer une liste pendant la frappe, sans
 * attendre la touche Entrée.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class ChampRecherche extends JTextField {

    /**
     * Texte grisé affiché lorsque le champ est vide.
     */
    private final String indication;

    /**
     * Dernier texte signalé aux écouteurs, pour ne signaler que les vrais changements.
     */
    private String dernierTexte = "";

    /**
     * Construit le champ de recherche.
     *
     * @param indication le texte d'indication affiché quand le champ est vide
     */
    public ChampRecherche(String indication) {
        this.indication = indication;
        setToolTipText("Tapez pour filtrer les contacts");
    }

    /**
     * Remplace le texte du champ. Les écouteurs ne sont pas prévenus : c'est l'appelant
     * qui sait qu'il vient de changer le texte.
     *
     * @param texte le nouveau texte
     */
    @Override
    public void setText(String texte) {
        super.setText(texte);
        dernierTexte = getText();
    }

    /**
     * Traite une touche du clavier. Quand une touche est relâchée et que le texte a
     * changé, les écouteurs sont prévenus.
     *
     * @param e l'événement clavier
     */
    @Override
    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);

        if (e.getID() == KeyEvent.KEY_RELEASED && !getText().equals(dernierTexte)) {
            dernierTexte = getText();
            postActionEvent();
        }
    }

    /**
     * Dessine le champ, puis le texte d'indication s'il est vide.
     *
     * @param g le contexte graphique
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty()) {
            Insets marges = getInsets();
            g.setColor(Style.TEXTE_DISCRET);
            g.setFont(getFont());
            int y = (getHeight() - g.getFontMetrics().getHeight()) / 2 + g.getFontMetrics().getAscent();
            g.drawString(indication, marges.left, y);
        }
    }
}
