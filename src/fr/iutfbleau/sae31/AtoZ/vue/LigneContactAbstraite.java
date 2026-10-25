package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * La classe <code>LigneContactAbstraite</code> regroupe le comportement et l'affichage
 * commun à toutes les lignes représentant un contact dans l'application (nom, email,
 * bouton de copie, surbrillance de sélection et clic de souris).
 *
 * @version 1.3
 * @author Nkouka Ildevert
 */
public abstract class LigneContactAbstraite extends JPanel {

    /**
     * Contact représenté par la ligne.
     */
    private final ContactVue contact;

    /**
     * Indique si la ligne est mise en évidence (contact sélectionné).
     */
    private boolean selectionnee = false;

    /**
     * Contraintes de placement, réutilisables par les classes filles pour ajouter des éléments.
     */
    protected final GridBagConstraints gbc;

    /**
     * Construit la ligne d'un contact : nom, adresse mail et bouton "Copier". La ligne
     * entière est cliquable si un écouteur est fourni.
     *
     * @param contact le contact à afficher
     * @param listener l'écouteur à notifier lors d'un clic sur la ligne (peut être <code>null</code>)
     * @param dansListe <code>true</code> si la ligne est affichée dans une liste de diffusion
     */
    public LigneContactAbstraite(ContactVue contact, NavigationListener listener, boolean dansListe) {
        this.contact = contact;

        setLayout(new GridBagLayout());
        setBackground(Style.FOND_LIGNE);
        Style.bordureLigne(this, false);

        // Rend la ligne entière cliquable
        if (listener != null) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new EcouteurClicLigne(this, listener, dansListe));
        }

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;

        // Nom du contact
        gbc.gridx = 0;
        gbc.weightx = 0.3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblNom = new JLabel(contact.getNom());
        lblNom.setFont(Style.POLICE_GRAS);
        add(lblNom, gbc);

        // Adresse email
        gbc.gridx = 1;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lblEmail = new JLabel(contact.getEmail());
        lblEmail.setForeground(Style.TEXTE_DISCRET);
        add(lblEmail, gbc);

        // Bouton Copier
        gbc.gridx = 2;
        gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        JButton btnCopier = new JButton("Copier");
        StyleBouton.styliserBoutonCompact(btnCopier);
        btnCopier.addActionListener(e -> PressePapiers.copier(btnCopier, contact.getEmail()));
        add(btnCopier, gbc);
    }

    /**
     * Renvoie le contact représenté par la ligne.
     *
     * @return le contact de la ligne
     */
    public ContactVue getContact() {
        return contact;
    }

    /**
     * Met la ligne en évidence (fond coloré et barre à gauche) ou la remet à l'état normal.
     *
     * @param selectionnee <code>true</code> si le contact de la ligne est sélectionné
     */
    public void setSelectionnee(boolean selectionnee) {
        if (this.selectionnee == selectionnee) {
            return;
        }
        this.selectionnee = selectionnee;
        setBackground(selectionnee ? Style.FOND_SELECTION : Style.FOND_LIGNE);
        Style.bordureLigne(this, selectionnee);
        repaint();
    }
}
