package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;

/**
 * La classe <code>LigneContact</code> représente un contact dans le répertoire principal.
 * En plus des informations de base, elle ajoute une ligne affichant les étiquettes.
 *
 * @version 1.2
 * @author Nkouka Ildevert
 */
public class LigneContact extends LigneContactAbstraite {

    public LigneContact(ContactVue contact, NavigationListener listener) {
        super(contact, listener);

        // Ajout spécifique des étiquettes sur la deuxième ligne
        if (!contact.getEtiquettes().isEmpty()) {
            StringBuilder etiquettes = new StringBuilder();
            for (EtiquetteVue etiquette : contact.getEtiquettes()) {
                if (etiquettes.length() > 0) {
                    etiquettes.append(' ');
                }
                etiquettes.append(etiquette.getNom());
            }

            gbc.gridx = 0; 
            gbc.gridy = 1; 
            gbc.weightx = 0; 
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(new JLabel("[" + etiquettes + "]"), gbc);
        }
    }
}