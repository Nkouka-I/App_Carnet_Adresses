package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

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
     * Modèle de données de la liste graphique affichant les critères.
     */
    private DefaultListModel<String> modelCriteres;

    /**
     * Composant graphique affichant les critères de la liste.
     */
    private JList<String> listCriteres;

    /**
     * Critères actuellement définis pour la liste de diffusion.
     */
    private List<CritereData> criteresList;

    /**
     * La classe <code>CritereData</code> représente un critère d'appartenance à une liste
     * de diffusion : une étiquette que le contact doit avoir ou ne doit pas avoir.
     */
    public static class CritereData {

        /**
         * Libellé de l'étiquette concernée par le critère.
         */
        public String etiquette;

        /**
         * <code>true</code> si le contact doit avoir l'étiquette, <code>false</code> s'il
         * ne doit pas l'avoir.
         */
        public boolean inclus; // true = doit l'avoir, false = ne doit pas l'avoir

        /**
         * Construit un critère.
         *
         * @param etiquette le libellé de l'étiquette concernée
         * @param inclus <code>true</code> si l'étiquette est requise, <code>false</code> si elle est interdite
         */
        public CritereData(String etiquette, boolean inclus) {
            this.etiquette = etiquette;
            this.inclus = inclus;
        }

        /**
         * Renvoie une description lisible du critère, utilisée pour l'affichage dans la liste.
         *
         * @return le texte "DOIT avoir : ..." ou "NE DOIT PAS avoir : ..." suivi de l'étiquette
         */
        @Override
        public String toString() {
            return (inclus ? "DOIT avoir : " : "NE DOIT PAS avoir : ") + etiquette;
        }
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
        if (criteresList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erreur : Une liste de diffusion doit contenir au moins un critère.", "Critères manquants", JOptionPane.ERROR_MESSAGE);
            return;
        }

        valide = true;
        dispose();
    }

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
        this.criteresList = criteresInitiaux != null ? new ArrayList<>(criteresInitiaux) : new ArrayList<>();

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
        for (CritereData c : criteresList) {
            modelCriteres.addElement(c.toString());
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
        JButton btnSupprimerCritere = new JButton("Supprimer sélectionné");

        panelAjoutCritere.add(comboEtiquettes);
        panelAjoutCritere.add(chkInclus);
        panelAjoutCritere.add(btnAjouterCritere);

        gbc.gridy = ligne++;
        add(panelAjoutCritere, gbc);

        gbc.gridy = ligne++;
        JPanel panelSuppr = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuppr.add(btnSupprimerCritere);
        add(panelSuppr, gbc);

        btnAjouterCritere.addActionListener(e -> {
            String etiquetteChoisie = (String) comboEtiquettes.getSelectedItem();
            if (etiquetteChoisie != null) {
                boolean inclus = chkInclus.isSelected();
                CritereData nouveauCritere = new CritereData(etiquetteChoisie, inclus);
                criteresList.add(nouveauCritere);
                modelCriteres.addElement(nouveauCritere.toString());
            }
        });

        btnSupprimerCritere.addActionListener(e -> {
            int index = listCriteres.getSelectedIndex();
            if (index != -1) {
                criteresList.remove(index);
                modelCriteres.remove(index);
            }
        });

        // Boutons de validation de base
        ajouterBoutonsValidation(ligne);
    }

    /**
     * Renvoie le nom saisi, sans les espaces en début et fin de chaîne.
     *
     * @return le nom de la liste de diffusion
     */
    public String getNomListeSaisi() { return txtNomListe.getText().trim(); }

    /**
     * Renvoie les critères actuellement définis dans le formulaire.
     *
     * @return la liste des critères de la liste de diffusion
     */
    public List<CritereData> getCriteresList() { return criteresList; }
}
