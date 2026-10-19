package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * La classe <code>PanneauOperationsAbstrait</code> est la base commune des panneaux
 * d'opérations affichés à droite de la fenêtre principale (contact, étiquette, liste
 * de diffusion). Elle regroupe le code partagé : préparation de la grille, bouton
 * "Modifier", titre, bouton "Supprimer" avec confirmation, et ouverture d'un formulaire.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public abstract class PanneauOperationsAbstrait extends JPanel {

    /**
     * Action exécutée une fois la suppression confirmée et effectuée
     * (en général : revenir à la vue vide du panneau de droite).
     */
    private final Runnable apresSuppression;

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
     * @param apresSuppression l'action à exécuter après une suppression réussie
     */
    protected PanneauOperationsAbstrait(Runnable apresSuppression) {
        this.apresSuppression = apresSuppression;
        setOpaque(false);
        setLayout(new GridBagLayout());
    }

    /**
     * Vide le panneau et réinitialise la grille. À appeler au début de la construction
     * (ou de la reconstruction) du contenu.
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
     * @param texteHtml le texte du titre (le format HTML de Swing est accepté)
     * @param alignement l'alignement horizontal (par exemple <code>JLabel.CENTER</code>)
     * @param marges les marges autour du titre
     */
    protected void ajouterTitre(String texteHtml, int alignement, Insets marges) {
        gbc.gridy = ligne++;
        gbc.insets = marges;

        JLabel lblTitre = new JLabel(texteHtml, alignement);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitre, gbc);
    }

    /**
     * Ajoute le bouton de suppression sur la ligne suivante. Un clic ouvre une
     * confirmation ; si l'utilisateur accepte, l'action est exécutée, un message de
     * succès est affiché, puis l'action <code>apresSuppression</code> est appelée.
     *
     * @param texteBouton le texte affiché sur le bouton
     * @param messageConfirmation le message de la boîte de confirmation
     * @param messageSucces le message affiché après la suppression
     * @param actionAEffectuer l'action à exécuter en cas de confirmation (peut être <code>null</code>)
     */
    protected void ajouterBoutonSuppression(String texteBouton, String messageConfirmation,
                                            String messageSucces, Runnable actionAEffectuer) {
        JButton btnSupprimer = new JButton(texteBouton);
        btnSupprimer.setForeground(Color.RED);

        btnSupprimer.addActionListener(e -> {

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

                // TODO: Code pour supprimer de la base de données
                if (actionAEffectuer != null) {
                    actionAEffectuer.run();
                }

                // pop-up de confirmation
                JOptionPane.showMessageDialog(
                    fenetreParente, 
                    messageSucces, 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE
                );

                // Remise à zéro de l'affichage
                apresSuppression.run();
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
        return (Frame) SwingUtilities.getWindowAncestor(this);
    }

    /**
     * Méthode générique pour ouvrir un formulaire et traiter sa validation via une interface.
     * Le formulaire est modal : l'appel est bloquant jusqu'à sa fermeture. L'écouteur
     * n'est appelé que si le formulaire a été validé.
     *
     * @param <T> le type de formulaire ouvert
     * @param formulaire le formulaire à afficher
     * @param listener l'écouteur appelé si le formulaire est validé
     */
    protected <T extends FenetreDialogueAbstraite> void ouvrirFormulaire(T formulaire, FormulaireListener<T> listener) {
        formulaire.setVisible(true);

        if (formulaire.isValide()) {
            listener.onValider(formulaire);
        }
    }
}