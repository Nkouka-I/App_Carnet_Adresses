package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * La classe <code>PanneauOperationsAbstrait</code> est la base commune des panneaux
 * d'opérations affichés à droite de la fenêtre principale (contact, étiquette, liste
 * de diffusion). Elle regroupe le code partagé : préparation de la grille, bouton
 * "Modifier", titre, texte simple et bouton "Supprimer" avec confirmation. Elle ne
 * garde aucune donnée : c'est le contrôleur qui modifie le carnet, puis la fenêtre
 * principale reconstruit le panneau.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public abstract class PanneauOperationsAbstrait extends JPanel {

    /**
     * Contrôleur auquel les opérations sont demandées.
     */
    private final Controleur controleur;

    /**
     * Contraintes de placement courantes, recréées à chaque appel de {@link #preparer()}.
     */
    private GridBagConstraints gbc;

    /**
     * Indice de la prochaine ligne libre de la grille.
     */
    private int ligne;

    /**
     * Construit un panneau d'opérations transparent muni d'un <code>GridBagLayout</code>.
     *
     * @param controleur le contrôleur auquel les opérations sont demandées
     */
    protected PanneauOperationsAbstrait(Controleur controleur) {
        this.controleur = controleur;
        setOpaque(false);
        setLayout(new GridBagLayout());
    }

    /**
     * Renvoie le contrôleur auquel les opérations sont demandées.
     *
     * @return le contrôleur
     */
    protected Controleur getControleur() {
        return controleur;
    }

    /**
     * Vide le panneau et réinitialise la grille. À appeler au début de la construction
     * du contenu.
     */
    protected void preparer() {
        removeAll();

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        ligne = 0;
    }

    /**
     * Rafraîchit l'affichage. À appeler à la fin de la construction du contenu.
     */
    protected void terminer() {
        revalidate();
        repaint();
    }

    /**
     * Ajoute le bouton de modification sur la ligne suivante.
     *
     * @param texte le texte affiché sur le bouton
     * @param action l'action exécutée au clic
     */
    protected void ajouterBoutonModifier(String texte, ActionListener action) {
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 5, 10);

        JButton btnModifier = new JButton(texte);
        btnModifier.addActionListener(action);
        add(btnModifier, gbc);
    }

    /**
     * Ajoute un titre en gras sur la ligne suivante.
     *
     * @param texte le texte du titre
     * @param alignement l'alignement horizontal (par exemple <code>JLabel.CENTER</code>)
     * @param marges les marges autour du titre
     */
    protected void ajouterTitre(String texte, int alignement, Insets marges) {
        gbc.gridy = ligne++;
        gbc.insets = marges;

        JLabel lblTitre = new JLabel(texte, alignement);
        lblTitre.setFont(Style.POLICE_TITRE);
        add(lblTitre, gbc);
    }

    /**
     * Ajoute une ligne de texte simple sur la ligne suivante.
     *
     * @param texte le texte à afficher
     * @param marges les marges autour du texte
     */
    protected void ajouterTexte(String texte, Insets marges) {
        gbc.gridy = ligne++;
        gbc.insets = marges;

        add(new JLabel(texte), gbc);
    }

    /**
     * Ajoute le bouton de suppression sur la ligne suivante. Un clic ouvre une
     * confirmation ; si l'utilisateur accepte, l'action est exécutée (elle doit demander
     * la suppression au contrôleur). Aucun message de succès n'est affiché : la
     * disparition de l'élément à l'écran suffit. Le panneau est ensuite remplacé par la
     * fenêtre principale, prévenue par le contrôleur.
     *
     * @param texteBouton le texte affiché sur le bouton
     * @param messageConfirmation le message de la boîte de confirmation
     * @param actionAEffectuer l'action à exécuter en cas de confirmation (peut être <code>null</code>)
     */
    protected void ajouterBoutonSuppression(String texteBouton, String messageConfirmation,
                                            Runnable actionAEffectuer) {
        JButton btnSupprimer = new JButton(texteBouton);
        Style.styliserBoutonDanger(btnSupprimer);

        btnSupprimer.addActionListener(e -> {

            // Mémorisée avant la suppression : ensuite ce panneau ne sera plus dans la fenêtre
            Window fenetreParente = SwingUtilities.getWindowAncestor(this);

            // Le pop-up bloquant
            int reponse = JOptionPane.showConfirmDialog(
                fenetreParente,
                messageConfirmation,
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (reponse == JOptionPane.YES_OPTION) {

                if (actionAEffectuer != null) {
                    actionAEffectuer.run();
                }
            }
        });

        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(btnSupprimer, gbc);
    }

    /**
     * Renvoie la fenêtre principale qui contient ce panneau, pour y rattacher un formulaire.
     *
     * @return la fenêtre parente
     */
    protected Frame getFenetreParente() {
        return FenetreDialogueAbstraite.fenetreDe(this);
    }
}
