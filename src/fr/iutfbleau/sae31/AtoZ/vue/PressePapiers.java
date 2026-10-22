package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JButton;

/**
 * La classe <code>PressePapiers</code> regroupe la copie de texte dans le presse-papier
 * du système, utilisée par les boutons « Copier » (un contact) et « Copier tout » (une
 * liste de diffusion). Elle prévient l'utilisateur du résultat : le bouton affiche
 * brièvement « Copié ! », ou une boîte de dialogue signale l'échec.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public final class PressePapiers {

    /**
     * Durée d'affichage du message « Copié ! » sur le bouton, en millisecondes.
     */
    private static final int DUREE_RETOUR_MS = 1500;

    /**
     * Clé sous laquelle un bouton mémorise son texte d'origine pendant l'affichage de « Copié ! ».
     */
    private static final String CLE_LIBELLE = "libelleInitial";

    /**
     * Classe utilitaire : elle ne s'instancie pas.
     */
    private PressePapiers() { }

    /**
     * Copie un texte dans le presse-papier, à appeler quand l'utilisateur clique sur le
     * bouton. Le bouton affiche « Copié ! » un instant. Si le texte est vide, rien n'est
     * copié et l'utilisateur en est informé.
     *
     * @param bouton le bouton qui a déclenché la copie
     * @param texte le texte à copier
     */
    public static void copier(JButton bouton, String texte) {
        Component parent = SwingUtilities.getWindowAncestor(bouton);

        if (texte == null || texte.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Il n'y a aucune adresse à copier.",
                "Rien à copier", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!placer(parent, texte)) {
            return;
        }

        // Le texte d'origine est mémorisé une fois : un second clic rapide ne doit pas le perdre
        String origine = (String) bouton.getClientProperty(CLE_LIBELLE);
        if (origine == null) {
            origine = bouton.getText();
            bouton.putClientProperty(CLE_LIBELLE, origine);
        }
        final String libelle = origine;

        bouton.setText("Copié !");
        Timer retour = new Timer(DUREE_RETOUR_MS, t -> bouton.setText(libelle));
        retour.setRepeats(false);
        retour.start();
    }

    /**
     * Place un texte dans le presse-papier du système. En cas d'échec (presse-papier
     * indisponible), un message d'erreur est affiché.
     *
     * @param parent le composant parent de la boîte de dialogue d'erreur
     * @param contenu le texte à copier
     * @return <code>true</code> si la copie a réussi
     */
    private static boolean placer(Component parent, String contenu) {
        try {
            Clipboard presse = Toolkit.getDefaultToolkit().getSystemClipboard();
            presse.setContents(new StringSelection(contenu), null);
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent,
                "Impossible d'accéder au presse-papier :\n" + ex.getMessage(),
                "Copie impossible", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
