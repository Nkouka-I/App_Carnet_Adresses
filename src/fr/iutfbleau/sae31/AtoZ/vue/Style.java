package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * La classe <code>Style</code> regroupe l'apparence commune de l'application : couleurs,
 * polices, marges et dimensions. La mise en forme des boutons est dans {@link StyleBouton},
 * celle des autres composants dans {@link StyleComposant}, et le défilement dans
 * {@link ZoneDefilante}. Changer les couleurs ou les polices revient à modifier uniquement
 * cette classe.
 * <p>
 * Le style est moderne et plat : fonds gris clair (un peu plus foncé pour les panneaux
 * latéraux), accent violet pour les actions principales, vert menthe pour les validations
 * et rouge pour les suppressions. Les textes respectent un contraste suffisant avec leur
 * fond (par exemple, le texte des boutons menthe est sombre, car le blanc y serait peu lisible).
 *
 * @version 2.1
 * @author Nkouka Ildevert
 */
public final class Style {

    // ------------------------------------------------------------------ Couleurs de fond

    /**
     * Fond du panneau central : gris perle.
     */
    public static final Color FOND_CENTRAL = new Color(248, 250, 252);

    /**
     * Fond des panneaux latéraux (gauche et droit) : gris un peu plus foncé.
     */
    public static final Color FOND_LATERAL = new Color(236, 239, 244);

    /**
     * Fond d'un champ de saisie ou d'une ligne de contact : blanc.
     */
    public static final Color FOND_BLANC = Color.WHITE;

    /**
     * Fond du contenu : même couleur que le panneau central (nom conservé pour les classes
     * qui l'utilisent déjà).
     */
    public static final Color FOND_CONTENU = FOND_CENTRAL;

    /**
     * Fond des panneaux latéraux (nom conservé pour les classes qui l'utilisent déjà).
     */
    public static final Color FOND_PANNEAU_LATERAL = FOND_LATERAL;

    // ------------------------------------------------------------------ Couleurs de texte

    /**
     * Couleur du texte courant : noir d'encre.
     */
    public static final Color TEXTE_PRINCIPAL = new Color(15, 23, 42);

    /**
     * Couleur du texte placé sur un bouton violet.
     */
    public static final Color TEXTE_SUR_BOUTON = Color.WHITE;

    /**
     * Couleur d'un texte discret (messages d'invite, informations secondaires). Son contraste
     * avec les fonds gris reste supérieur à 4,5.
     */
    public static final Color TEXTE_DISCRET = new Color(71, 85, 105);

    // ------------------------------------------------------------------ Couleurs d'accentuation

    /**
     * Violet de l'application : boutons principaux et surbrillance.
     */
    public static final Color VIOLET_ATOZ = new Color(99, 102, 241);

    /**
     * Violet plus foncé : boutons principaux survolés ou enfoncés, texte sur fond violet clair.
     */
    public static final Color VIOLET_FONCE = new Color(67, 56, 202);

    /**
     * Vert menthe : boutons de validation.
     */
    public static final Color VERT_MENTHE = new Color(20, 184, 166);

    /**
     * Rouge des actions destructrices (suppression).
     */
    public static final Color ROUGE_DANGER = new Color(185, 28, 28);

    /**
     * Couleur d'accentuation (nom conservé pour les classes qui l'utilisent déjà).
     */
    public static final Color COULEUR_PRINCIPALE = VIOLET_ATOZ;

    /**
     * Couleur du texte sur un fond d'accentuation (nom conservé pour les classes qui
     * l'utilisent déjà).
     */
    public static final Color TEXTE_SUR_PRINCIPALE = TEXTE_SUR_BOUTON;

    /**
     * Couleur des actions destructrices (nom conservé pour les classes qui l'utilisent déjà).
     */
    public static final Color COULEUR_DANGER = ROUGE_DANGER;

    // ------------------------------------------------------------------ Couleurs dérivées

    /**
     * Fond de l'élément sélectionné (ligne de contact, bouton du menu) : violet très clair.
     */
    public static final Color FOND_SELECTION = new Color(224, 231, 255);

    /**
     * Fond d'une ligne de contact.
     */
    public static final Color FOND_LIGNE = FOND_BLANC;

    /**
     * Couleur des bordures des champs et des boutons secondaires.
     */
    public static final Color BORDURE = new Color(203, 213, 225);

    /**
     * Couleur des filets discrets (séparateur de lignes, cadre des zones de défilement).
     */
    public static final Color FILET = new Color(226, 232, 240);

    /**
     * Fond d'un bouton de menu survolé.
     */
    public static final Color SURVOL_MENU = new Color(224, 228, 238);

    /**
     * Fond d'un bouton de menu enfoncé.
     */
    public static final Color APPUI_MENU = new Color(210, 216, 230);

