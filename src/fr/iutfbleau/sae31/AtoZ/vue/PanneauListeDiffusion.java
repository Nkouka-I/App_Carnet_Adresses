package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanneauListeDiffusion extends JPanel {

    private final Controleur controleur;
    private final NavigationListener listener;
    private final List<LigneListeDiffusion> lignes = new ArrayList<>(); // Mémorise les lignes affichées

    public PanneauListeDiffusion(NavigationListener listener, Controleur controleur) {
        this.listener = listener;
        this.controleur = controleur;

        setLayout(new GridBagLayout());
        setBackground(Style.FOND_CONTENU);
        Style.bordureContenu(this);
    }

    public void afficher(ListeDiffusionVue liste) {
        removeAll(); 

        if (liste == null) {
            afficherAccueil();
        } else {
            afficherDetails(liste);
        }

        revalidate();
        repaint();
    }

    private GridBagConstraints creerContraintes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_ELEMENT;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private void afficherAccueil() {
        GridBagConstraints gbc = creerContraintes();

        JLabel lblTitre = new JLabel("Gestion des Listes de Diffusion");
        lblTitre.setFont(Style.POLICE_GRAND_TITRE);
        gbc.gridy = 0;
        add(lblTitre, gbc);

        gbc.gridy = 1;
        add(new JLabel("Sélectionnez une liste de diffusion dans le menu de gauche pour afficher ses détails."), gbc);
    }

    private void afficherDetails(ListeDiffusionVue liste) {
        GridBagConstraints gbc = creerContraintes();

        JLabel lblTitre = new JLabel("Liste : " + liste.getNom());
        lblTitre.setFont(Style.POLICE_TITRE);
        gbc.gridy = 0;
        add(lblTitre, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(creerListeContacts(liste)), gbc);

        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(creerBoutonCopierTout(liste), gbc);
    }

    private JPanel creerListeContacts(ListeDiffusionVue liste) {
        JPanel panelContactsList = new JPanel(new GridBagLayout());
        panelContactsList.setBackground(Style.FOND_CONTENU);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int ligne = 0;
        lignes.clear(); // On vide la mémoire à chaque réaffichage

        for (ContactVue contact : controleur.getContactsDe(liste)) {
            gbc.gridy = ligne++;
            LigneListeDiffusion lc = new LigneListeDiffusion(contact, listener);
            lignes.add(lc);
            panelContactsList.add(lc, gbc);
        }

        gbc.gridy = ligne;
        gbc.weighty = 1.0;
        panelContactsList.add(Style.ressort(), gbc);

        return panelContactsList;
    }

    /**
     * Surligne le contact spécifié directement à l'intérieur de la liste.
     *
     * @param contact le contact à mettre en évidence
     */
    public void selectionnerContact(ContactVue contact) {
        for (LigneListeDiffusion ligne : lignes) {
            ligne.setSelectionnee(controleur.memeContact(ligne.getContact(), contact));
        }
    }

    private JButton creerBoutonCopierTout(ListeDiffusionVue liste) {
        JButton btnCopierTout = new JButton("[ Copier tout ]");
        Style.styliserBoutonPrincipal(btnCopierTout);
        btnCopierTout.addActionListener(e -> PressePapiers.copier(btnCopierTout, adressesDe(liste)));
        return btnCopierTout;
    }

    private String adressesDe(ListeDiffusionVue liste) {
        StringBuilder adresses = new StringBuilder();
        for (ContactVue contact : controleur.getContactsDe(liste)) {
            if (adresses.length() > 0) {
                adresses.append(", ");
            }
            adresses.append(contact.getEmail());
        }
        return adresses.toString();
    }
}