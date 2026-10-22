package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Fenetre extends JFrame implements NavigationListener {

    private final Controleur controleur;
    private final PanneauCentral panneauCentral;
    private final PanneauGauche panneauGauche;
    private final PanneauDroit panneauDroit;

    private EtiquetteVue filtreEtiquette;
    private ContactVue contactSelectionne;
    private EtiquetteVue etiquetteSelectionnee;
    private ListeDiffusionVue listeSelectionnee;
    private boolean modeListes = false;

	public Fenetre(Controleur controleur){
        this.controleur = controleur;

	    setTitle("AtoZ Carnet");
        setSize(1150, 700);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new EcouteurFermeture(this));

        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        panneauGauche = new PanneauGauche(this, controleur);
        add(panneauGauche, BorderLayout.WEST);

        panneauCentral = new PanneauCentral(this, controleur);
        add(panneauCentral, BorderLayout.CENTER);

        panneauDroit = new PanneauDroit(controleur);
        add(panneauDroit, BorderLayout.EAST);

        controleur.ajouterEcouteur(this::actualiser);
	}

    @Override
    public void clicTousLesContacts() {
        afficherTousLesContacts();
    }

    @Override
    public void clicMenuEtiquettes() {
        afficherTousLesContacts();
    }

    @Override
    public void clicMenuListesDiffusion() {
        deselectionner();
        modeListes = true;

        panneauCentral.afficherListeDiffusion(null);
        panneauDroit.afficherVide();
        majSelection();
    }

    @Override
    public void clicContact(ContactVue contact) {
        deselectionner();
        contactSelectionne = contact;
        modeListes = false;

        panneauCentral.afficherRepertoire();
        panneauDroit.afficherOperationsContact(contact);
        majSelection();
    }

    // NOUVELLE MÉTHODE : On sélectionne le contact mais on ne change pas le panneau central
    @Override
    public void clicContactDansListe(ContactVue contact) {
        contactSelectionne = contact;
        // On laisse intentionnellement modeListes à true et listeSelectionnee intacte
        panneauDroit.afficherOperationsContact(contact);
        majSelection();
    }

    @Override
    public void clicEtiquette(EtiquetteVue etiquette) {
        deselectionner();
        etiquetteSelectionnee = etiquette;
        filtreEtiquette = etiquette;
        modeListes = false;

        panneauCentral.afficherRepertoire(etiquette);
        panneauDroit.afficherOperationsEtiquette(etiquette);
        majSelection();
    }

    @Override
    public void clicListeDiffusion(ListeDiffusionVue liste) {
        deselectionner();
        listeSelectionnee = liste;
        modeListes = true;

        panneauCentral.afficherListeDiffusion(liste);
        panneauDroit.afficherOperationsListeDiffusion(liste);
        majSelection();
    }

    private void afficherTousLesContacts() {
        deselectionner();
        filtreEtiquette = null;
        modeListes = false;

        panneauCentral.afficherRepertoire(null);
        panneauDroit.afficherVide();
        majSelection();
    }

    private void majSelection() {
        panneauCentral.selectionnerContact(contactSelectionne);
        panneauGauche.marquer(
            !modeListes && filtreEtiquette == null,
            modeListes ? null : filtreEtiquette,
            listeSelectionnee
        );
    }

    private void deselectionner() {
        contactSelectionne = null;
        etiquetteSelectionnee = null;
        listeSelectionnee = null;
    }

    private void actualiser() {
        if (filtreEtiquette != null && !controleur.getEtiquettes().contains(filtreEtiquette)) {
            filtreEtiquette = null;
        }
        if (etiquetteSelectionnee != null && !controleur.getEtiquettes().contains(etiquetteSelectionnee)) {
            etiquetteSelectionnee = null;
        }
        if (contactSelectionne != null && !controleur.getContacts().contains(contactSelectionne)) {
            contactSelectionne = null;
        }
        if (listeSelectionnee != null && !controleur.getListesDiffusion().contains(listeSelectionnee)) {
            listeSelectionnee = null;
        }

        panneauCentral.actualiser(filtreEtiquette, listeSelectionnee);
        majSelection();

        if (contactSelectionne != null) {
            panneauDroit.afficherOperationsContact(contactSelectionne);
        } else if (etiquetteSelectionnee != null) {
            panneauDroit.afficherOperationsEtiquette(etiquetteSelectionnee);
        } else if (listeSelectionnee != null) {
            panneauDroit.afficherOperationsListeDiffusion(listeSelectionnee);
        } else {
            panneauDroit.afficherVide();
        }
    }

    protected void confirmerFermeture() {
        int reponse = JOptionPane.showConfirmDialog(
            this,
            "Voulez-vous vraiment quitter l'application ?",
            "Confirmation de fermeture",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (reponse == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }
}