    /**
     * Fond d'un bouton secondaire survolé.
     */
    public static final Color SURVOL_SECONDAIRE = new Color(241, 245, 249);

    /**
     * Fond d'un bouton secondaire enfoncé.
     */
    public static final Color APPUI_SECONDAIRE = new Color(226, 232, 240);

    /**
     * Fond d'un bouton de suppression survolé.
     */
    public static final Color SURVOL_DANGER = new Color(254, 226, 226);

    /**
     * Fond d'un bouton de suppression enfoncé.
     */
    public static final Color APPUI_DANGER = new Color(252, 205, 205);

    /**
     * Fond d'un bouton de validation survolé.
     */
    public static final Color SURVOL_MENTHE = new Color(45, 212, 191);

    /**
     * Fond d'un bouton de validation enfoncé.
     */
    public static final Color APPUI_MENTHE = new Color(13, 148, 136);

    /**
     * Fond d'un bouton violet survolé.
     */
    public static final Color SURVOL_VIOLET = new Color(79, 70, 229);

    /**
     * Fond du texte sélectionné dans un champ de saisie.
     */
    public static final Color SELECTION_TEXTE = new Color(199, 210, 254);

    // ------------------------------------------------------------------ Polices

    /**
     * Famille de police utilisée : la première disponible parmi des polices modernes,
     * sinon la police sans empattement par défaut de Java.
     */
    private static final String FAMILLE = choisirFamille();

    /**
     * Police du texte courant.
     */
    public static final Font POLICE_TEXTE = new Font(FAMILLE, Font.PLAIN, 13);

    /**
     * Police du texte en gras (boutons principaux, noms de contacts).
     */
    public static final Font POLICE_GRAS = new Font(FAMILLE, Font.BOLD, 13);

    /**
     * Police des titres de section.
     */
    public static final Font POLICE_TITRE = new Font(FAMILLE, Font.BOLD, 15);

    /**
     * Police des grands titres de page.
     */
    public static final Font POLICE_GRAND_TITRE = new Font(FAMILLE, Font.BOLD, 20);

    // ------------------------------------------------------------------ Dimensions

    /**
     * Largeur fixe du panneau de gauche, en pixels.
     */
    public static final int LARGEUR_PANNEAU_GAUCHE = 230;

    /**
     * Largeur fixe du panneau de droite, en pixels.
     */
    public static final int LARGEUR_PANNEAU_DROIT = 260;

    // ------------------------------------------------------------------ Marges

    /**
     * Marges autour des éléments d'un panneau latéral.
     */
    public static final Insets MARGE_PANNEAU = new Insets(5, 12, 5, 12);

    /**
     * Marges autour des éléments d'un contenu (répertoire, liste de diffusion).
     */
    public static final Insets MARGE_ELEMENT = new Insets(6, 6, 6, 6);

    /**
     * Marges autour des éléments d'un formulaire.
     */
    public static final Insets MARGE_FORMULAIRE = new Insets(6, 16, 6, 16);

    /**
     * Constructeur privé : la classe n'est pas instanciable.
     */
    private Style() {
    }

    /**
     * Choisit la famille de police : « Segoe UI » (Windows) ou « Helvetica Neue » (macOS) si
     * elles sont installées, sinon la police sans empattement par défaut de Java, qui s'affiche
     * correctement sur tous les systèmes.
     *
     * @return le nom de la famille de police
     */
    private static String choisirFamille() {
        String[] souhaitees = {"Segoe UI", "Helvetica Neue"};
        String[] disponibles = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String souhaitee : souhaitees) {
            for (String disponible : disponibles) {
                if (disponible.equals(souhaitee)) {
                    return souhaitee;
                }
            }
        }
        return Font.SANS_SERIF;
    }

    /**
     * Applique la bordure vide qui entoure le contenu des pages du panneau central.
     *
     * @param composant le composant à mettre en forme
     */
    public static void bordureContenu(JComponent composant) {
        composant.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    /**
     * Applique la bordure d'une ligne de la liste : un filet en bas et un espace intérieur.
     * Une ligne sélectionnée a en plus une barre violette à gauche, sans changer
     * l'encombrement de la ligne.
     *
     * @param composant la ligne à mettre en forme
     * @param selectionnee <code>true</code> si la ligne est sélectionnée
     */
    public static void bordureLigne(JComponent composant, boolean selectionnee) {
        if (selectionnee) {
            composant.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 1, 0, VIOLET_ATOZ),
                BorderFactory.createEmptyBorder(10, 8, 10, 10)
            ));
        } else {
            composant.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, FILET),
                BorderFactory.createEmptyBorder(10, 12, 10, 10)
            ));
        }
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
