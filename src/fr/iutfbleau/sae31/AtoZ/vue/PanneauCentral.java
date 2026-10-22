package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

import java.awt.CardLayout;
import javax.swing.JPanel;

public class PanneauCentral extends JPanel {

    private static final String CARTE_REPERTOIRE = "repertoire";
    private static final String CARTE_LISTE_DIFFUSION = "listeDiffusion";

    private final CardLayout cartes = new CardLayout();
    private final PanneauRepertoire panneauRepertoire;
    private final PanneauListeDiffusion panneauListeDiffusion;

	public PanneauCentral(NavigationListener listener, Controleur controleur) {
        setLayout(cartes);
        setBackground(Style.FOND_CONTENU);
        Style.bordureContenu(this);

        panneauRepertoire = new PanneauRepertoire(listener, controleur);
        panneauListeDiffusion = new PanneauListeDiffusion(listener, controleur);

        add(panneauRepertoire, CARTE_REPERTOIRE);
        add(panneauListeDiffusion, CARTE_LISTE_DIFFUSION);

        afficherRepertoire();
    }

	public void afficherRepertoire() {
        cartes.show(this, CARTE_REPERTOIRE);
    }

    public void afficherRepertoire(EtiquetteVue filtre) {
        panneauRepertoire.afficherContacts(filtre);
        cartes.show(this, CARTE_REPERTOIRE);
    }

    public void afficherListeDiffusion(ListeDiffusionVue liste) {
        panneauListeDiffusion.afficher(liste);
        cartes.show(this, CARTE_LISTE_DIFFUSION);
    }

    public void selectionnerContact(ContactVue contact) {
        panneauRepertoire.selectionnerContact(contact);
        panneauListeDiffusion.selectionnerContact(contact); // Relaye la sélection à la liste
    }

    public void actualiser(EtiquetteVue filtre, ListeDiffusionVue liste) {
        panneauRepertoire.actualiser(filtre);
        panneauListeDiffusion.afficher(liste);
    }
}