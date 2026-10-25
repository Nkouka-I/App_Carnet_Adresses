package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * La classe <code>ZoneDefilante</code> est un panneau qui contient un contenu dans une zone de
 * défilement. Il prend la taille de la zone tant que celle-ci est assez grande pour le
 * contenu, puis laisse apparaître les barres de défilement quand elle devient trop petite.
 * La méthode {@link #autour(JComponent, int, int, int)} crée directement la zone de défilement.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class ZoneDefilante extends JPanel implements Scrollable {

    /**
     * Largeur minimale du contenu, en pixels.
     */
    private final int largeurMin;

    /**
     * Hauteur minimale du contenu, en pixels (négative : hauteur naturelle du contenu).
     */
    private final int hauteurMin;

    /**
     * Construit le panneau autour d'un contenu.
     *
     * @param contenu le contenu à afficher
     * @param largeurMin la largeur minimale du contenu
     * @param hauteurMin la hauteur minimale du contenu (négative : hauteur naturelle)
     */
    public ZoneDefilante(JComponent contenu, int largeurMin, int hauteurMin) {
        super(new BorderLayout());
        this.largeurMin = largeurMin;
        this.hauteurMin = hauteurMin;
        setOpaque(false);
        add(contenu, BorderLayout.CENTER);
    }

    /**
     * Renvoie la taille souhaitée : les minimums demandés, ou la taille naturelle du
     * contenu pour la hauteur quand aucun minimum n'est donné.
     *
     * @return la taille souhaitée
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension naturelle = super.getPreferredSize();
        return new Dimension(largeurMin, hauteurMin >= 0 ? hauteurMin : naturelle.height);
    }

    /**
     * Renvoie la taille souhaitée de la zone visible.
     *
     * @return la taille souhaitée
     */
    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    /**
     * Renvoie le déplacement produit par un cran de molette.
     *
     * @param visible la partie visible
     * @param orientation l'orientation de la barre
     * @param direction le sens du déplacement
     * @return le déplacement en pixels
     */
    @Override
    public int getScrollableUnitIncrement(Rectangle visible, int orientation, int direction) {
        return 16;
    }

    /**
     * Renvoie le déplacement produit par un clic dans la barre de défilement.
     *
     * @param visible la partie visible
     * @param orientation l'orientation de la barre
     * @param direction le sens du déplacement
     * @return le déplacement en pixels
     */
    @Override
    public int getScrollableBlockIncrement(Rectangle visible, int orientation, int direction) {
        return orientation == SwingConstants.VERTICAL ? visible.height - 16 : visible.width - 16;
    }

    /**
     * Indique si le contenu prend la largeur de la zone (c'est le cas tant qu'elle est
     * au moins aussi large que le minimum demandé).
     *
     * @return <code>true</code> s'il n'y a pas besoin de barre horizontale
     */
    @Override
    public boolean getScrollableTracksViewportWidth() {
        Component parent = getParent();
        return !(parent instanceof JViewport) || parent.getWidth() >= getPreferredSize().width;
    }

    /**
     * Indique si le contenu prend la hauteur de la zone (c'est le cas tant qu'elle est
     * au moins aussi haute que le contenu).
     *
     * @return <code>true</code> s'il n'y a pas besoin de barre verticale
     */
    @Override
    public boolean getScrollableTracksViewportHeight() {
        Component parent = getParent();
        return !(parent instanceof JViewport) || parent.getHeight() >= getPreferredSize().height;
    }

    /**
     * Entoure un panneau d'une zone de défilement. Le panneau suit la taille de la zone
     * quand celle-ci est assez grande ; sinon des barres de défilement apparaissent.
     *
     * @param contenu le panneau à rendre défilant
     * @param largeurZone la largeur fixe de la zone en pixels (0 pour laisser la disposition décider)
     * @param largeurMin la largeur minimale du contenu (0 : toujours la largeur de la zone)
     * @param hauteurMin la hauteur minimale du contenu (négative : la hauteur naturelle du contenu)
     * @return la zone de défilement
     */
    public static JScrollPane autour(JComponent contenu, int largeurZone, int largeurMin, int hauteurMin) {
        JScrollPane zone = new JScrollPane(new ZoneDefilante(contenu, largeurMin, hauteurMin));
        zone.setBorder(BorderFactory.createEmptyBorder());
        zone.getViewport().setBackground(contenu.getBackground());
        zone.getVerticalScrollBar().setUnitIncrement(16);
        zone.getHorizontalScrollBar().setUnitIncrement(16);
        if (largeurZone > 0) {
            zone.setPreferredSize(new Dimension(largeurZone, 0));
        }
        return zone;
    }
}
