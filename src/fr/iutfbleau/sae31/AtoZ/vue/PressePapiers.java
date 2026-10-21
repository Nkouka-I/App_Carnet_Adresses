package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

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
     * Classe utilitaire
     */
    private PressePapiers() { }

    /**
     * Fait copier un texte par un bouton lorsqu'on clique dessus. Le texte est demandé
     * au moment du clic, donc il est toujours à jour. Si le texte est vide, rien n'est
     * copié et l'utilisateur en est informé.
     *
     * @param bouton le bouton qui déclenche la copie
     * @param texte le fournisseur du texte à copier
     */
    public static void brancher(JButton bouton, Supplier<String> texte) {
        final String libelleInitial = bouton.getText();

        bouton.addActionListener(e -> {
            Component parent = SwingUtilities.getWindowAncestor(bouton);
            String contenu = texte.get();

            if (contenu == null || contenu.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Il n'y a aucune adresse à copier.",
                    "Rien à copier", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (copier(parent, contenu)) {
                bouton.setText("Copié !");
                Timer retour = new Timer(DUREE_RETOUR_MS, t -> bouton.setText(libelleInitial));
                retour.setRepeats(false);
                retour.start();
            }
        });
    }

    /**
     * Place un texte dans le presse-papier du système. En cas d'échec (presse-papier
     * indisponible), un message d'erreur est affiché.
     *
     * @param parent le composant parent de la boîte de dialogue d'erreur
     * @param contenu le texte à copier
     * @return <code>true</code> si la copie a réussi
     */
    public static boolean copier(Component parent, String contenu) {
        try {
            Clipboard presse = Toolkit.getDefaultToolkit().getSystemClipboard();
            presse.setContents(new StringSelection(contenu), null);
            return true;
        } catch (Exception ex) { // HeadlessException, IllegalStateException, SecurityException
            JOptionPane.showMessageDialog(parent,
                "Impossible d'accéder au presse-papier :\n" + ex.getMessage(),
                "Copie impossible", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
