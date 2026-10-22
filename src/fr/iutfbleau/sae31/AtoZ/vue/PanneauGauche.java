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

public class PanneauGauche extends JPanel {

    private final JButton btnNewContact;
    private final JButton btnNewEtiquette;
    private final JButton btnNewListesDiffusion;
    private final JLabel titreApp;
    private final JButton btnTousContacts;
    private final JButton btnEtiquettes;
    private final JButton btnListesDiffusion;

    private boolean etiquettesVisibles = false;
    private boolean listesDiffusionVisibles = false;
    private boolean tousContactsActif = true;

    private EtiquetteVue etiquetteActive;
    private ListeDiffusionVue listeActive;

    private final NavigationListener listener;
    private final Controleur controleur;
    private final GridBagConstraints gbc;
    private int ligne = 0;

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

    private void creerEtiquette() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer une nouvelle étiquette", "", 
            controleur.getContacts(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            EtiquetteVue nouvelleEtiq = controleur.creerEtiquette(f.getLibelleSaisi());
            
            for (ContactVue c : f.getContactsSelectionnes()) {
                List<EtiquetteVue> etiqs = new ArrayList<>(c.getEtiquettes());
                etiqs.add(nouvelleEtiq);
                controleur.modifierContact(c, c.getNom(), c.getEmail(), etiqs);
            }
        });
    }

    private void creerListeDiffusion() {
        FenetreFormulaireListeDiffusion form = new FenetreFormulaireListeDiffusion(
            FenetreDialogueAbstraite.fenetreDe(this), "Créer une liste de diffusion", "", controleur.getEtiquettes(), List.of()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> controleur.creerListeDiffusion(f.getNomListeSaisi(), f.getCriteresList()));
    }

    private void basculerEtiquettes() {
        etiquettesVisibles = !etiquettesVisibles;
        if (etiquettesVisibles && listener != null) {
            listener.clicMenuEtiquettes();
        }
        rafraichir();
    }

    private void basculerListesDiffusion() {
        listesDiffusionVisibles = !listesDiffusionVisibles;
        if (listesDiffusionVisibles && listener != null) {
            listener.clicMenuListesDiffusion();
        }
        rafraichir();
    }

    private void ajouter(Component comp) {
        gbc.gridy = ligne++;
        add(comp, gbc);
    }

    private void ajouterBoutonsEtiquettes() {
        for (EtiquetteVue etiquette : controleur.getEtiquettes()) {
            JButton btn = new JButton(etiquette.getNom());
            Style.marquerBoutonMenu(btn, etiquette.equals(etiquetteActive));
            btn.addActionListener(e -> {
                if (listener != null) {
                    listener.clicEtiquette(etiquette);
                }
            });
            ajouter(btn);
        }
    }

    private void ajouterBoutonsListes() {
        for (ListeDiffusionVue liste : controleur.getListesDiffusion()) {
            JButton btn = new JButton(liste.getNom());
            Style.marquerBoutonMenu(btn, liste.equals(listeActive));
            btn.addActionListener(e -> {
                if (listener != null) {
                    listener.clicListeDiffusion(liste);
                }
            });
            ajouter(btn);
        }
    }

    public void marquer(boolean tousContacts, EtiquetteVue etiquette, ListeDiffusionVue liste) {
        this.tousContactsActif = tousContacts;
        this.etiquetteActive = etiquette;
        this.listeActive = liste;
        rafraichir();
    }

    public void rafraichir() {
        removeAll();
        ligne = 0;

        ajouter(titreApp);
        ajouter(btnNewContact);
        Style.marquerBoutonMenu(btnTousContacts, tousContactsActif);
        ajouter(btnTousContacts);

        ajouter(btnNewEtiquette);
        btnEtiquettes.setText(etiquettesVisibles ? "▼ Étiquettes :" : "▶ Étiquettes :");
        ajouter(btnEtiquettes);
        if (etiquettesVisibles) {
            ajouterBoutonsEtiquettes();
        }

        ajouter(btnNewListesDiffusion);
        btnListesDiffusion.setText(listesDiffusionVisibles ? "▼ Listes de Diffusion :" : "▶ Listes de Diffusion :");
        ajouter(btnListesDiffusion);
        if (listesDiffusionVisibles) {
            ajouterBoutonsListes();
        }

        gbc.weighty = 1.0;
        ajouter(Style.ressort());
        gbc.weighty = 0.0;

        revalidate();
        repaint();
    }
}