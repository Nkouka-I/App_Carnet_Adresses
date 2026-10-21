package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import java.awt.*;
import javax.swing.*;

/**
 * La classe <code>PanneauRepertoire</code> est la vue du répertoire de contacts :
 * une barre de recherche, un bouton de tri et la liste des contacts, chacun
 * représenté par une {@link LigneContact}. Seule la liste est reconstruite quand les
 * contacts changent : la barre de recherche garde ce que l'utilisateur a saisi.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauRepertoire extends JPanel {

    /**
     * Écouteur notifié lorsque l'utilisateur clique sur un contact.
     */
    private final NavigationListener listener;

    /**
     * Contrôleur fournissant les contacts.
     */
    private final Controleur controleur;

    /**
     * Message rappelant le filtre actif (masqué quand il n'y en a pas).
     */
    private final JLabel lblFiltre = new JLabel();

    /**
     * Conteneur des lignes de contacts, reconstruit à chaque affichage.
     */
    private final JPanel panelContacts = new JPanel(new GridBagLayout());

    /**
     * Construit le répertoire et affiche tous les contacts.
     *
     * @param listener l'écouteur à notifier lors des clics sur les contacts
     * @param controleur le contrôleur fournissant les contacts
     */
    public PanneauRepertoire(NavigationListener listener, Controleur controleur) {
        this.listener = listener;
        this.controleur = controleur;

        setLayout(new GridBagLayout());
        setBackground(Style.FOND_CONTENU);

        construire();
        afficherContacts(null);
    }

    /**
     * Construit la structure du panneau : barre de recherche et de tri en haut, rappel du
     * filtre, puis conteneur de la liste des contacts.
     */
    private void construire() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_ELEMENT;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Barre de recherche et Trier
        JPanel panelHaut = new JPanel(new GridBagLayout());
        panelHaut.setBackground(Style.FOND_CONTENU);
        GridBagConstraints gbcHaut = new GridBagConstraints();
        gbcHaut.insets = new Insets(0, 0, 10, 5);

        gbcHaut.gridx = 0; gbcHaut.weightx = 1.0; gbcHaut.fill = GridBagConstraints.HORIZONTAL;
        panelHaut.add(new JTextField("Chercher un nom, étiquette..."), gbcHaut);

        gbcHaut.gridx = 1; gbcHaut.weightx = 0.0;/*reste a droite*/ gbcHaut.fill = GridBagConstraints.NONE;/*Ne s'etire pas*/
        panelHaut.add(new JButton("Trier"), gbcHaut);

        add(panelHaut, gbc);

        // Rappel du filtre actif
        lblFiltre.setFont(Style.POLICE_TITRE);
        lblFiltre.setVisible(false);
        gbc.gridy = 1;
        add(lblFiltre, gbc);

        // Liste des contacts
        panelContacts.setBackground(Style.FOND_CONTENU);
        gbc.gridy = 2;		/*ligne du dessous*/
        gbc.weightx = 1.0;	/*occupe toute largeur possible*/
        gbc.weighty = 1.0;	/*occupe toute hauteur possible*/
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(panelContacts), gbc);
    }

    /**
     * Reconstruit la liste des contacts à partir des données actuelles.
     *
     * @param filtre l'étiquette que les contacts doivent avoir (<code>null</code> pour tous les contacts)
     */
    public void afficherContacts(EtiquetteVue filtre) {
        lblFiltre.setText(filtre == null ? "" : "Contacts avec l'étiquette « " + filtre.getNom() + " »");
        lblFiltre.setVisible(filtre != null);

        panelContacts.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int ligne = 0;

        for (ContactVue contact : controleur.getContacts(filtre)) {
            gbc.gridy = ligne++;
            panelContacts.add(new LigneContact(contact, listener), gbc);
        }

        // Ressort
        gbc.gridy = ligne;
        gbc.weighty = 1.0;
        panelContacts.add(Style.ressort(), gbc);

        panelContacts.revalidate();
        panelContacts.repaint();
        revalidate();
        repaint();
    }
}
