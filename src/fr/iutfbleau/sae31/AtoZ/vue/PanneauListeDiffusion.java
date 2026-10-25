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

/**
 * La classe <code>PanneauListeDiffusion</code> est la vue dédiée à une liste de
 * diffusion : son titre, la liste de ses contacts (que l'on peut sélectionner) et un
 * bouton pour copier tous les contacts. Sans liste sélectionnée, elle affiche un message
 * d'accueil.
 *
 * @version 1.2
 * @author Nkouka Ildevert
 */
public class PanneauListeDiffusion extends JPanel {

    /**
     * Contrôleur fournissant les contacts d'une liste.
     */
    private final Controleur controleur;

    /**
     * Écouteur notifié lorsque l'utilisateur clique sur un contact de la liste.
     */
    private final NavigationListener listener;

    /**
     * Lignes actuellement affichées, pour mettre à jour la sélection sans tout reconstruire.
     */
    private final List<LigneListeDiffusion> lignes = new ArrayList<>();

    /**
     * Construit un panneau vide, prêt à afficher une liste de diffusion.
     *
     * @param listener l'écouteur à notifier lors des clics sur les contacts
     * @param controleur le contrôleur fournissant les contacts
     */
    public PanneauListeDiffusion(NavigationListener listener, Controleur controleur) {
        this.listener = listener;
        this.controleur = controleur;

        setLayout(new GridBagLayout());
        setBackground(Style.FOND_CONTENU);
        Style.bordureContenu(this);
    }

    /**
     * Affiche la page d'une liste de diffusion. Le contenu précédent du panneau est effacé.
     *
     * @param liste la liste de diffusion à afficher
     *              (<code>null</code> pour afficher le message d'accueil des listes)
     */
    public void afficher(ListeDiffusionVue liste) {
        removeAll();

        if (liste == null) {
            afficherAccueil();
        } else {
            afficherDetails(liste);
        }

        StyleComposant.moderniser(this);
        revalidate();
        repaint();
    }

    /**
     * Crée les contraintes de placement communes aux deux vues.
     *
     * @return les contraintes : une colonne, remplissage horizontal, marges d'élément
     */
    private GridBagConstraints creerContraintes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_ELEMENT;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    /**
     * Affiche le message d'accueil, lorsqu'aucune liste n'est sélectionnée.
     */
    private void afficherAccueil() {
        GridBagConstraints gbc = creerContraintes();

        JLabel lblTitre = new JLabel("Gestion des Listes de Diffusion");
        lblTitre.setFont(Style.POLICE_GRAND_TITRE);
        gbc.gridy = 0;
        add(lblTitre, gbc);

        gbc.gridy = 1;
        add(new JLabel("Sélectionnez une liste de diffusion dans le menu de gauche pour afficher ses détails."), gbc);
    }

    /**
     * Affiche la page d'une liste précise : titre, contacts et bouton "Copier tout".
     *
     * @param liste la liste de diffusion à afficher
     */
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

    /**
     * Crée le conteneur listant les contacts de la liste de diffusion.
     *
     * @param liste la liste de diffusion dont on affiche les contacts
     * @return le panneau contenant une {@link LigneListeDiffusion} par contact
     */
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

        // Ressort vertical pour éviter l'étirement des lignes
        gbc.gridy = ligne;
        gbc.weighty = 1.0;
        panelContactsList.add(Style.ressort(), gbc);

        return panelContactsList;
    }

    /**
     * Surligne le contact donné à l'intérieur de la liste affichée.
     *
     * @param contact le contact à mettre en évidence (<code>null</code> pour ne rien sélectionner)
     */
    public void selectionnerContact(ContactVue contact) {
        for (LigneListeDiffusion ligne : lignes) {
            ligne.setSelectionnee(controleur.memeContact(ligne.getContact(), contact));
        }
    }

    /**
     * Crée le bouton qui copie les adresses de toute la liste.
     *
     * @param liste la liste de diffusion dont on copie les adresses
     * @return le bouton "[ Copier tout ]"
     */
    private JButton creerBoutonCopierTout(ListeDiffusionVue liste) {
        JButton btnCopierTout = new JButton("[ Copier tout ]");
        StyleBouton.styliserBoutonPrincipal(btnCopierTout);
        btnCopierTout.addActionListener(e -> PressePapiers.copier(btnCopierTout, adressesDe(liste)));
        return btnCopierTout;
    }

    /**
     * Rassemble les adresses de tous les contacts d'une liste, séparées par des virgules.
     *
     * @param liste la liste de diffusion
     * @return les adresses, par exemple « a@mail.fr, b@mail.fr » (vide si la liste n'a aucun contact)
     */
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
