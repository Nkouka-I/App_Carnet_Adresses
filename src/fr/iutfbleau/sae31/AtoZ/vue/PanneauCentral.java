package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

public class PanneauCentral extends JPanel{

    private final NavigationListener listener;

	public PanneauCentral(NavigationListener listener) {
        this.listener = listener;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Par défaut, on charge la vue répertoire
        afficherRepertoire();
    }

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
        panelContactsList.add(creerLigneContact("Jean Dupont", "jean.dupont@mail.com", "Client" + " " + "VIP"+ " " + "Projet"), gbcContacts);
        gbcContacts.gridy = ligne++;
        panelContactsList.add(creerLigneContact("Marie Curie", "marie.curie@mail.com", "Client" + " " + "VIP"+ " " + "Projet"), gbcContacts);

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

    private JPanel creerLigneContact(String nom, String email, String etiquette) {
        JPanel ligne = new JPanel(new GridBagLayout());
        ligne.setBackground(new Color(250, 250, 250));
        ligne.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        ligne.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ligne.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.clicContact(nom, email, List.of(etiquette.split(" ")));
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;

        gbc.gridx = 0; gbc.weightx = 0.3; gbc.fill = GridBagConstraints.HORIZONTAL;
        ligne.add(new JLabel(nom), gbc);

        gbc.gridx = 1; gbc.weightx = 0.4; gbc.fill = GridBagConstraints.HORIZONTAL;
        ligne.add(new JLabel(email), gbc);

        gbc.gridx = 3; gbc.weightx = 0.1; gbc.fill = GridBagConstraints.NONE;
        ligne.add(new JButton("Copier"), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        ligne.add(new JLabel("[" + etiquette + "]"), gbc);
        return ligne;
    }

    public void afficherListeDiffusion(String nomListe) {
        removeAll(); // Efface tout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 		// 5 pixels de marge tout autour de chaque élément
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int l = 0;

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