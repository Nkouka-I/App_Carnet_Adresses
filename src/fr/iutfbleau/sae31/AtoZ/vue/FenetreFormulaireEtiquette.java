package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * La classe <code>FenetreFormulaireEtiquette</code> est le formulaire de création et de
 * modification d'une étiquette. Il comporte le libellé de l'étiquette et permet
 * d'y associer directement des contacts.
 *
 * @version 1.1
 * @author Nkouka Ildevert
 */
public class FenetreFormulaireEtiquette extends FenetreDialogueAbstraite {

    private static final int LONGUEUR_MAX_LIBELLE = 100;
    private JTextField txtLibelle;
    
    private final List<ContactVue> contactsProposes = new ArrayList<>();
    private final List<JCheckBox> casesContacts = new ArrayList<>();

    public FenetreFormulaireEtiquette(Controleur controleur, Frame parent, String titre, String libelleInitial, 
                                      List<ContactVue> tousLesContacts, List<ContactVue> contactsDeLEtiquette) {
        super(parent, titre, 400, 420); // Fenêtre agrandie pour faire de la place à la liste

        // Libellé de l'étiquette
        ajouter(new JLabel("Nom de l'étiquette :"));
        txtLibelle = new JTextField(libelleInitial, 20);
        ajouter(txtLibelle);
        
        // Contacts liés à cette étiquette
        ajouter(new JLabel("Appliquer cette étiquette aux contacts :"));
        
        JPanel panelContacts = new JPanel(new GridLayout(0, 1, 5, 5));
        
        if (tousLesContacts != null) {
            for (ContactVue contact : tousLesContacts) {
                JCheckBox cb = new JCheckBox(contact.getNom() + " (" + contact.getEmail() + ")");
                cb.setSelected(controleur.contientContact(contactsDeLEtiquette, contact));
                contactsProposes.add(contact);
                casesContacts.add(cb);
                panelContacts.add(cb);
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(panelContacts);
        scrollPane.setPreferredSize(new Dimension(320, 180));
        scrollPane.setMinimumSize(new Dimension(320, 180));
        ajouter(scrollPane);

        // Boutons de validation
        ajouterBoutonsValidation();
    }

    @Override
    protected void validerFormulaire() {
        String libelle = getLibelleSaisi();

        if (libelle.isBlank()) {
            afficherErreur("Nom invalide", "Le nom de l'étiquette ne peut pas être vide.");
            return;
        }
        if (libelle.length() > LONGUEUR_MAX_LIBELLE) {
            afficherErreur("Nom trop long", "Le nom de l'étiquette ne peut pas dépasser " + LONGUEUR_MAX_LIBELLE + " caractères.");
            return;
        }

        validerEtFermer();
    }

    public String getLibelleSaisi() {
        return txtLibelle.getText().trim();
    }
    
    public List<ContactVue> getContactsSelectionnes() {
        List<ContactVue> selection = new ArrayList<>();
        for (int i = 0; i < contactsProposes.size(); i++) {
            if (casesContacts.get(i).isSelected()) {
                selection.add(contactsProposes.get(i));
            }
        }
        return selection;
    }
}