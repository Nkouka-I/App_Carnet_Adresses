package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * La classe <code>PanneauGauche</code> est le panneau de navigation de gauche de la
 * fenêtre principale. Il propose les boutons de création (contact, étiquette, liste de
 * diffusion) ainsi que deux menus déroulants (accordéons) listant les étiquettes et
 * les listes de diffusion.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauGauche extends JPanel {

    /**
     * Bouton de création d'un nouveau contact.
     */
    private final JButton btnNewContact;

    /**
     * Bouton de création d'une nouvelle étiquette.
     */
    private final JButton btnNewEtiquette;

    /**
     * Bouton de création d'une nouvelle liste de diffusion.
     */
    private final JButton btnNewListesDiffusion;

    /**
     * Titre de l'application affiché en haut du panneau.
     */
    private final JLabel titreApp;

    /**
     * Bouton pour afficher tous les contacts.
     */
    private final JButton btnTousContacts;

    /**
     * Bouton qui déplie ou replie la liste des étiquettes.
     */
    private final JButton btnEtiquettes;

    /**
     * Bouton qui déplie ou replie la liste des listes de diffusion.
     */
    private final JButton btnListesDiffusion;

    /**
     * Indique si la liste des étiquettes est actuellement dépliée.
     */
    private boolean etiquettesVisibles = false;

    /**
     * Indique si la liste des listes de diffusion est actuellement dépliée.
     */
    private boolean listesDiffusionVisibles = false;

    /**
     * L'écouteur de navigation.
     */
    private final NavigationListener listener;

    /**
     * Contraintes de placement pour la grille de disposition.
     */
    private final GridBagConstraints gbc;

    /**
     * Compteur interne pour placer automatiquement chaque composant sur la ligne suivante.
     */
    private int ligne = 0;

    /**
     * Construit le panneau de gauche, initialise ses composants et configure
     * une seule fois les écouteurs pour éviter toute fuite ou accumulation.
     *
     * @param listener l'écouteur à notifier lors d'un clic sur une navigation
     */
    public PanneauGauche(NavigationListener listener) {
        this.listener = listener;

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Style.LARGEUR_PANNEAU_GAUCHE, 0)); // Limité à une largeur fixe
        setBackground(Style.FOND_PANNEAU_LATERAL);

        gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_PANNEAU;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        titreApp = new JLabel("AtoZ Carnet", JLabel.CENTER);

        btnNewContact = new JButton("+ Contact");
        btnNewContact.addActionListener(e -> creerContact());

        btnNewEtiquette = new JButton("+ Etiquette");
        btnNewEtiquette.addActionListener(e -> creerEtiquette());

        btnNewListesDiffusion = new JButton("+ Listes de Diffusion");
        btnNewListesDiffusion.addActionListener(e -> creerListeDiffusion());

        btnTousContacts = new JButton("Tous les contacts");
        btnTousContacts.addActionListener(e -> {
            if (listener != null) {
                listener.clicContact(null, null, null);
            }
            rafraichirInterface();
        });

        btnEtiquettes = new JButton();
        btnEtiquettes.addActionListener(e -> basculerEtiquettes());

        btnListesDiffusion = new JButton();
        btnListesDiffusion.addActionListener(e -> basculerListesDiffusion());

        rafraichirInterface();
    }

    /**
     * Ouvre le formulaire de création d'un contact.
     */
    private void creerContact() {
        FenetreFormulaireContact form = new FenetreFormulaireContact(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer un nouveau contact", "", "", DonneesTest.etiquettes(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            String nom = f.getNomSaisi();
            String email = f.getEmailSaisi();

            System.out.println("Nouveau contact créé : " + nom + " <" + email + ">");
            if (listener != null) {
                listener.clicContact(nom, email, f.getEtiquettesSelectionnees());
            }
        });
    }

    /**
     * Ouvre le formulaire de création d'une étiquette.
     */
    private void creerEtiquette() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer une nouvelle étiquette", ""
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            System.out.println("Nouvelle étiquette créée : " + f.getLibelleSaisi());
            rafraichirInterface();
        });
    }

    /**
     * Ouvre le formulaire de création d'une liste de diffusion.
     */
    private void creerListeDiffusion() {
        FenetreFormulaireListeDiffusion form = new FenetreFormulaireListeDiffusion(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer une liste de diffusion", "", DonneesTest.etiquettes(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            System.out.println("Nouvelle liste créée : " + f.getNomListeSaisi());
            rafraichirInterface();
        });
    }

    /**
     * Déplie ou replie la liste des étiquettes. Au dépliage, le listener est notifié
     * avec <code>null</code>.
     */
    private void basculerEtiquettes() {
        etiquettesVisibles = !etiquettesVisibles;

        if (etiquettesVisibles && listener != null) {
            listener.clicContactEtiquette(null);
        }

        rafraichirInterface();
    }

    /**
     * Déplie ou replie la liste des listes de diffusion. Au dépliage, le listener est
     * notifié avec <code>null</code>.
     */
    private void basculerListesDiffusion() {
        listesDiffusionVisibles = !listesDiffusionVisibles;

        if (listesDiffusionVisibles && listener != null) {
            listener.clicListeDiffusion(null);
        }

        rafraichirInterface();
    }

    /**
     * Ajoute un composant sur la ligne suivante.
     *
     * @param comp le composant à ajouter
     */
    private void ajouter(Component comp) {
        gbc.gridy = ligne++;
        add(comp, gbc);
    }

    /**
     * Ajoute un bouton par nom, chacun déclenchant l'action donnée avec son nom.
     *
     * @param noms les noms à afficher
     * @param action l'action exécutée au clic, avec le nom du bouton cliqué
     */
    private void ajouterBoutons(List<String> noms, Consumer<String> action) {
        for (String nom : noms) {
            JButton btn = new JButton(nom);
            btn.addActionListener(e -> action.accept(nom));
            ajouter(btn);
        }
    }

    /**
     * Reconstruit entièrement le contenu visuel du panneau selon l'état des accordéons.
     */
    private void rafraichirInterface() {
        removeAll();
        ligne = 0;

        ajouter(titreApp);
        ajouter(btnNewContact);
        ajouter(btnTousContacts);

        // Étiquettes : bouton de création et accordéon
        ajouter(btnNewEtiquette);

        btnEtiquettes.setText(etiquettesVisibles ? "▼ Étiquettes :" : "▶ Étiquettes :");
        ajouter(btnEtiquettes);

        if (etiquettesVisibles) {
            ajouterBoutons(DonneesTest.etiquettes(), nom -> {
                if (listener != null) {
                    listener.clicContactEtiquette(nom);
                }
            });
        }

        // Listes de diffusion : bouton de création et accordéon
        ajouter(btnNewListesDiffusion);

        btnListesDiffusion.setText(listesDiffusionVisibles ? "▼ Listes de Diffusion :" : "▶ Listes de Diffusion :");
        ajouter(btnListesDiffusion);

        if (listesDiffusionVisibles) {
            ajouterBoutons(DonneesTest.listesDiffusion(), nom -> {
                if (listener != null) {
                    listener.clicListeDiffusion(nom);
                }
            });
        }

        // Pousse le contenu vers le haut
        gbc.weighty = 1.0;
        ajouter(Style.ressort());
        gbc.weighty = 0.0;

        // Rafraîchissement visuel
        revalidate();
        repaint();
    }
}
