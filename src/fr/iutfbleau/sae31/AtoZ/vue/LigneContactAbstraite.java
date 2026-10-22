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
 * @version 1.2
 * @author Nkouka Ildevert
 */
public abstract class LigneContactAbstraite extends JPanel {

    private final ContactVue contact;
    private boolean selectionnee = false;
    protected final GridBagConstraints gbc;

    public LigneContactAbstraite(ContactVue contact, NavigationListener listener) {
        this.contact = contact;

        setLayout(new GridBagLayout());
        setBackground(Style.FOND_LIGNE);
        Style.bordureLigne(this, false);

        // Rend la ligne entière cliquable
        if (listener != null) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new EcouteurClicLigne(this, listener));
        }

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;

        // Nom du contact
        gbc.gridx = 0; 
        gbc.weightx = 0.3; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(contact.getNom()), gbc);

        // Adresse email
        gbc.gridx = 1; 
        gbc.weightx = 0.4; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(contact.getEmail()), gbc);

        // Bouton Copier
        gbc.gridx = 2; 
        gbc.weightx = 0.1; 
        gbc.fill = GridBagConstraints.NONE;
        JButton btnCopier = new JButton("Copier");
        btnCopier.addActionListener(e -> PressePapiers.copier(btnCopier, contact.getEmail()));
        add(btnCopier, gbc);
    }

    public ContactVue getContact() { 
        return contact; 
    }

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