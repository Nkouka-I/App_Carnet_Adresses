package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import java.util.List;
import javax.swing.*;

/**
 * La classe <code>PanneauGauche</code> est le panneau de navigation de gauche de la
 * fenêtre principale. Il propose les boutons de création (contact, étiquette, liste de
 * diffusion) ainsi que deux menus déroulants (accordéons) listant les étiquettes et
 * les listes de diffusion.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauGauche extends JPanel {

    /**
     * Bouton de création d'un nouveau contact.
     */
    private final JButton btnNewContact;

    /**
     * Bouton de création d'une nouvelle étiquette.
     */
    private final JButton btnNewEtiquette;

    /**
     * Bouton de création d'une nouvelle liste de diffusion.
     */
    private final JButton btnNewListesDiffusion;

    /**
     * Titre de l'application affiché en haut du panneau.
     */
    private final JLabel titreApp;

    /**
     * Bouton pour afficher tout les contacts.
     */
    private final JButton btnToutContacts;

    /**
     * Bouton qui déplie ou replie la liste des étiquettes.
     */
    private final JButton btnEtiquettes;

    /**
     * Bouton qui déplie ou replie la liste des listes de diffusion.
     */
    private final JButton btnListesDiffusion;

    /**
     * Indique si la liste des étiquettes est actuellement dépliée.
     */
    private boolean etiquettesVisibles = false;

    /**
     * Indique si la liste des listes de diffusion est actuellement dépliée.
     */
    private boolean listesDiffusionVisibles = false;

    /**
     * Construit le panneau de gauche, initialise ses composants et configure
     * une seule fois les écouteurs pour éviter toute fuite ou accumulation.
     *
     * @param listener l'écouteur à notifier lors d'un clic sur une navigation
     */
    public PanneauGauche(NavigationListener listener) {

        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(220, 0)); // Limité à une largeur fixe
        setBackground(new Color(245, 245, 245));

        titreApp = new JLabel("AtoZ Carnet", JLabel.CENTER);

        btnNewContact = new JButton("+ Contact");
        btnNewContact.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            List<String> toutesLesEtiquettes = List.of("Client", "Famille", "Travail", "VIP");

            FenetreFormulaireContact form = new FenetreFormulaireContact(
                parent, "Créer un nouveau contact", "", "", toutesLesEtiquettes, List.of()
            );

            ouvrirFormulaire(form, f -> {
                String nom = f.getNomSaisi();
                String email = f.getEmailSaisi();
                List<String> etiquettes = f.getEtiquettesSelectionnees();

                System.out.println("Nouveau contact créé : " + nom + " <" + email + ">");
                if (listener != null) {
                    listener.clicContact(nom, email, etiquettes);
                }
            });
        });

        btnNewEtiquette = new JButton("+ Etiquette");
        btnNewEtiquette.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);

            FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
                parent, "Créer une nouvelle étiquette", ""
            );

            ouvrirFormulaire(form, f -> {
                String libelle = f.getLibelleSaisi();
                System.out.println("Nouvelle étiquette créée : " + libelle);
                rafraichirInterface(listener);
            });
        });

        btnNewListesDiffusion = new JButton("+ Listes de Diffusion");
        btnNewListesDiffusion.addActionListener(e -> {
            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            List<String> toutesLesEtiquettes = List.of("Client", "Famille", "Travail", "VIP");

            FenetreFormulaireListeDiffusion form = new FenetreFormulaireListeDiffusion(
                parent, "Créer une liste de diffusion", "", toutesLesEtiquettes, List.of()
            );

            ouvrirFormulaire(form, f -> {
                String nomListe = f.getNomListeSaisi();
                List<CritereData> criteres = f.getCriteresList();

                System.out.println("Nouvelle liste créée : " + nomListe);
                rafraichirInterface(listener);
            });
        });

        // Initialisation boutons tout les contactes
        btnToutContacts = new JButton();
        btnToutContacts.addActionListener(e -> {
            // on notifie le listener avec null a l'appuie
            listener.clicContact(null, null, null);
            
            rafraichirInterface(listener);
        });

        // Initialisation de l'accordéon Étiquettes
        btnEtiquettes = new JButton();
        btnEtiquettes.addActionListener(e -> {
            etiquettesVisibles = !etiquettesVisibles;
            
            // Si on vient de le DÉPLIER, on notifie le listener avec null
            if (etiquettesVisibles && listener != null) {
                listener.clicContactEtiquette(null);
            }
            
            rafraichirInterface(listener);
        });

        // Initialisation de l'accordéon Listes de diffusion
        btnListesDiffusion = new JButton();
        btnListesDiffusion.addActionListener(e -> {
            listesDiffusionVisibles = !listesDiffusionVisibles;
            
            // Si on vient de le DÉPLIER, on notifie le listener avec null
            if (listesDiffusionVisibles && listener != null) {
                listener.clicListeDiffusion(null);
            }
            
            rafraichirInterface(listener);
        });

        rafraichirInterface(listener);
    }

    /**
     * Méthode générique pour ouvrir un formulaire modal et traiter sa validation.
     *
     * @param <T> le type de formulaire
     * @param formulaire l'instance du dialogue modal à afficher
     * @param listener l'action à exécuter en cas de validation
     */
    private <T extends FenetreDialogueAbstraite> void ouvrirFormulaire(T formulaire, FormulaireListener<T> listener) {
        formulaire.setVisible(true);
        if (formulaire.isValide()) {
            listener.onValider(formulaire);
        }
    }

    /**
     * Reconstruit entièrement le contenu visuel du panneau selon l'état des accordéons.
     *
     * @param listener l'écouteur à notifier lors d'un clic sur un sous-élément
     */
    public void rafraichirInterface(NavigationListener listener) {
        removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; 
        
        int ligne = 0;

        // Titre
        gbc.gridy = ligne++;
        add(titreApp, gbc);

        // 1-Bouton "+ new Contact" en premier
        gbc.gridy = ligne++;
        add(btnNewContact, gbc);

        gbc.gridy = ligne++;
        add(btnToutContacts, gbc);
        btnToutContacts.setText("Tout les contacts");

        // 2-Bouton "+ new Etiquette" avec texte dynamique selon l'état
        gbc.gridy = ligne++;
        add(btnNewEtiquette, gbc);

        btnEtiquettes.setText(etiquettesVisibles ? "▼ Étiquettes :" : "▶ Étiquettes :");
        gbc.gridy = ligne++;
        add(btnEtiquettes, gbc);

        // Étiquettes affichées uniquement si le menu est déplié
        if (etiquettesVisibles) {
            List<String> nomsEtiquettes = List.of("Client", "Famille", "Travail", "VIP");
            for (String nom : nomsEtiquettes) {
                gbc.gridy = ligne++;
                JButton btn = new JButton(nom);
                
                String nomEtiquette = nom;
                // Clic sur une étiquette spécifique
                btn.addActionListener(e -> {
                    if (listener != null) {
                        listener.clicContactEtiquette(nomEtiquette); 
                    }
                });
                
                add(btn, gbc);
            }
        }

        // 3-Bouton Listes de diffusion avec texte dynamique selon l'état
        gbc.gridy = ligne++;
        add(btnNewListesDiffusion, gbc);

        btnListesDiffusion.setText(listesDiffusionVisibles ? "▼ Listes de Diffusion :" : "▶ Listes de Diffusion :");
        gbc.gridy = ligne++;
        add(btnListesDiffusion, gbc);

        // Listes affichées uniquement si le menu est déplié
        if (listesDiffusionVisibles) {
            List<String> nomsListes = List.of("Projet", "News");
            for (String nom : nomsListes) {
                gbc.gridy = ligne++;
                JButton btn = new JButton(nom);
                
                String nomListeActuel = nom;
                // Clic sur une liste de diffusion spécifique
                btn.addActionListener(e -> {
                    if (listener != null) {
                        listener.clicListeDiffusion(nomListeActuel);
                    }
                });
                
                add(btn, gbc);
            }
        }

        // Pousse le contenu vers le haut
        gbc.gridy = ligne++;
        gbc.weighty = 1.0;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        add(filler, gbc);

        // Rafraîchissement visuel
        revalidate();
        repaint();
    }
}