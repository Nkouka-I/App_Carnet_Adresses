package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
     * Contact représenté par la ligne.
     */
    private final ContactVue contact;

    /**
     * Indique si la ligne est mise en évidence (contact sélectionné).
     */
    private boolean selectionnee = false;

    /**
     * Construit la ligne d'un contact et la rend cliquable.
     *
     * @param contact le contact à afficher
     * @param listener l'écouteur à notifier lors d'un clic sur la ligne (peut être <code>null</code>)
     */
    public LigneContact(ContactVue contact, NavigationListener listener) {
        this.contact = contact;

        setLayout(new GridBagLayout());
        setBackground(Style.FOND_LIGNE);
        Style.bordureLigne(this, false);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new EcouteurClicLigne(this, listener));

        construire();
    }

    /**
     * Place les composants de la ligne : nom, email et bouton "Copier" sur la première
     * rangée, étiquettes sur la seconde (si le contact en a).
     */
    private void construire() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;

        gbc.gridx = 0; gbc.weightx = 0.3; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(contact.getNom()), gbc);

        gbc.gridx = 1; gbc.weightx = 0.4; gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel(contact.getEmail()), gbc);

        gbc.gridx = 3; gbc.weightx = 0.1; gbc.fill = GridBagConstraints.NONE;
        JButton btnCopier = new JButton("Copier");
        btnCopier.addActionListener(e -> PressePapiers.copier(btnCopier, contact.getEmail()));
        add(btnCopier, gbc);

        if (!contact.getEtiquettes().isEmpty()) {
            StringBuilder etiquettes = new StringBuilder();
            for (EtiquetteVue etiquette : contact.getEtiquettes()) {
                if (etiquettes.length() > 0) {
                    etiquettes.append(' ');
                }
                etiquettes.append(etiquette.getNom());
            }

            gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.HORIZONTAL;
            add(new JLabel("[" + etiquettes + "]"), gbc);
        }
    }

    /**
     * Renvoie le contact représenté par la ligne.
     *
     * @return le contact de la ligne
     */
    public ContactVue getContact() { return contact; }

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
