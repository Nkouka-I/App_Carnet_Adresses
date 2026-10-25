package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * La classe <code>FenetreDialogueAbstraite</code> est la base commune de tous les
 * formulaires de l'application. Il s'agit d'une boîte de dialogue modale (bloquante)
 * qui fournit un <code>GridBagLayout</code> préconfiguré, le placement automatique des
 * composants ligne par ligne et les boutons standards "Annuler" et "Valider".
 * Chaque classe fille définit sa propre logique de validation.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public abstract class FenetreDialogueAbstraite extends JDialog {

    /**
     * Indique si le formulaire a été validé (<code>true</code>) ou annulé (<code>false</code>).
     */
    private boolean valide = false;

    /**
     * Contraintes de placement utilisées pour ajouter chaque composant.
     */
    private final GridBagConstraints gbc;

    /**
     * Compteur interne pour placer automatiquement chaque composant sur la ligne suivante.
     */
    private int ligne = 0;

    /**
     * Largeur minimale de la boîte de dialogue, en pixels.
     */
    private final int largeurMin;

    /**
     * Hauteur minimale de la boîte de dialogue, en pixels.
     */
    private final int hauteurMin;

    /**
     * Construit une boîte de dialogue modale centrée sur sa fenêtre parente.
     *
     * @param parent la fenêtre parente
     * @param titre le titre de la boîte de dialogue
     * @param largeur la largeur de la boîte de dialogue en pixels
     * @param hauteur la hauteur de la boîte de dialogue en pixels
     */
    public FenetreDialogueAbstraite(Frame parent, String titre, int largeur, int hauteur) {
        super(parent, titre, true); // bloquant

        setSize(largeur, hauteur);
        this.largeurMin = largeur;
        this.hauteurMin = hauteur;
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Style.FOND_CENTRAL);

        gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_FORMULAIRE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
    }

    /**
     * Ajoute un composant sur la ligne suivante du formulaire en gérant automatiquement gridy.
     *
     * @param comp le composant graphique à ajouter
     */
    protected void ajouter(Component comp) {
        gbc.gridy = ligne++;
        add(comp, gbc);
    }

    /**
     * Crée et ajoute, sur la ligne suivante, le panneau standard des boutons "Annuler"
     * et "Valider". Le bouton "Annuler" ferme la fenêtre sans valider, le bouton
     * "Valider" appelle {@link #validerFormulaire()}.
     */
    protected void ajouterBoutonsValidation() {
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelBoutons.setOpaque(false);

        JButton btnAnnuler = new JButton("Annuler");
        JButton btnValider = new JButton("Valider");
        StyleBouton.styliserBoutonSecondaire(btnAnnuler);
        StyleBouton.styliserBoutonValidation(btnValider);

        panelBoutons.add(btnAnnuler);
        panelBoutons.add(btnValider);
        ajouter(panelBoutons);

        // Action par défaut pour Annuler
        btnAnnuler.addActionListener(e -> dispose());

        // Action de validation gérée par les classes fille
        btnValider.addActionListener(e -> validerFormulaire());

        // Les boutons de validation sont ajoutés en dernier : on met en forme tout le formulaire
        StyleComposant.moderniser(getContentPane());

        // La boîte s'agrandit si son contenu est trop grand pour la taille demandée
        // (police plus grosse, par exemple), afin que rien ne soit coupé.
        // addNotify() crée d'abord la fenêtre native : sans cela, la taille de la barre de
        // titre et des bordures est inconnue au premier affichage et le contenu est écrasé.
        addNotify();
        pack();
        setSize(Math.max(getWidth(), largeurMin), Math.max(getHeight(), hauteurMin));
        setLocationRelativeTo(getOwner());
    }

    /**
     * Chaque classe fille implémente sa propre logique de validation.
     * En cas de saisie correcte, elle doit appeler {@link #validerEtFermer()}.
     */
    protected abstract void validerFormulaire();

    /**
     * Valide le formulaire et ferme la boîte de dialogue.
     */
    protected void validerEtFermer() {
        valide = true;
        dispose();
    }

    /**
     * Affiche un message d'erreur de saisie ; le formulaire reste ouvert.
     *
     * @param titre le titre de la boîte de message
     * @param message le détail de l'erreur (le préfixe "Erreur : " est ajouté automatiquement)
     */
    protected void afficherErreur(String titre, String message) {
        JOptionPane.showMessageDialog(this, "Erreur : " + message, titre, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Indique si le formulaire a été validé par l'utilisateur.
     *
     * @return <code>true</code> si le formulaire a été validé, <code>false</code> s'il a été annulé
     */
    public boolean isValide() {
        return valide;
    }

    /**
     * Retrouve la fenêtre principale qui contient un composant, pour y rattacher un formulaire.
     *
     * @param composant un composant affiché dans la fenêtre
     * @return la fenêtre parente, ou <code>null</code> si le composant n'est pas dans une fenêtre
     */
    public static Frame fenetreDe(Component composant) {
        Window fenetre = SwingUtilities.getWindowAncestor(composant);
        return (fenetre instanceof Frame) ? (Frame) fenetre : null;
    }

    /**
     * Ouvre un formulaire modal et exécute une action uniquement s'il a été validé.
     * L'appel est bloquant jusqu'à la fermeture du formulaire.
     *
     * @param <T> le type de la fenêtre de dialogue
     * @param formulaire l'instance du dialogue modal à ouvrir
     * @param listener l'action à exécuter en cas de validation réussie
     */
    public static <T extends FenetreDialogueAbstraite> void ouvrirFormulaire(T formulaire, FormulaireListener<T> listener) {
        formulaire.setVisible(true);
        if (formulaire.isValide()) {
            listener.onValider(formulaire);
        }
    }
}
