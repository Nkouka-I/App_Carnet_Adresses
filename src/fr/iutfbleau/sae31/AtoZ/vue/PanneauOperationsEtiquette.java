package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 * La classe <code>PanneauOperationsEtiquette</code> est le panneau d'opérations affiché
 * lorsqu'une ÉTIQUETTE est sélectionnée : il propose de la modifier (nom et contacts qui
 * la possèdent) ou de la supprimer, et liste les listes de diffusion où elle apparaît.
 *
 * @version 1.2
 * @author Nkouka Ildevert
 */
public class PanneauOperationsEtiquette extends PanneauOperationsAbstrait {

    /**
     * Étiquette sélectionnée.
     */
    private final EtiquetteVue etiquette;

    /**
     * Construit le panneau et affiche ses éléments.
     *
     * @param controleur le contrôleur auquel les opérations sont demandées
     * @param etiquette l'étiquette sélectionnée
     */
    public PanneauOperationsEtiquette(Controleur controleur, EtiquetteVue etiquette) {
        super(controleur);
        this.etiquette = etiquette;
        construire();
    }

    /**
     * Construit le contenu du panneau : bouton "Modifier", titre explicatif, listes
     * où l'étiquette apparaît, puis bouton "Supprimer".
     */
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

    /**
     * Ouvre le formulaire de modification de l'étiquette et, s'il est validé, demande au
     * contrôleur de la renommer et de fixer les contacts qui la possèdent.
     */
    private void modifier() {
        FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
            getFenetreParente(),
            "Modifier l'étiquette",
            etiquette.getNom(),
            getControleur().getContacts(),
            getControleur().getContacts(etiquette)
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            appliquerContacts(etiquette, f.getContactsSelectionnes());
            if (!etiquette.getNom().equals(f.getLibelleSaisi())) {
                getControleur().modifierEtiquette(etiquette, f.getLibelleSaisi());
            }
        });
    }

    /**
     * Ajoute l'étiquette aux contacts voulus qui ne l'ont pas, et la retire aux autres.
     * Seuls les contacts dont la situation change sont modifiés ; ils sont reconnus par
     * <code>equals</code>, car le contrôleur recharge ses objets après chaque modification.
     *
     * @param etiquette l'étiquette concernée
     * @param voulus les contacts qui doivent la posséder
     */
    private void appliquerContacts(EtiquetteVue etiquette, List<ContactVue> voulus) {
        List<ContactVue> tous = new ArrayList<>(getControleur().getContacts());
        for (ContactVue contact : tous) {
            boolean doitLAvoir = voulus.contains(contact);
            boolean laPossede = contact.getEtiquettes().contains(etiquette);
            if (doitLAvoir != laPossede) {
                List<EtiquetteVue> nouvelles = new ArrayList<>(contact.getEtiquettes());
                if (doitLAvoir) {
                    nouvelles.add(etiquette);
                } else {
                    nouvelles.remove(etiquette);
                }
                getControleur().modifierContact(contact, contact.getNom(), contact.getEmail(), nouvelles);
            }
        }
    }
}
