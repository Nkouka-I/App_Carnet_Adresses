package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
        setBackground(Style.FOND_CONTENU);

        construire();
    }

    /**
     * Construit le contenu du panneau : barre de recherche et de tri en haut, liste
     * des contacts en dessous.
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

        // Liste des contacts
        gbc.gridy = 1;		/*ligne du dessous par rapport au panneau haut*/
        gbc.weightx = 1.0;	/*occupe toute largeur possible*/
        gbc.weighty = 1.0;	/*occupe toute hauteur possible*/
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(creerListeContacts()), gbc);
    }

    /**
     * Crée le conteneur listant les contacts du répertoire.
     *
     * @return le panneau contenant une {@link LigneContact} par contact
     */
    private JPanel creerListeContacts() {
        JPanel panelContactsList = new JPanel(new GridBagLayout());
        panelContactsList.setBackground(Style.FOND_CONTENU);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int ligne = 0;

        for (DonneesTest.ContactTest contact : DonneesTest.contacts()) {
            gbc.gridy = ligne++;
            panelContactsList.add(new LigneContact(contact.getNom(), contact.getEmail(), contact.getEtiquettes(), listener), gbc);
        }

        // Ressort
        gbc.gridy = ligne;
        gbc.weighty = 1.0;
        panelContactsList.add(Style.ressort(), gbc);

        return panelContactsList;
    }
}
