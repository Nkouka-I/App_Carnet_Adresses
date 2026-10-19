package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

/**
 * La classe <code>LigneContact</code> est la ligne graphique représentant un contact
 * dans le répertoire : son nom, son adresse email, un bouton "Copier" et ses étiquettes.
 * Un clic sur la ligne notifie l'écouteur de navigation.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class LigneContact extends JPanel {

    /**
     * Nom du contact affiché sur la ligne.
     */
    private final String nom;

    /**
     * Adresse email du contact affichée sur la ligne.
     */
    private final String email;

    /**
     * Étiquettes associées au contact.
     */
    private final List<String> etiquettes;

    /**
     * Construit la ligne d'un contact et la rend cliquable.
     *
     * @param nom le nom du contact
     * @param email l'adresse email du contact
     * @param etiquettes les étiquettes associées au contact
     * @param listener l'écouteur à notifier lors d'un clic sur la ligne (peut être <code>null</code>)
     */
    public LigneContact(String nom, String email, List<String> etiquettes, NavigationListener listener) {
        this.nom = nom;
        this.email = email;
        this.etiquettes = List.copyOf(etiquettes);

        setLayout(new GridBagLayout());
        setBackground(new Color(250, 250, 250));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {
                    listener.clicContact(LigneContact.this.nom, LigneContact.this.email, LigneContact.this.etiquettes);
                }
            }
        });

        construire();
    }

    /**
     * Place les composants de la ligne : nom, email et bouton "Copier" sur la première
     * rangée, étiquettes sur la seconde.
     */
    private void construire() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;

        gbc.gridx = 0; gbc.weightx = 0.3; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(nom), gbc);

        gbc.gridx = 1; gbc.weightx = 0.4; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(email), gbc);

        gbc.gridx = 3; gbc.weightx = 0.1; gbc.fill = GridBagConstraints.NONE;
        add(new JButton("Copier"), gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("[" + String.join(" ", etiquettes) + "]"), gbc);
    }

    /**
     * Renvoie le nom du contact affiché sur la ligne.
     *
     * @return le nom du contact
     */
    public String getNom() { return nom; }

    /**
     * Renvoie l'adresse email du contact affichée sur la ligne.
     *
     * @return l'email du contact
     */
    public String getEmail() { return email; }

    /**
     * Renvoie les étiquettes associées au contact.
     *
     * @return la liste (non modifiable) des étiquettes du contact
     */
    public List<String> getEtiquettes() { return etiquettes; }
}
