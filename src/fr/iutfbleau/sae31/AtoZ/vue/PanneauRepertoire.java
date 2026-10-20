package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * La classe <code>PanneauRepertoire</code> est la vue du répertoire de contacts :
 * une barre de recherche, un bouton de tri et la liste des contacts, chacun
 * représenté par une {@link LigneContact}.
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
     * Construit le répertoire et affiche ses composants.
     *
     * @param listener l'écouteur à notifier lors des clics sur les contacts
     */
    public PanneauRepertoire(NavigationListener listener) {
        this.listener = listener;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);

        construire();
    }

    /**
     * Construit le contenu du panneau : barre de recherche et de tri en haut, liste
     * des contacts en dessous.
     */
    private void construire() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 5 pixels de marge tout autour de chaque élément
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Barre de recherche et Trier
        JPanel panelHaut = new JPanel(new GridBagLayout());
        panelHaut.setBackground(Color.WHITE);
        GridBagConstraints gbcHaut = new GridBagConstraints();
        gbcHaut.insets = new Insets(0, 0, 10, 5);

        gbcHaut.gridx = 0; gbcHaut.weightx = 1.0; gbcHaut.fill = GridBagConstraints.HORIZONTAL;
        panelHaut.add(new JTextField("Chercher un nom, étiquette..."), gbcHaut);

        gbcHaut.gridx = 1; gbcHaut.weightx = 0.0;/*reste a droite*/ gbcHaut.fill = GridBagConstraints.NONE;/*Ne s'etire pas*/
        panelHaut.add(new JButton("Trier"), gbcHaut);

        add(panelHaut, gbc);

        // Liste des contacts
        gbc.gridy = 1;		/*ligne du dessous par rapport au panneau haut*/
        gbc.weightx = 1.0;	/*occupe toute largeur possible*/
        gbc.weighty = 1.0;	/*occupe toute hauteur possible*/
        gbc.fill = GridBagConstraints.BOTH;

        JPanel panelContactsList = new JPanel(new GridBagLayout());
        panelContactsList.setBackground(Color.WHITE);
        GridBagConstraints gbcContacts = new GridBagConstraints();
        gbcContacts.gridx = 0;
        gbcContacts.fill = GridBagConstraints.HORIZONTAL;
        gbcContacts.weightx = 1.0;

        int ligne = 0;

        // Données fake
        gbcContacts.gridy = ligne++;
        panelContactsList.add(new LigneContact("Jean Dupont", "jean.dupont@mail.com", List.of("Client", "VIP", "Projet"), listener), gbcContacts);
        gbcContacts.gridy = ligne++;
        panelContactsList.add(new LigneContact("Marie Curie", "marie.curie@mail.com", List.of("Client", "VIP", "Projet"), listener), gbcContacts);

        // Ressort
        gbcContacts.gridy = ligne;
        gbcContacts.weighty = 1.0;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        panelContactsList.add(filler, gbcContacts);

        add(new JScrollPane(panelContactsList), gbc);
    }
}
