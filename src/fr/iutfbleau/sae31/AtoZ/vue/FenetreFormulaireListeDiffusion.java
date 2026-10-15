package fr.iutfbleau.sae31.AtoZ.vue;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class FenetreFormulaireListeDiffusion extends FenetreDialogueAbstraite {

    private JTextField txtNomListe;
    private DefaultListModel<String> modelCriteres;
    private JList<String> listCriteres;
    private List<CritereData> criteresList;

    public static class CritereData {
        public String etiquette;
        public boolean inclus; // true = doit l'avoir, false = ne doit pas l'avoir
        
        public CritereData(String etiquette, boolean inclus) {
            this.etiquette = etiquette;
            this.inclus = inclus;
        }

        @Override
        public String toString() {
            return (inclus ? "DOIT avoir : " : "NE DOIT PAS avoir : ") + etiquette;
        }
    }

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

    public String getNomListeSaisi() { return txtNomListe.getText().trim(); }
    public List<CritereData> getCriteresList() { return criteresList; }
}