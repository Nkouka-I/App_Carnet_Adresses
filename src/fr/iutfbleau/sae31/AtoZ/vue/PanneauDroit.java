package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * La classe <code>PanneauDroit</code> est le panneau de droite de la fenêtre principale.
 * Elle se contente d'afficher le bon panneau d'opérations selon l'élément sélectionné
 * (contact, étiquette ou liste de diffusion), ou un message d'invite lorsque rien
 * n'est sélectionné. Le détail de chaque vue est délégué aux classes
 * <code>PanneauOperations...</code>.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauDroit extends JPanel{

    /**
     * Construit le panneau de droite avec une largeur fixe et affiche la vue vide.
     */
    public PanneauDroit(){
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
     * @param nomActuel le nom actuel du contact
     * @param emailActuel l'email actuel du contact
     * @param toutesLesEtiquettes toutes les étiquettes disponibles
     * @param etiquettesActuelles les étiquettes actuellement associées au contact
     */
    public void afficherOperationsContact(String nomActuel, String emailActuel,
                                        List<String> toutesLesEtiquettes, List<String> etiquettesActuelles) {
        afficher(new PanneauOperationsContact(
            this::afficherVide, nomActuel, emailActuel, toutesLesEtiquettes, etiquettesActuelles
        ));
    }

    /**
     * Vue dédiée lorsqu'une ÉTIQUETTE est sélectionnée.
     *
     * @param nomEtiquette le libellé de l'étiquette sélectionnée
     */
    public void afficherOperationsEtiquette(String nomEtiquette) {
        afficher(new PanneauOperationsEtiquette(this::afficherVide, nomEtiquette));
    }

    /**
     * Vue dédiée lorsqu'une LISTE DE DIFFUSION est sélectionnée.
     *
     * @param nomListe le nom de la liste de diffusion sélectionnée
     * @param toutesLesEtiquettes toutes les étiquettes disponibles pour définir un critère
     * @param criteresActuels les critères actuellement définis pour la liste
     */
    public void afficherOperationsListeDiffusion(String nomListe, List<String> toutesLesEtiquettes, List<CritereData> criteresActuels) {
        afficher(new PanneauOperationsListeDiffusion(
            this::afficherVide, nomListe, toutesLesEtiquettes, criteresActuels
        ));
    }
}
