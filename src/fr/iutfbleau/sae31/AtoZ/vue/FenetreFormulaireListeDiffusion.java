package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * La classe <code>FenetreFormulaireListeDiffusion</code> est le formulaire de création et de
 * modification d'une liste de diffusion. Il permet de saisir le nom de la liste et de
 * définir ses critères dynamiques, c'est-à-dire les étiquettes que les contacts doivent
 * posséder (ou ne pas posséder) pour faire partie de la liste.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class FenetreFormulaireListeDiffusion extends FenetreDialogueAbstraite {

    /**
     * Champ de saisie du nom de la liste de diffusion.
     */
    private JTextField txtNomListe;

    /**
     * Modèle de données des critères de la liste. C'est l'unique source de vérité :
     * la liste graphique l'affiche et {@link #getCriteresList()} le relit.
     */
    private DefaultListModel<CritereData> modelCriteres;

    /**
     * Composant graphique affichant les critères de la liste.
     */
    private JList<CritereData> listCriteres;

    /**
     * Construit le formulaire et pré-remplit ses champs.
     *
     * @param parent la fenêtre parente
     * @param titre le titre de la boîte de dialogue
     * @param nomInitial le nom à afficher au départ (vide pour une création)
     * @param toutesLesEtiquettes toutes les étiquettes proposées pour définir un critère
     * @param criteresInitiaux les critères déjà définis (peut être <code>null</code> pour une création)
     */
    public FenetreFormulaireListeDiffusion(Frame parent, String titre, String nomInitial,
                                           List<String> toutesLesEtiquettes, List<CritereData> criteresInitiaux) {
        super(parent, titre, 450, 420);

        int ligne = 0;

        // Nom de la liste
        gbc.gridy = ligne++;
        add(new JLabel("Nom de la liste de diffusion (max 50 car.) :"), gbc);
        gbc.gridy = ligne++;
        txtNomListe = new JTextField(nomInitial, 20);
        add(txtNomListe, gbc);

        // Liste des critères existants
        gbc.gridy = ligne++;
        add(new JLabel("Critères dynamiques :"), gbc);

        modelCriteres = new DefaultListModel<>();
        if (criteresInitiaux != null) {
            // On travaille sur des copies : annuler le formulaire ne doit pas modifier les originaux
            for (CritereData c : criteresInitiaux) {
                modelCriteres.addElement(new CritereData(c));
            }
        }
        listCriteres = new JList<>(modelCriteres);
        JScrollPane scrollPane = new JScrollPane(listCriteres);
        scrollPane.setPreferredSize(new Dimension(400, 90));
        gbc.gridy = ligne++;
        add(scrollPane, gbc);

        // Panneau d'ajout critère
        JPanel panelAjoutCritere = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> comboEtiquettes = new JComboBox<>(toutesLesEtiquettes != null ? toutesLesEtiquettes.toArray(new String[0]) : new String[0]);
        JCheckBox chkInclus = new JCheckBox("Inclus (doit l'avoir)", true);
        JButton btnAjouterCritere = new JButton("Ajouter");
        JButton btnModifierCritere = new JButton("Modifier sélectionné");
        JButton btnSupprimerCritere = new JButton("Supprimer sélectionné");

        panelAjoutCritere.add(comboEtiquettes);
        panelAjoutCritere.add(chkInclus);
        panelAjoutCritere.add(btnAjouterCritere);

        gbc.gridy = ligne++;
        add(panelAjoutCritere, gbc);

        gbc.gridy = ligne++;
        JPanel panelSuppr = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuppr.add(btnModifierCritere);
        panelSuppr.add(btnSupprimerCritere);
        add(panelSuppr, gbc);

        btnAjouterCritere.addActionListener(e -> {
            String etiquetteChoisie = (String) comboEtiquettes.getSelectedItem();
            if (etiquetteChoisie != null) {
                modelCriteres.addElement(new CritereData(etiquetteChoisie, chkInclus.isSelected()));
            }
        });

        // Sélectionner un critère charge ses valeurs dans la liste déroulante et la case à cocher
        listCriteres.addListSelectionListener(e -> {
            CritereData selectionne = listCriteres.getSelectedValue();
            if (!e.getValueIsAdjusting() && selectionne != null) {
                comboEtiquettes.setSelectedItem(selectionne.getEtiquette());
                chkInclus.setSelected(selectionne.isInclus());
            }
        });

        // Applique l'étiquette et le type choisis au critère sélectionné
        btnModifierCritere.addActionListener(e -> {
            int index = listCriteres.getSelectedIndex();
            String etiquetteChoisie = (String) comboEtiquettes.getSelectedItem();
            if (index != -1 && etiquetteChoisie != null) {
                CritereData critere = modelCriteres.get(index);
                critere.setEtiquette(etiquetteChoisie);
                critere.setInclus(chkInclus.isSelected());
                modelCriteres.set(index, critere); // déclenche le rafraîchissement de l'affichage
            }
        });

        btnSupprimerCritere.addActionListener(e -> {
            int index = listCriteres.getSelectedIndex();
            if (index != -1) {
                modelCriteres.remove(index);
            }
        });

        // Boutons de validation de base
        ajouterBoutonsValidation(ligne);
    }

    /**
     * Vérifie la saisie du formulaire. Le nom ne doit pas être vide ni dépasser 50
     * caractères, et la liste doit comporter au moins un critère. En cas d'erreur, un
     * message est affiché et le formulaire reste ouvert.
     */
    @Override
    protected void validerFormulaire() {
        String nom = txtNomListe.getText().trim();

        if (nom.isBlank()) {
            JOptionPane.showMessageDialog(this, "Erreur : Le nom de la liste ne peut pas être vide.", "Nom invalide", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (nom.length() > 50) {
            JOptionPane.showMessageDialog(this, "Erreur : Le nom de la liste ne peut pas dépasser 50 caractères.", "Nom trop long", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (modelCriteres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erreur : Une liste de diffusion doit contenir au moins un critère.", "Critères manquants", JOptionPane.ERROR_MESSAGE);
            return;
        }

        valide = true;
        dispose();
    }

    /**
     * Renvoie le nom saisi, sans les espaces en début et fin de chaîne.
     *
     * @return le nom de la liste de diffusion
     */
    public String getNomListeSaisi() { return txtNomListe.getText().trim(); }

    /**
     * Renvoie une copie des critères actuellement définis dans le formulaire.
     *
     * @return la liste des critères de la liste de diffusion
     */
    public List<CritereData> getCriteresList() { return Collections.list(modelCriteres.elements()); }
}
