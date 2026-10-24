package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * La classe <code>PanneauGauche</code> est le panneau de navigation de gauche de la
 * fenêtre principale. Il propose les boutons de création (contact, étiquette, liste de
 * diffusion) ainsi que deux menus déroulants (accordéons) listant les étiquettes et
 * les listes de diffusion du carnet. Le bouton de ce qui est actuellement affiché est
 * mis en évidence.
 *
 * @version 1.2
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
     * Indique si le répertoire complet est affiché (bouton "Tous les contacts" en évidence).
     */
    private boolean tousContactsActif = true;

    /**
     * Étiquette dont le répertoire est filtré (<code>null</code> si aucune).
     */
    private EtiquetteVue etiquetteActive;

    /**
     * Liste de diffusion affichée (<code>null</code> si aucune).
     */
    private ListeDiffusionVue listeActive;

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
        setPreferredSize(new Dimension(Style.LARGEUR_PANNEAU_GAUCHE, 0));
        setBackground(Style.FOND_PANNEAU_LATERAL);

        gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_PANNEAU;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        titreApp = new JLabel("AtoZ Carnet", JLabel.CENTER);

        btnNewContact = new JButton("+ Contact");
        btnNewContact.addActionListener(e -> creerContact());

        btnNewEtiquette = new JButton("+ Étiquette");
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
            controleur, FenetreDialogueAbstraite.fenetreDe(this), "Créer un nouveau contact", "", "", controleur.getEtiquettes(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            ContactVue contact = controleur.creerContact(f.getNomSaisi(), f.getEmailSaisi(), f.getEtiquettesSelectionnees());
            if (listener != null) {
                listener.clicContact(contact);
            }
        });
    }

    /**
     * Ouvre le formulaire de création d'une étiquette et la crée, avec les contacts
     * cochés, s'il est validé.
     */
    private void creerEtiquette() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
<<<<<<< HEAD
            FenetreDialogueAbstraite.fenetreDe(this), "Créer une nouvelle étiquette", "",
=======
            controleur, FenetreDialogueAbstraite.fenetreDe(this), "Créer une nouvelle étiquette", "", 
>>>>>>> modelfix
            controleur.getContacts(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            EtiquetteVue creee = controleur.creerEtiquette(f.getLibelleSaisi());
            if (creee != null) {
                appliquerContacts(creee, f.getContactsSelectionnes());
            }
        });
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
     * Ajoute un bouton par étiquette. Un clic prévient l'écouteur de navigation ; le bouton
     * de l'étiquette dont le répertoire est filtré est mis en évidence.
     */
    private void ajouterBoutonsEtiquettes() {
        for (EtiquetteVue etiquette : controleur.getEtiquettes()) {
            JButton btn = new JButton(etiquette.getNom());
            Style.marquerBoutonMenu(btn, controleur.memeEtiquette(etiquette, etiquetteActive));
            btn.addActionListener(e -> {
                if (listener != null) {
                    listener.clicEtiquette(etiquette);
                }
            });
            ajouter(btn);
        }
    }

    /**
     * Ajoute un bouton par liste de diffusion. Un clic prévient l'écouteur de navigation ;
     * le bouton de la liste affichée est mis en évidence.
     */
    private void ajouterBoutonsListes() {
        for (ListeDiffusionVue liste : controleur.getListesDiffusion()) {
            JButton btn = new JButton(liste.getNom());
            Style.marquerBoutonMenu(btn, controleur.memeListe(liste, listeActive));
            btn.addActionListener(e -> {
                if (listener != null) {
                    listener.clicListeDiffusion(liste);
                }
            });
            ajouter(btn);
        }
    }

    /**
     * Met en évidence dans le menu ce qui est actuellement affiché, puis reconstruit le panneau.
     *
     * @param tousContacts <code>true</code> si le répertoire complet est affiché
     * @param etiquette l'étiquette qui filtre le répertoire (<code>null</code> si aucune)
     * @param liste la liste de diffusion affichée (<code>null</code> si aucune)
     */
    public void marquer(boolean tousContacts, EtiquetteVue etiquette, ListeDiffusionVue liste) {
        this.tousContactsActif = tousContacts;
        this.etiquetteActive = etiquette;
        this.listeActive = liste;
        rafraichir();
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
        Style.marquerBoutonMenu(btnTousContacts, tousContactsActif);
        ajouter(btnTousContacts);

        // Étiquettes : bouton de création et accordéon
        ajouter(btnNewEtiquette);
        btnEtiquettes.setText(etiquettesVisibles ? "▼ Étiquettes :" : "▶ Étiquettes :");
        ajouter(btnEtiquettes);
        if (etiquettesVisibles) {
            ajouterBoutonsEtiquettes();
        }

        // Listes de diffusion : bouton de création et accordéon
        ajouter(btnNewListesDiffusion);
        btnListesDiffusion.setText(listesDiffusionVisibles ? "▼ Listes de Diffusion :" : "▶ Listes de Diffusion :");
        ajouter(btnListesDiffusion);
        if (listesDiffusionVisibles) {
            ajouterBoutonsListes();
        }

        // Pousse le contenu vers le haut
        gbc.weighty = 1.0;
        ajouter(Style.ressort());
        gbc.weighty = 0.0;

        revalidate();
        repaint();
    }

    /**
     * Ajoute l'étiquette aux contacts voulus qui ne l'ont pas, et la retire aux autres.
     * Seuls les contacts dont la situation change sont modifiés ; ils sont reconnus par
     * <code>equals</code>, car le contrôleur recharge ses objets après chaque modification.
     *
     * @param etiquette l'étiquette concernée
     * @param voulus les contacts qui doivent la posséder
     */
    private void appliquerContacts(EtiquetteVue etiquette, List<ContactVue> voulus) {
        List<ContactVue> tous = new ArrayList<>(controleur.getContacts());
        for (ContactVue contact : tous) {
            boolean doitLAvoir = voulus.contains(contact);
            boolean laPossede = contact.getEtiquettes().contains(etiquette);
            if (doitLAvoir != laPossede) {
                List<EtiquetteVue> nouvelles = new ArrayList<>(contact.getEtiquettes());
                if (doitLAvoir) {
                    nouvelles.add(etiquette);
                } else {
                    nouvelles.remove(etiquette);
                }
                controleur.modifierContact(contact, contact.getNom(), contact.getEmail(), nouvelles);
            }
        }
    }
}
