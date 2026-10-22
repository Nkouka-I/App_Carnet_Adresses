package fr.iutfbleau.sae31.AtoZ.vue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;

/**
 * La classe <code>PanneauOperationsContact</code> est le panneau d'opérations affiché
 * lorsqu'un CONTACT est sélectionné : il propose de le modifier ou de le supprimer.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauOperationsContact extends PanneauOperationsAbstrait {

    /**
     * Contact sélectionné.
     */
    private final ContactVue contact;

    /**
     * Construit le panneau et affiche ses boutons.
     *
     * @param controleur le contrôleur auquel les opérations sont demandées
     * @param contact le contact sélectionné
     */
    public PanneauOperationsContact(Controleur controleur, ContactVue contact) {
        super(controleur);
        this.contact = contact;

        construire();
    }

    /**
     * Construit le contenu du panneau : bouton "Modifier" puis bouton "Supprimer".
     */
    private void construire() {
        preparer();

        ajouterBoutonModifier("Modifier", e -> modifier());

        ajouterBoutonSuppression(
            "Supprimer le contact",
            "Voulez-vous vraiment supprimer le contact \"" + contact.getNom() + "\" ?",
            () -> getControleur().supprimerContact(contact)
        );

        terminer();
    }

    /**
     * Ouvre le formulaire de modification du contact et, s'il est validé, demande la
     * modification au contrôleur.
     */
    private void modifier() {
        FenetreFormulaireContact form = new FenetreFormulaireContact(
            getFenetreParente(),
            "Modifier le contact",
            contact.getNom(),
            contact.getEmail(),
            getControleur().getEtiquettes(),
            contact.getEtiquettes()
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f ->
            getControleur().modifierContact(contact, f.getNomSaisi(), f.getEmailSaisi(), f.getEtiquettesSelectionnees())
        );
    }
}
