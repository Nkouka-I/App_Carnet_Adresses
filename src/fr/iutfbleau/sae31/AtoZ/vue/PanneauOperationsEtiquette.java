package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

public class PanneauOperationsEtiquette extends PanneauOperationsAbstrait {

    private final EtiquetteVue etiquette;

    public PanneauOperationsEtiquette(Controleur controleur, EtiquetteVue etiquette) {
        super(controleur);
        this.etiquette = etiquette;
        construire();
    }

    private void construire() {
        preparer();

        ajouterBoutonModifier("Modifier l'étiquette", e -> modifier());

        ajouterTitre("L'étiquette \"" + etiquette.getNom() + "\"", JLabel.CENTER, new Insets(20, 10, 2, 10));
        ajouterTitre("apparaît dans les listes :", JLabel.CENTER, new Insets(0, 10, 5, 10));

        List<ListeDiffusionVue> listes = getControleur().getListesUtilisant(etiquette);
        if (listes.isEmpty()) {
            ajouterTexte("(aucune liste)", new Insets(0, 20, 2, 10));
        }
        for (ListeDiffusionVue liste : listes) {
            ajouterTexte("• " + liste.getNom(), new Insets(0, 20, 2, 10));
        }

        ajouterBoutonSuppression(
            "Supprimer l'étiquette",
            "Voulez-vous vraiment supprimer l'étiquette \"" + etiquette.getNom() + "\" ?",
            () -> getControleur().supprimerEtiquette(etiquette)
        );

        terminer();
    }

    private void modifier() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
            getControleur(),
            getFenetreParente(),
            "Modifier l'étiquette",
            etiquette.getNom(),
            getControleur().getContacts(),
            getControleur().getContacts(etiquette)
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            getControleur().modifierEtiquette(etiquette, f.getLibelleSaisi());
            
            List<ContactVue> anciens = getControleur().getContacts(etiquette);
            List<ContactVue> nouveaux = f.getContactsSelectionnes();

            for (ContactVue c : getControleur().getContacts()) {
                boolean avait = getControleur().contientContact(anciens, c);
                boolean aura = getControleur().contientContact(nouveaux, c);
                if (avait != aura) {
                    List<EtiquetteVue> etiqs = new ArrayList<>(c.getEtiquettes());
                    if (aura) {
                        etiqs.add(etiquette);
                    } else {
                        etiqs.removeIf(x -> getControleur().memeEtiquette(x, etiquette));
                    }
                    getControleur().modifierContact(c, c.getNom(), c.getEmail(), etiqs);
                }
            }
        });
    }
}