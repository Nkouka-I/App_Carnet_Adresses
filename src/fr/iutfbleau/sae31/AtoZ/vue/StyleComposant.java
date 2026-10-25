package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * La classe <code>StyleComposant</code> met en forme les composants Swing autres que les
 * boutons (champs de saisie, listes, cases à cocher, zones de défilement, boîtes de message).
 * La méthode {@link #installer()} fixe l'apparence par défaut de tous les composants ; la méthode
 * {@link #moderniser(Component)} met en forme un panneau déjà construit et tout ce qu'il contient.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public final class StyleComposant {

    /**
     * Constructeur privé : la classe n'est pas instanciable.
     */
    private StyleComposant() {
    }

    /**
     * Installe l'apparence par défaut de tous les composants Swing de l'application
     * (couleurs, polices, bordures des champs et des zones de défilement, boîtes de
     * message). À appeler une fois, avant la création de la première fenêtre.
     */
    public static void installer() {
        UIManager.put("Panel.background", Style.FOND_CENTRAL);
        UIManager.put("OptionPane.background", Style.FOND_CENTRAL);
        UIManager.put("OptionPane.messageForeground", Style.TEXTE_PRINCIPAL);
        UIManager.put("OptionPane.messageFont", Style.POLICE_TEXTE);
        UIManager.put("OptionPane.buttonFont", Style.POLICE_TEXTE);

        UIManager.put("Label.font", Style.POLICE_TEXTE);
        UIManager.put("Label.foreground", Style.TEXTE_PRINCIPAL);
        UIManager.put("CheckBox.font", Style.POLICE_TEXTE);
        UIManager.put("CheckBox.foreground", Style.TEXTE_PRINCIPAL);
        UIManager.put("Button.font", Style.POLICE_TEXTE);
        UIManager.put("Button.foreground", Style.TEXTE_PRINCIPAL);
        UIManager.put("Button.background", Style.FOND_BLANC);
        // Couleur du fond d'un bouton enfoncé : transparente, car Style gère lui-même cette couleur
        UIManager.put("Button.select", new Color(0, 0, 0, 0));

        UIManager.put("TextField.font", Style.POLICE_TEXTE);
        UIManager.put("TextField.background", Style.FOND_BLANC);
        UIManager.put("TextField.foreground", Style.TEXTE_PRINCIPAL);
        UIManager.put("TextField.caretForeground", Style.VIOLET_FONCE);
        UIManager.put("TextField.selectionBackground", Style.SELECTION_TEXTE);
        UIManager.put("TextField.selectionForeground", Style.TEXTE_PRINCIPAL);
        UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Style.BORDURE), BorderFactory.createEmptyBorder(7, 9, 7, 9)));

        UIManager.put("ComboBox.font", Style.POLICE_TEXTE);
        UIManager.put("ComboBox.background", Style.FOND_BLANC);
        UIManager.put("ComboBox.foreground", Style.TEXTE_PRINCIPAL);
        UIManager.put("ComboBox.selectionBackground", Style.SELECTION_TEXTE);
        UIManager.put("ComboBox.selectionForeground", Style.TEXTE_PRINCIPAL);

        UIManager.put("List.font", Style.POLICE_TEXTE);
        UIManager.put("List.background", Style.FOND_BLANC);
        UIManager.put("List.foreground", Style.TEXTE_PRINCIPAL);
        UIManager.put("List.selectionBackground", Style.FOND_SELECTION);
        UIManager.put("List.selectionForeground", Style.TEXTE_PRINCIPAL);

        UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(Style.FILET));
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.thumb", Style.BORDURE);
        UIManager.put("ScrollBar.thumbHighlight", Style.BORDURE);
        UIManager.put("ScrollBar.thumbShadow", Style.BORDURE);
        UIManager.put("ScrollBar.thumbDarkShadow", Style.BORDURE);
        UIManager.put("ScrollBar.track", Style.FOND_LATERAL);
        UIManager.put("ScrollBar.trackHighlight", Style.FOND_LATERAL);
        UIManager.put("ScrollBar.background", Style.FOND_LATERAL);
        // Dégradé de la barre : trois couleurs identiques pour un rendu plat
        UIManager.put("ScrollBar.gradient", List.of(0.0f, 0.0f, Style.BORDURE, Style.BORDURE, Style.BORDURE));
    }

    /**
     * Applique le style d'un champ de saisie : fond blanc, fin cadre gris et espace intérieur.
     *
     * @param champ le champ à mettre en forme
     */
    public static void styliserChamp(JTextField champ) {
        champ.setBackground(Style.FOND_BLANC);
        champ.setForeground(Style.TEXTE_PRINCIPAL);
        champ.setCaretColor(Style.VIOLET_FONCE);
        champ.setSelectionColor(Style.SELECTION_TEXTE);
        champ.setSelectedTextColor(Style.TEXTE_PRINCIPAL);
        champ.setFont(Style.POLICE_TEXTE);
        champ.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Style.BORDURE), BorderFactory.createEmptyBorder(7, 9, 7, 9)));
    }

    /**
     * Applique le style d'une liste déroulante : fond blanc et cadre gris.
     *
     * @param liste la liste déroulante à mettre en forme
     */
    public static void styliserListeDeroulante(JComboBox<?> liste) {
        liste.setBackground(Style.FOND_BLANC);
        liste.setForeground(Style.TEXTE_PRINCIPAL);
        liste.setFont(Style.POLICE_TEXTE);
        liste.setBorder(BorderFactory.createLineBorder(Style.BORDURE));
    }

    /**
     * Applique le style d'une liste : fond blanc, sélection violet clair.
     *
     * @param liste la liste à mettre en forme
     */
    public static void styliserListe(JList<?> liste) {
        liste.setBackground(Style.FOND_BLANC);
        liste.setForeground(Style.TEXTE_PRINCIPAL);
        liste.setFont(Style.POLICE_TEXTE);
        liste.setSelectionBackground(Style.FOND_SELECTION);
        liste.setSelectionForeground(Style.TEXTE_PRINCIPAL);
    }

    /**
     * Applique le style d'une case à cocher : fond transparent et texte sombre.
     *
     * @param caseACocher la case à mettre en forme
     */
    public static void styliserCase(JCheckBox caseACocher) {
        caseACocher.setOpaque(false);
        caseACocher.setForeground(Style.TEXTE_PRINCIPAL);
        caseACocher.setFont(Style.POLICE_TEXTE);
        caseACocher.setFocusPainted(false);
    }

    /**
     * Applique le style d'une zone de défilement : fin cadre gris, et défilement à la
     * molette assez rapide.
     *
     * @param zone la zone de défilement à mettre en forme
     */
    public static void styliserDefilement(JScrollPane zone) {
        zone.setBorder(BorderFactory.createLineBorder(Style.FILET));
        zone.getVerticalScrollBar().setUnitIncrement(16);
        zone.getHorizontalScrollBar().setUnitIncrement(16);
    }

    /**
     * Met en forme un composant et tout ce qu'il contient : chaque bouton qui n'a pas déjà
     * un style est mis en forme comme un bouton secondaire, et les champs, listes déroulantes,
     * listes, cases à cocher et zones de défilement reçoivent leur style. À appeler quand un
     * panneau vient d'être construit.
     *
     * @param composant le composant (ou le conteneur) à mettre en forme
     */
    public static void moderniser(Component composant) {
        if (composant instanceof JButton) {
            JButton bouton = (JButton) composant;
            if (bouton.getClientProperty("role") == null) {
                StyleBouton.styliserBoutonSecondaire(bouton);
            }
        } else if (composant instanceof JTextField) {
            styliserChamp((JTextField) composant);
        } else if (composant instanceof JComboBox) {
            styliserListeDeroulante((JComboBox<?>) composant);
        } else if (composant instanceof JList) {
            styliserListe((JList<?>) composant);
        } else if (composant instanceof JCheckBox) {
            styliserCase((JCheckBox) composant);
        } else if (composant instanceof JScrollPane) {
            JScrollPane zone = (JScrollPane) composant;
            styliserDefilement(zone);
            moderniser(zone.getViewport().getView());
        } else if (composant instanceof Container) {
            for (Component enfant : ((Container) composant).getComponents()) {
                moderniser(enfant);
            }
        }
    }
}
