package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;
import java.awt.*;
import javax.swing.*;

/**
 * La classe <code>PanneauDroit</code> est le panneau de droite de la fenêtre principale.
 * Elle se contente d'afficher le bon panneau d'opérations selon l'élément sélectionné
 * (contact, étiquette ou liste de diffusion), ou un message d'invite lorsque rien
 * n'est sélectionné. Le détail de chaque vue est délégué aux classes
 * <code>PanneauOperations...</code>, qui relisent les données de l'élément à chaque
 * affichage.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauDroit extends JPanel{

    /**
     * Contrôleur transmis aux panneaux d'opérations.
     */
    private final Controleur controleur;

    /**
     * Construit le panneau de droite avec une largeur fixe et affiche la vue vide.
     *
     * @param controleur le contrôleur transmis aux panneaux d'opérations
     */
    public PanneauDroit(Controleur controleur){
        this.controleur = controleur;

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Style.LARGEUR_PANNEAU_DROIT, 0)); // Limité à une largeur fixe
        setBackground(Style.FOND_PANNEAU_LATERAL);

        afficherVide();
    }

    /**
     * Affiche la vue par défaut, invitant l'utilisateur à sélectionner un élément.
     * Le contenu précédent du panneau est effacé.
     */
    public void afficherVide() {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = Style.MARGE_PANNEAU;

        JLabel lblInfo = new JLabel("<html><center>Sélectionnez un élément<br>pour afficher ses détails</center></html>");
        lblInfo.setForeground(Style.TEXTE_DISCRET);
        add(lblInfo, gbc);

        revalidate();
        repaint();
    }

    /**
     * Remplace le contenu du panneau par le panneau d'opérations donné.
     *
     * @param contenu le panneau d'opérations à afficher
     */
    private void afficher(JPanel contenu) {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(contenu, gbc);

        revalidate();
        repaint();
    }

    /**
     * Vue dédiée lorsqu'un CONTACT est sélectionné.
     *
     * @param contact le contact sélectionné
     */
    public void afficherOperationsContact(ContactVue contact) {
        afficher(new PanneauOperationsContact(controleur, contact));
    }

    /**
     * Vue dédiée lorsqu'une ÉTIQUETTE est sélectionnée.
     *
     * @param etiquette l'étiquette sélectionnée
     */
    public void afficherOperationsEtiquette(EtiquetteVue etiquette) {
        afficher(new PanneauOperationsEtiquette(controleur, etiquette));
    }

    /**
     * Vue dédiée lorsqu'une LISTE DE DIFFUSION est sélectionnée.
     *
     * @param liste la liste de diffusion sélectionnée
     */
    public void afficherOperationsListeDiffusion(ListeDiffusionVue liste) {
        afficher(new PanneauOperationsListeDiffusion(controleur, liste));
    }
}
