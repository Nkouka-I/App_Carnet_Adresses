package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * La classe <code>StyleBouton</code> met en forme les boutons de l'application : fond plat,
 * cadre, texte et trois états (normal, survolé, enfoncé). Les couleurs viennent de
 * {@link Style}. Le dessin d'un bouton est entièrement pris en charge par ces couleurs, de
 * la même manière quel que soit le système. Les styles disponibles sont : principal (violet),
 * validation (menthe), secondaire (blanc), compact (petit bouton secondaire), danger (rouge)
 * et menu (bouton de navigation du panneau de gauche).
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public final class StyleBouton {

    /**
     * Constructeur privé : la classe n'est pas instanciable.
     */
    private StyleBouton() {
    }

    /**
     * Applique à un bouton un fond plat, un texte, une bordure et trois états (normal,
     * survolé, enfoncé). Le dessin du bouton est entièrement pris en charge par ces
     * couleurs, de la même manière quel que soit le système.
     *
     * @param bouton le bouton à mettre en forme
     * @param role le nom du style (mémorisé pour ne pas le remplacer ensuite)
     * @param fond la couleur de fond au repos
     * @param survol la couleur de fond quand la souris est dessus
     * @param appui la couleur de fond quand le bouton est enfoncé
     * @param texte la couleur du texte
     * @param bordure la couleur du cadre
     * @param police la police du texte
     * @param marges l'espace entre le cadre et le texte
     */
    private static void appliquerBouton(JButton bouton, String role, Color fond, Color survol, Color appui,
                                        Color texte, Color bordure, Font police, Insets marges) {
        bouton.putClientProperty("role", role);
        bouton.putClientProperty("fond", fond);
        bouton.putClientProperty("survol", survol);
        bouton.putClientProperty("appui", appui);

        bouton.setBackground(fond);
        bouton.setForeground(texte);
        bouton.setFont(police);
        bouton.setOpaque(true);
        bouton.setContentAreaFilled(true);
        bouton.setFocusPainted(false);
        bouton.setHorizontalAlignment(SwingConstants.CENTER);
        bouton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bouton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bordure),
            BorderFactory.createEmptyBorder(marges.top, marges.left, marges.bottom, marges.right)
        ));

        // Un seul écouteur de survol par bouton, même si le style est appliqué plusieurs fois
        if (bouton.getClientProperty("ecouteurSurvol") == null) {
            bouton.putClientProperty("ecouteurSurvol", Boolean.TRUE);
            bouton.addMouseListener(new Survol());
        }
    }

    /**
     * Applique le style d'un bouton principal : fond violet, texte blanc en gras.
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonPrincipal(JButton bouton) {
        appliquerBouton(bouton, "principal", Style.VIOLET_ATOZ, Style.SURVOL_VIOLET, Style.VIOLET_FONCE,
            Style.TEXTE_SUR_BOUTON, Style.VIOLET_ATOZ, Style.POLICE_GRAS, new Insets(9, 16, 9, 16));
    }

    /**
     * Applique le style d'un bouton de validation : fond vert menthe, texte sombre en gras
     * (le blanc serait peu lisible sur ce vert).
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonValidation(JButton bouton) {
        appliquerBouton(bouton, "validation", Style.VERT_MENTHE, Style.SURVOL_MENTHE, Style.APPUI_MENTHE,
            Style.TEXTE_PRINCIPAL, Style.VERT_MENTHE, Style.POLICE_GRAS, new Insets(8, 18, 8, 18));
    }

    /**
     * Applique le style d'un bouton secondaire : fond blanc, cadre gris, texte sombre.
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonSecondaire(JButton bouton) {
        appliquerBouton(bouton, "secondaire", Style.FOND_BLANC, Style.SURVOL_SECONDAIRE, Style.APPUI_SECONDAIRE,
            Style.TEXTE_PRINCIPAL, Style.BORDURE, Style.POLICE_TEXTE, new Insets(8, 14, 8, 14));
    }

    /**
     * Applique le style d'un petit bouton secondaire (par exemple « Copier » dans une
     * ligne de contact).
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonCompact(JButton bouton) {
        appliquerBouton(bouton, "compact", Style.FOND_BLANC, Style.SURVOL_SECONDAIRE, Style.APPUI_SECONDAIRE,
            Style.TEXTE_PRINCIPAL, Style.BORDURE, Style.POLICE_TEXTE, new Insets(7, 14, 7, 14));
    }

    /**
     * Applique le style d'un bouton destructeur : fond blanc, cadre et texte rouges.
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonDanger(JButton bouton) {
        appliquerBouton(bouton, "danger", Style.FOND_BLANC, Style.SURVOL_DANGER, Style.APPUI_DANGER,
            Style.ROUGE_DANGER, Style.ROUGE_DANGER, Style.POLICE_GRAS, new Insets(8, 14, 8, 14));
    }

    /**
     * Applique le style d'un bouton d'en-tête de section du menu (par exemple le bouton qui
     * déplie la liste des étiquettes) : texte en gras, aligné à gauche, sans cadre.
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonSection(JButton bouton) {
        marquerBoutonMenu(bouton, false);
        bouton.setFont(Style.POLICE_GRAS);
    }

    /**
     * Met en évidence (gras, fond violet clair et barre violette à gauche) ou remet à
     * l'état normal (fond du panneau, sans cadre) un bouton de menu.
     *
     * @param bouton le bouton à mettre en forme
     * @param actif <code>true</code> si le bouton correspond à l'élément actuellement affiché
     */
    public static void marquerBoutonMenu(JButton bouton, boolean actif) {
        Color fond = actif ? Style.FOND_SELECTION : Style.FOND_LATERAL;
        Color survol = actif ? Style.SELECTION_TEXTE : Style.SURVOL_MENU;
        Color appui = actif ? Style.SELECTION_TEXTE : Style.APPUI_MENU;
        Color texte = actif ? Style.VIOLET_FONCE : Style.TEXTE_PRINCIPAL;

        appliquerBouton(bouton, "menu", fond, survol, appui, texte, fond,
            actif ? Style.POLICE_GRAS : Style.POLICE_TEXTE, new Insets(8, 12, 8, 12));
        bouton.setHorizontalAlignment(SwingConstants.LEFT);
        if (actif) {
            bouton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, Style.VIOLET_ATOZ),
                BorderFactory.createEmptyBorder(8, 9, 8, 12)
            ));
        } else {
            bouton.setBorder(BorderFactory.createEmptyBorder(8, 13, 8, 12));
        }
    }

    /**
     * Écouteur qui change la couleur de fond d'un bouton quand la souris le survole ou
     * l'enfonce. Les couleurs sont lues dans les propriétés du bouton, ce qui permet de
     * les changer (par exemple pour un bouton de menu sélectionné) sans nouvel écouteur.
     */
    private static final class Survol implements MouseListener {

        /**
         * Constructeur de l'écouteur de survol.
         */
        Survol() {
        }

        /**
         * Lit une couleur mémorisée dans un bouton.
         *
         * @param bouton le bouton
         * @param nom le nom de la propriété (fond, survol ou appui)
         * @return la couleur mémorisée
         */
        private static Color couleur(JButton bouton, String nom) {
            return (Color) bouton.getClientProperty(nom);
        }

        /**
         * Prend la couleur de survol quand la souris entre dans le bouton.
         *
         * @param e l'événement souris
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setBackground(couleur(bouton, "survol"));
        }

        /**
         * Reprend la couleur de repos quand la souris quitte le bouton.
         *
         * @param e l'événement souris
         */
        @Override
        public void mouseExited(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setBackground(couleur(bouton, "fond"));
        }

        /**
         * Prend la couleur d'appui quand le bouton est enfoncé.
         *
         * @param e l'événement souris
         */
        @Override
        public void mousePressed(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setBackground(couleur(bouton, "appui"));
        }

        /**
         * Reprend la couleur de survol quand le bouton est relâché.
         *
         * @param e l'événement souris
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            JButton bouton = (JButton) e.getSource();
            bouton.setBackground(couleur(bouton, "survol"));
        }

        /**
         * Ne fait rien : le clic est déjà traité par l'action du bouton.
         *
         * @param e l'événement souris
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }
}
