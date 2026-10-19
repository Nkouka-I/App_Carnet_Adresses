package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * La classe <code>PanneauCentral</code> est le panneau central de la fenêtre principale.
 * Il affiche soit le répertoire de contacts (avec barre de recherche), soit la page
 * dédiée à une liste de diffusion (tableau de ses contacts).
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauCentral extends JPanel{

    /**
     * Écouteur notifié lorsque l'utilisateur clique sur un contact.
     */
    private final NavigationListener listener;

    /**
     * Construit le panneau central et affiche par défaut la vue répertoire.
     *
     * @param listener l'écouteur à notifier lors des clics sur les contacts
     */
	public PanneauCentral(NavigationListener listener) {
        this.listener = listener;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Par défaut, on charge la vue répertoire
        afficherRepertoire();
    }

    /**
     * Affiche la vue répertoire : une barre de recherche, un bouton de tri et la liste
     * des contacts. Le contenu précédent du panneau est effacé.
     */
	public void afficherRepertoire() {
        removeAll();

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

        // Rafraîchit l'affichage de la fenêtre
        revalidate();
        repaint();
    }

    /**
     * Affiche la page dédiée à une liste de diffusion : son titre, un tableau de ses
     * contacts et un bouton pour copier tous les contacts. Le contenu précédent du
     * panneau est effacé.
     *
     * @param nomListe le nom de la liste de diffusion à afficher
     *                 (<code>null</code> pour afficher le message d'accueil des listes)
     */
    public void afficherListeDiffusion(String nomListe) {
        removeAll(); // Efface tout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 		// 5 pixels de marge tout autour de chaque élément
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int l = 0;

        // Si on clique sur le bouton global de l'accordéon (nomListe == null)
        if (nomListe == null) {
            gbc.gridy = l++;
            JLabel lblTitre = new JLabel("Gestion des Listes de Diffusion");
            lblTitre.setFont(new Font("Arial", Font.BOLD, 16));
            add(lblTitre, gbc);

            gbc.gridy = l++;
            JLabel lblInfo = new JLabel("Sélectionnez une liste de diffusion dans le menu de gauche pour afficher ses détails.");
            add(lblInfo, gbc);

            revalidate();
            repaint();
            return;
        }

        // Titre de la liste
        gbc.gridy = l++;
        JLabel lblTitre = new JLabel("Liste : " + nomListe);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitre, gbc);

        // Tableau des contacts de la liste
        gbc.gridy = l++;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        String[] colonnes = {"Nom", "Prénom", "Adresse mail"};
        Object[][] donnees = {
            {"Dupont", "Jean", "jean.dupont@mail.com"},
            {"Curie", "Marie", "marie.curie@mail.com"}
        };
        JTable table = new JTable(donnees, colonnes);
        add(new JScrollPane(table), gbc);

        // 3. Bouton "[ Copier tout ]" en bas à droite[cite: 1]
        gbc.gridy = l++;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;

        JButton btnCopierTout = new JButton("[ Copier tout ]");
        btnCopierTout.setBackground(new Color(70, 130, 180));
        btnCopierTout.setForeground(Color.WHITE);
        add(btnCopierTout, gbc);

        // Rafraîchit l'affichage
        revalidate();
        repaint();
    }
}
