package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;

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

    /**
     * Nombre maximal de caractères du libellé d'une étiquette.
     */
    private static final int LONGUEUR_MAX_LIBELLE = 100;

    /**
     * Champ de saisie du libellé de l'étiquette.
     */
    private JTextField txtLibelle;

    /**
     * Contacts proposés, dans l'ordre d'affichage.
     */
    private final List<ContactVue> contactsProposes = new ArrayList<>();

    /**
     * Case à cocher de chaque contact proposé (même ordre que {@link #contactsProposes}).
     */
    private final List<JCheckBox> casesContacts = new ArrayList<>();

    /**
     * Construit le formulaire et pré-remplit ses champs.
     *
     * @param parent la fenêtre parente
     * @param titre le titre de la boîte de dialogue
     * @param libelleInitial le libellé à afficher au départ (vide pour une création)
     * @param tousLesContacts tous les contacts proposés à la sélection
     * @param contactsDeLEtiquette les contacts à cocher au départ
     */
    public FenetreFormulaireEtiquette(Frame parent, String titre, String libelleInitial,
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
                cb.setSelected(contactsDeLEtiquette != null && contactsDeLEtiquette.contains(contact));
                contactsProposes.add(contact);
                casesContacts.add(cb);
                panelContacts.add(cb);
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelContacts);
        scrollPane.setPreferredSize(new Dimension(320, 180));
        ajouter(scrollPane);

        // Boutons de validation
        ajouterBoutonsValidation();
    }

    /**
     * Vérifie la saisie du formulaire. Le libellé ne doit pas être vide ni dépasser 100
     * caractères. En cas d'erreur, un message est affiché et le formulaire reste ouvert.
     */
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

    /**
     * Renvoie le libellé saisi, sans les espaces en début et fin de chaîne.
     *
     * @return le libellé de l'étiquette
     */
    public String getLibelleSaisi() {
        return txtLibelle.getText().trim();
    }

    /**
     * Renvoie les contacts cochés dans le formulaire.
     *
     * @return les contacts sélectionnés
     */
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
