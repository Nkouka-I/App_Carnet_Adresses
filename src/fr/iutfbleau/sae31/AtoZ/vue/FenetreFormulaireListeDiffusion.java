package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.CritereVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FenetreFormulaireListeDiffusion extends FenetreDialogueAbstraite {

    private static final int LONGUEUR_MAX_NOM = 50;
    private JTextField txtNomListe;
    private DefaultListModel<CritereData> modelCriteres;

    public FenetreFormulaireListeDiffusion(Frame parent, String titre, String nomInitial,
                                           List<EtiquetteVue> toutesLesEtiquettes, List<CritereVue> criteresInitiaux) {
        super(parent, titre, 450, 520);

        ajouter(new JLabel("Nom de la liste de diffusion (max " + LONGUEUR_MAX_NOM + " car.) :"));
        txtNomListe = new JTextField(nomInitial, 20);
        ajouter(txtNomListe);

        ajouter(new JLabel("Critères dynamiques :"));

        modelCriteres = new DefaultListModel<>();
        if (criteresInitiaux != null) {
            for (CritereVue c : criteresInitiaux) {
                modelCriteres.addElement(new CritereData(c));
            }
        }
        
        JList<CritereData> listCriteres = new JList<>(modelCriteres);
        JScrollPane scrollPane = new JScrollPane(listCriteres);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        scrollPane.setMinimumSize(new Dimension(400, 200)); 
        ajouter(scrollPane);

        JComboBox<EtiquetteVue> comboEtiquettes = new JComboBox<>(toutesLesEtiquettes != null ? toutesLesEtiquettes.toArray(new EtiquetteVue[0]) : new EtiquetteVue[0]);
        comboEtiquettes.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> liste, Object valeur, int index,
                                                          boolean selectionne, boolean focus) {
                super.getListCellRendererComponent(liste, valeur, index, selectionne, focus);
                if (valeur instanceof EtiquetteVue) {
                    setText(((EtiquetteVue) valeur).getNom());
                }
                return this;
            }
        });
        
        JCheckBox chkInclus = new JCheckBox("Inclus", true);
        JButton btnAjouterCritere = new JButton("Ajouter");
        JButton btnModifierCritere = new JButton("Modifier sélectionné");
        
        JButton btnSupprimerCritere = new JButton("Supprimer le critère");
        Style.styliserBoutonDanger(btnSupprimerCritere);

        JPanel panelAjoutCritere = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAjoutCritere.add(comboEtiquettes);
        panelAjoutCritere.add(chkInclus);
        panelAjoutCritere.add(btnAjouterCritere);
        ajouter(panelAjoutCritere);

        JPanel panelSuppr = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuppr.add(btnModifierCritere);
        panelSuppr.add(btnSupprimerCritere);
        ajouter(panelSuppr);

        btnAjouterCritere.addActionListener(e -> {
            EtiquetteVue etiquetteChoisie = (EtiquetteVue) comboEtiquettes.getSelectedItem();
            boolean estInclus = chkInclus.isSelected();
            if (etiquetteChoisie != null && critereAcceptable(etiquetteChoisie, estInclus, -1)) {
                modelCriteres.addElement(new CritereData(etiquetteChoisie, estInclus));
            }
        });

        listCriteres.addListSelectionListener(e -> {
            CritereData selectionne = listCriteres.getSelectedValue();
            if (!e.getValueIsAdjusting() && selectionne != null) {
                comboEtiquettes.setSelectedItem(selectionne.getEtiquette());
                chkInclus.setSelected(selectionne.isInclus());
            }
        });

        btnModifierCritere.addActionListener(e -> {
            int index = listCriteres.getSelectedIndex();
            EtiquetteVue etiquetteChoisie = (EtiquetteVue) comboEtiquettes.getSelectedItem();
            boolean estInclus = chkInclus.isSelected();

            if (index != -1 && etiquetteChoisie != null && critereAcceptable(etiquetteChoisie, estInclus, index)) {
                CritereData critere = modelCriteres.get(index);
                critere.setEtiquette(etiquetteChoisie);
                critere.setInclus(estInclus);
                modelCriteres.set(index, critere); 
            }
        });

        btnSupprimerCritere.addActionListener(e -> {
            int index = listCriteres.getSelectedIndex();
            if (index != -1) {
                modelCriteres.remove(index);
            }
        });

        ajouterBoutonsValidation();
    }

    private boolean critereAcceptable(EtiquetteVue etiquette, boolean inclus, int indexIgnore) {
        for (int i = 0; i < modelCriteres.size(); i++) {
            CritereData c = modelCriteres.get(i);
            if (i != indexIgnore && c.getEtiquette() == etiquette) {
                if (c.isInclus() == inclus) {
                    afficherErreur("Critère en double", "Un critère identique existe déjà pour cette étiquette.");
                } else {
                    afficherErreur("Critère contradictoire", "Un critère contradictoire (Inclus/Exclu) existe déjà pour cette étiquette.");
                }
                return false;
            }
        }
        return true;
    }

    @Override
    protected void validerFormulaire() {
        String nom = getNomListeSaisi();
        if (nom.isBlank()) {
            afficherErreur("Nom invalide", "Le nom de la liste ne peut pas être vide.");
            return;
        }
        if (nom.length() > LONGUEUR_MAX_NOM) {
            afficherErreur("Nom trop long", "Le nom de la liste ne peut pas dépasser " + LONGUEUR_MAX_NOM + " caractères.");
            return;
        }
        if (modelCriteres.isEmpty()) {
            afficherErreur("Critères manquants", "Une liste de diffusion doit contenir au moins un critère.");
            return;
        }
        validerEtFermer();
    }

    public String getNomListeSaisi() { return txtNomListe.getText().trim(); }

    public List<CritereVue> getCriteresList() {
        List<CritereVue> criteres = new ArrayList<>();
        for (int i = 0; i < modelCriteres.size(); i++) {
            criteres.add(modelCriteres.get(i));
        }
        return criteres;
    }
}