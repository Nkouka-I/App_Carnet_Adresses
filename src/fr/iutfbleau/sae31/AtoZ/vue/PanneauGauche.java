package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.*;

/**
 * La classe <code>PanneauGauche</code> est le panneau de navigation de gauche de la
 * fenêtre principale. Il propose les boutons de création (contact, étiquette, liste de
 * diffusion) ainsi que deux menus déroulants (accordéons) listant les étiquettes et
 * les listes de diffusion du carnet.
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
     * Contrôleur fournissant les étiquettes et les listes, et créant les éléments.
     */
    private final Controleur controleur;

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
     * @param controleur le contrôleur fournissant les données
     */
    public PanneauGauche(NavigationListener listener, Controleur controleur) {
        this.listener = listener;
        this.controleur = controleur;

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(Style.LARGEUR_PANNEAU_GAUCHE, 0)); // Limité à une largeur fixe
        setBackground(Style.FOND_PANNEAU_LATERAL);

        gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_PANNEAU;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        titreApp = new JLabel("AtoZ Carnet", JLabel.CENTER);

        btnNewContact = new JButton("+ ContactVue");
        btnNewContact.addActionListener(e -> creerContact());

        btnNewEtiquette = new JButton("+ EtiquetteVue");
        btnNewEtiquette.addActionListener(e -> creerEtiquette());

        btnNewListesDiffusion = new JButton("+ Listes de Diffusion");
        btnNewListesDiffusion.addActionListener(e -> creerListeDiffusion());

        btnTousContacts = new JButton("Tous les contacts");
        btnTousContacts.addActionListener(e -> {
            if (this.listener != null) {
                this.listener.clicTousLesContacts();
            }
        });

        btnEtiquettes = new JButton();
        btnEtiquettes.addActionListener(e -> basculerEtiquettes());

        btnListesDiffusion = new JButton();
        btnListesDiffusion.addActionListener(e -> basculerListesDiffusion());

        rafraichir();
    }

    /**
     * Ouvre le formulaire de création d'un contact, le crée s'il est validé, puis le
     * sélectionne.
     */
    private void creerContact() {
        FenetreFormulaireContact form = new FenetreFormulaireContact(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer un nouveau contact", "", "", controleur.getEtiquettes(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            ContactVue contact = controleur.creerContact(f.getNomSaisi(), f.getEmailSaisi(), f.getEtiquettesSelectionnees());
            if (listener != null) {
                listener.clicContact(contact);
            }
        });
    }

    /**
     * Ouvre le formulaire de création d'une étiquette et la crée s'il est validé.
     */
    private void creerEtiquette() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer une nouvelle étiquette", ""
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> controleur.creerEtiquette(f.getLibelleSaisi()));
    }

    /**
     * Ouvre le formulaire de création d'une liste de diffusion et la crée s'il est validé.
     */
    private void creerListeDiffusion() {
        FenetreFormulaireListeDiffusion form = new FenetreFormulaireListeDiffusion(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer une liste de diffusion", "", controleur.getEtiquettes(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> controleur.creerListeDiffusion(f.getNomListeSaisi(), f.getCriteresList()));
    }

    /**
     * Déplie ou replie la liste des étiquettes. Au dépliage, le listener est notifié.
     */
    private void basculerEtiquettes() {
        etiquettesVisibles = !etiquettesVisibles;

        if (etiquettesVisibles && listener != null) {
            listener.clicMenuEtiquettes();
        }

        rafraichir();
    }

    /**
     * Déplie ou replie la liste des listes de diffusion. Au dépliage, le listener est notifié.
     */
    private void basculerListesDiffusion() {
        listesDiffusionVisibles = !listesDiffusionVisibles;

        if (listesDiffusionVisibles && listener != null) {
            listener.clicMenuListesDiffusion();
        }

        rafraichir();
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
     * Ajoute un bouton par élément, chacun déclenchant l'action donnée avec son élément.
     *
     * @param <T> le type des éléments
     * @param elements les éléments à afficher
     * @param libelle la fonction qui donne le texte du bouton d'un élément
     * @param action l'action exécutée au clic, avec l'élément du bouton cliqué
     */
    private <T> void ajouterBoutons(List<T> elements, Function<T, String> libelle, Consumer<T> action) {
        for (T element : elements) {
            JButton btn = new JButton(libelle.apply(element));
            btn.addActionListener(e -> action.accept(element));
            ajouter(btn);
        }
    }

    /**
     * Reconstruit entièrement le contenu visuel du panneau selon l'état des accordéons
     * et les données actuelles du carnet.
     */
    public void rafraichir() {
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
            ajouterBoutons(controleur.getEtiquettes(), EtiquetteVue::getNom, etiquette -> {
                if (listener != null) {
                    listener.clicEtiquette(etiquette);
                }
            });
        }

        // Listes de diffusion : bouton de création et accordéon
        ajouter(btnNewListesDiffusion);

        btnListesDiffusion.setText(listesDiffusionVisibles ? "▼ Listes de Diffusion :" : "▶ Listes de Diffusion :");
        ajouter(btnListesDiffusion);

        if (listesDiffusionVisibles) {
            ajouterBoutons(controleur.getListesDiffusion(), ListeDiffusionVue::getNom, liste -> {
                if (listener != null) {
                    listener.clicListeDiffusion(liste);
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
