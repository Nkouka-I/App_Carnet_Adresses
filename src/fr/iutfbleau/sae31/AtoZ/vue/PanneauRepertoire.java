package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * La classe <code>PanneauRepertoire</code> est la vue du répertoire de contacts :
 * une barre de recherche, un choix de tri et la liste des contacts, chacun
 * représenté par une {@link LigneContact}. La recherche filtre pendant la frappe (nom,
 * adresse et étiquettes, sans tenir compte de la casse ni des accents ; tous les mots
 * saisis doivent être trouvés). Seule la liste est reconstruite quand les contacts
 * changent : la barre de recherche garde ce que l'utilisateur a saisi.
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
     * Champ de recherche.
     */
    private final ChampRecherche txtRecherche = new ChampRecherche("Chercher un nom, une adresse, une étiquette...");

    /**
     * Bouton qui efface la recherche.
     */
    private final JButton btnEffacer = new JButton("✕");

    /**
     * Liste déroulante du choix de tri.
     */
    private final JComboBox<OrdreTri> comboTri = new JComboBox<>(OrdreTri.values());

    /**
     * Message rappelant le filtre actif (masqué quand il n'y en a pas).
     */
    private final JLabel lblFiltre = new JLabel();

    /**
     * Message donnant le nombre de contacts affichés.
     */
    private final JLabel lblNombre = new JLabel();

    /**
     * Conteneur des lignes de contacts, reconstruit à chaque affichage.
     */
    private final JPanel panelContacts = new JPanel(new GridBagLayout());

    /**
     * Zone de défilement contenant la liste des contacts.
     */
    private final JScrollPane defilement = new JScrollPane(panelContacts);

    /**
     * Lignes actuellement affichées, pour mettre à jour la sélection sans tout reconstruire.
     */
    private final List<LigneContact> lignes = new ArrayList<>();

    /**
     * Étiquette qui filtre le répertoire (<code>null</code> : tous les contacts).
     */
    private EtiquetteVue filtre;

    /**
     * Contact mis en évidence (<code>null</code> si aucun).
     */
    private ContactVue contactSelectionne;

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
     * filtre, nombre de contacts, puis conteneur de la liste des contacts.
     */
    private void construire() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = Style.MARGE_ELEMENT;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Barre de recherche et de tri
        JPanel panelHaut = new JPanel(new GridBagLayout());
        panelHaut.setBackground(Style.FOND_CONTENU);
        GridBagConstraints gbcHaut = new GridBagConstraints();
        gbcHaut.insets = new Insets(0, 0, 10, 5);
        gbcHaut.fill = GridBagConstraints.HORIZONTAL;

        gbcHaut.gridx = 0; gbcHaut.weightx = 1.0;
        panelHaut.add(txtRecherche, gbcHaut);

        btnEffacer.setToolTipText("Effacer la recherche");
        btnEffacer.setMargin(new Insets(2, 6, 2, 6));
        btnEffacer.setEnabled(false);
        btnEffacer.addActionListener(e -> {
            txtRecherche.setText("");
            txtRecherche.requestFocusInWindow();
            reconstruire(true);
        });
        gbcHaut.gridx = 1; gbcHaut.weightx = 0.0; gbcHaut.fill = GridBagConstraints.NONE;
        panelHaut.add(btnEffacer, gbcHaut);

        gbcHaut.gridx = 2;
        gbcHaut.insets = new Insets(0, 15, 10, 5);
        panelHaut.add(new JLabel("Trier :"), gbcHaut);

        comboTri.setToolTipText("Choisissez l'ordre d'affichage des contacts");
        comboTri.addActionListener(e -> reconstruire(true));
        gbcHaut.gridx = 3;
        gbcHaut.insets = new Insets(0, 0, 10, 0);
        panelHaut.add(comboTri, gbcHaut);

        // Le champ prévient ses écouteurs à chaque frappe qui change le texte
        txtRecherche.addActionListener(e -> reconstruire(true));

        add(panelHaut, gbc);

        // Rappel du filtre actif
        lblFiltre.setFont(Style.POLICE_TITRE);
        lblFiltre.setVisible(false);
        gbc.gridy = 1;
        add(lblFiltre, gbc);

        // Nombre de contacts
        lblNombre.setForeground(Style.TEXTE_DISCRET);
        gbc.gridy = 2;
        add(lblNombre, gbc);

        // Liste des contacts
        panelContacts.setBackground(Style.FOND_CONTENU);
        gbc.gridy = 3;		/*ligne du dessous*/
        gbc.weightx = 1.0;	/*occupe toute largeur possible*/
        gbc.weighty = 1.0;	/*occupe toute hauteur possible*/
        gbc.fill = GridBagConstraints.BOTH;
        add(defilement, gbc);
    }

    /**
     * Affiche le répertoire pour une navigation de l'utilisateur : la recherche est
     * effacée et la liste remonte en haut.
     *
     * @param filtre l'étiquette que les contacts doivent avoir (<code>null</code> pour tous les contacts)
     */
    public void afficherContacts(EtiquetteVue filtre) {
        this.filtre = filtre;

        txtRecherche.setText("");
        reconstruire(true);
    }

    /**
     * Recharge le répertoire après une modification du carnet, en gardant la recherche, le
     * tri et la position dans la liste.
     *
     * @param filtre l'étiquette que les contacts doivent avoir (<code>null</code> pour tous les contacts)
     */
    public void actualiser(EtiquetteVue filtre) {
        this.filtre = filtre;
        reconstruire(false);
    }

    /**
     * Met un contact en évidence dans la liste. S'il est caché par la recherche, celle-ci
     * est effacée pour qu'il redevienne visible.
     *
     * @param contact le contact à mettre en évidence (<code>null</code> pour ne rien sélectionner)
     */
    public void selectionnerContact(ContactVue contact) {
        this.contactSelectionne = contact;

        if (contact != null && !txtRecherche.getText().isEmpty() && trouverLigne(contact) == null) {
            txtRecherche.setText("");
            reconstruire(false);
        }

        for (LigneContact ligne : lignes) {
            ligne.setSelectionnee(ligne.getContact().equals(contact));
        }

        LigneContact visee = trouverLigne(contact);
        if (visee != null) {
            // Après la mise en page, fait défiler la liste jusqu'à la ligne
            SwingUtilities.invokeLater(() -> visee.scrollRectToVisible(new Rectangle(visee.getSize())));
        }
    }

    /**
     * Cherche la ligne affichée d'un contact.
     *
     * @param contact le contact recherché
     * @return la ligne, ou <code>null</code> si ce contact n'est pas affiché
     */
    private LigneContact trouverLigne(ContactVue contact) {
        if (contact == null) {
            return null;
        }
        for (LigneContact ligne : lignes) {
            if (ligne.getContact().equals(contact)) {
                return ligne;
            }
        }
        return null;
    }

    /**
     * Reconstruit la liste des contacts à partir des données actuelles, du filtre, de la
     * recherche et du tri.
     *
     * @param remonter <code>true</code> pour remettre la liste en haut
     */
    private void reconstruire(boolean remonter) {
        List<ContactVue> tous = controleur.getContacts(filtre);
        String recherche = txtRecherche.getText().trim();
        btnEffacer.setEnabled(!txtRecherche.getText().isEmpty());

        List<ContactVue> affiches = filtrer(tous, recherche);
        affiches.sort(((OrdreTri) comboTri.getSelectedItem()).getComparateur());

        lblFiltre.setText(filtre == null ? "" : "Contacts avec l'étiquette « " + filtre.getNom() + " »");
        lblFiltre.setVisible(filtre != null);
        lblNombre.setText(recherche.isEmpty()
            ? nombre(tous.size(), "contact")
            : nombre(affiches.size(), "résultat") + " sur " + tous.size());

        panelContacts.removeAll();
        lignes.clear();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int ligne = 0;

        if (affiches.isEmpty()) {
            JLabel vide = new JLabel(messageVide(recherche));
            vide.setForeground(Style.TEXTE_DISCRET);
            gbc.gridy = ligne++;
            gbc.insets = new Insets(20, 10, 0, 10);
            panelContacts.add(vide, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
        }

        for (ContactVue contact : affiches) {
            LigneContact lc = new LigneContact(contact, listener);
            lc.setSelectionnee(contact.equals(contactSelectionne));
            lignes.add(lc);
            gbc.gridy = ligne++;
            panelContacts.add(lc, gbc);
        }

        // Ressort
        gbc.gridy = ligne;
        gbc.weighty = 1.0;
        panelContacts.add(Style.ressort(), gbc);

        panelContacts.revalidate();
        panelContacts.repaint();
        revalidate();
        repaint();

        if (remonter) {
            SwingUtilities.invokeLater(() -> defilement.getVerticalScrollBar().setValue(0));
        }
    }

    /**
     * Garde les contacts qui correspondent à la recherche : chaque mot saisi doit se
     * trouver dans le nom, l'adresse ou une étiquette du contact, sans tenir compte de la
     * casse ni des accents.
     *
     * @param contacts les contacts à filtrer
     * @param recherche le texte saisi (vide pour tout garder)
     * @return une nouvelle liste modifiable des contacts retenus
     */
    private List<ContactVue> filtrer(List<ContactVue> contacts, String recherche) {
        List<ContactVue> retenus = new ArrayList<>();
        String[] mots = Texte.sansAccents(recherche).split("\\s+");

        for (ContactVue contact : contacts) {
            if (recherche.isEmpty()) {
                retenus.add(contact);
                continue;
            }

            StringBuilder texte = new StringBuilder(contact.getNom()).append(' ').append(contact.getEmail());
            for (EtiquetteVue etiquette : contact.getEtiquettes()) {
                texte.append(' ').append(etiquette.getNom());
            }
            String cible = Texte.sansAccents(texte.toString());

            boolean tousTrouves = true;
            for (String mot : mots) {
                if (!cible.contains(mot)) {
                    tousTrouves = false;
                    break;
                }
            }
            if (tousTrouves) {
                retenus.add(contact);
            }
        }
        return retenus;
    }

    /**
     * Écrit un nombre suivi de son nom, accordé au pluriel si besoin.
     *
     * @param n le nombre
     * @param nom le nom au singulier
     * @return par exemple « 1 contact » ou « 12 contacts »
     */
    private static String nombre(int n, String nom) {
        return n + " " + nom + (n > 1 ? "s" : "");
    }

    /**
     * Choisit le message affiché quand aucun contact n'est à montrer.
     *
     * @param recherche le texte de recherche en cours
     * @return le message adapté à la situation
     */
    private String messageVide(String recherche) {
        if (!recherche.isEmpty()) {
            return "Aucun résultat pour « " + recherche + " ».";
        }
        if (filtre != null) {
            return "Aucun contact n'a cette étiquette.";
        }
        return "Aucun contact pour le moment. Cliquez sur « + Contact » pour en ajouter un.";
    }
}
