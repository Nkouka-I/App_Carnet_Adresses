package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * La classe <code>Style</code> regroupe l'apparence commune de l'application : couleurs,
 * polices, marges, dimensions et petites méthodes de mise en forme. Changer l'aspect de
 * l'application revient à modifier uniquement cette classe.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public final class Style {

    // ------------------------------------------------------------------ Couleurs

    /**
     * Couleur d'accentuation de l'application (boutons principaux).
     */
    public static final Color COULEUR_PRINCIPALE = new Color(70, 130, 180);

    /**
     * Couleur du texte placé sur un fond de couleur principale.
     */
    public static final Color TEXTE_SUR_PRINCIPALE = Color.WHITE;

    /**
     * Couleur des actions destructrices (suppression).
     */
    public static final Color COULEUR_DANGER = Color.RED;

    /**
     * Couleur de fond du contenu (panneau central).
     */
    public static final Color FOND_CONTENU = Color.WHITE;

    /**
     * Couleur de fond des panneaux latéraux (gauche et droit).
     */
    public static final Color FOND_PANNEAU_LATERAL = new Color(245, 245, 245);

    /**
     * Couleur de fond d'une ligne de contact.
     */
    public static final Color FOND_LIGNE = new Color(250, 250, 250);

    /**
     * Couleur de fond de l'élément sélectionné (ligne de contact, bouton du menu).
     */
    public static final Color FOND_SELECTION = new Color(214, 232, 250);

    /**
     * Couleur d'un texte discret (messages d'invite).
     */
    public static final Color TEXTE_DISCRET = new Color(120, 120, 120);

    /**
     * Couleur du filet séparant deux lignes.
     */
    private static final Color COULEUR_SEPARATEUR = new Color(230, 230, 230);

    // ------------------------------------------------------------------ Polices

    /**
     * Police des titres de section.
     */
    public static final Font POLICE_TITRE = new Font("Arial", Font.BOLD, 14);

    /**
     * Police des grands titres de page.
     */
    public static final Font POLICE_GRAND_TITRE = new Font("Arial", Font.BOLD, 16);

    // ------------------------------------------------------------------ Dimensions

    /**
     * Largeur fixe du panneau de gauche, en pixels.
     */
    public static final int LARGEUR_PANNEAU_GAUCHE = 220;

    /**
     * Largeur fixe du panneau de droite, en pixels.
     */
    public static final int LARGEUR_PANNEAU_DROIT = 250;

    // ------------------------------------------------------------------ Marges et bordures

    /**
     * Marges autour des éléments d'un panneau latéral.
     */
    public static final Insets MARGE_PANNEAU = new Insets(10, 10, 10, 10);

    /**
     * Marges autour des éléments d'un contenu (répertoire, liste de diffusion).
     */
    public static final Insets MARGE_ELEMENT = new Insets(5, 5, 5, 5);

    /**
     * Marges autour des éléments d'un formulaire.
     */
    public static final Insets MARGE_FORMULAIRE = new Insets(6, 10, 6, 10);

    /**
     * Constructeur privé : la classe n'est pas instanciable.
     */
    private Style() {
    }

    // ------------------------------------------------------------------ Méthodes utilitaires

    /**
     * Applique la bordure vide qui entoure le contenu des pages du panneau central.
     *
     * @param composant le composant à mettre en forme
     */
    public static void bordureContenu(JComponent composant) {
        composant.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    /**
     * Applique la bordure d'une ligne de la liste : un filet en bas et un espace intérieur.
     * Une ligne sélectionnée a en plus une barre de couleur principale à gauche, sans
     * changer l'encombrement de la ligne.
     *
     * @param composant la ligne à mettre en forme
     * @param selectionnee <code>true</code> si la ligne est sélectionnée
     */
    public static void bordureLigne(JComponent composant, boolean selectionnee) {
        if (selectionnee) {
            composant.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 1, 0, COULEUR_PRINCIPALE),
                BorderFactory.createEmptyBorder(8, 4, 8, 8)
            ));
        } else {
            composant.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COULEUR_SEPARATEUR),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
        }
    }

    /**
     * Applique le style d'un bouton principal (fond coloré, texte clair).
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonPrincipal(JButton bouton) {
        bouton.setBackground(COULEUR_PRINCIPALE);
        bouton.setForeground(TEXTE_SUR_PRINCIPALE);
    }

    /**
     * Applique le style d'un bouton destructeur (texte rouge).
     *
     * @param bouton le bouton à mettre en forme
     */
    public static void styliserBoutonDanger(JButton bouton) {
        bouton.setForeground(COULEUR_DANGER);
    }

    /**
     * Met en évidence (gras et fond coloré) ou remet à l'état normal un bouton de menu.
     *
     * @param bouton le bouton à mettre en forme
     * @param actif <code>true</code> si le bouton correspond à l'élément actuellement affiché
     */
    public static void marquerBoutonMenu(JButton bouton, boolean actif) {
        bouton.setFont(bouton.getFont().deriveFont(actif ? Font.BOLD : Font.PLAIN));
        bouton.setBackground(actif ? FOND_SELECTION : UIManager.getColor("Button.background"));
    }

    /**
     * Crée un panneau transparent qui absorbe l'espace restant, afin de pousser les
     * autres composants vers le haut d'une grille. Il doit être ajouté avec un
     * <code>weighty</code> de 1.0.
     *
     * @return le panneau "ressort"
     */
    public static JPanel ressort() {
        JPanel ressort = new JPanel();
        ressort.setOpaque(false);
        return ressort;
    }
}
