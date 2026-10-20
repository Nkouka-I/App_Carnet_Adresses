package fr.iutfbleau.sae31.AtoZ.vue;
import java.util.List;

/**
 * La classe <code>PanneauOperationsContact</code> est le panneau d'opérations affiché
 * lorsqu'un CONTACT est sélectionné : il propose de le modifier ou de le supprimer.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauOperationsContact extends PanneauOperationsAbstrait {

    /**
     * Nom actuel du contact.
     */
    private String nom;

    /**
     * Adresse email actuelle du contact.
     */
    private String email;

    /**
     * Toutes les étiquettes disponibles.
     */
    private final List<String> toutesLesEtiquettes;

    /**
     * Étiquettes actuellement associées au contact.
     */
    private List<String> etiquettesActuelles;

    /**
     * Construit le panneau et affiche ses boutons.
     *
     * @param apresSuppression l'action à exécuter après une suppression réussie
     * @param nom le nom actuel du contact
     * @param email l'email actuel du contact
     * @param toutesLesEtiquettes toutes les étiquettes disponibles
     * @param etiquettesActuelles les étiquettes actuellement associées au contact
     */
    public PanneauOperationsContact(Runnable apresSuppression, String nom, String email,
                                    List<String> toutesLesEtiquettes, List<String> etiquettesActuelles) {
        super(apresSuppression);
        this.nom = nom;
        this.email = email;
        this.toutesLesEtiquettes = toutesLesEtiquettes;
        this.etiquettesActuelles = etiquettesActuelles;

        construire();
    }

    /**
     * Construit (ou reconstruit) le contenu du panneau : bouton "Modifier" puis bouton "Supprimer".
     */
    private void construire() {
        preparer();

        ajouterBoutonModifier("Modifier", e -> modifier());

        ajouterBoutonSuppression(
            "Supprimer le contact",
            "Voulez-vous vraiment supprimer le contact \"" + nom + "\" ?",
            "Le contact a bien été supprimé !",
            () -> {
                // TODO: Appeler le contrôleur pour supprimer le contact
                System.out.println("le contact a bien été supprimé");
            }
        );

        terminer();
    }

    /**
     * Ouvre le formulaire de modification du contact et, s'il est validé, met à jour
     * les valeurs affichées.
     */
    private void modifier() {
        FenetreFormulaireContact form = new FenetreFormulaireContact(
            getFenetreParente(),
            "Modifier le contact",
            nom,
            email,
            toutesLesEtiquettes,
            etiquettesActuelles
        );

        FenetreDialogueAbstraite.ouvrirFormulaire(form, f -> {
            nom = f.getNomSaisi();
            email = f.getEmailSaisi();
            etiquettesActuelles = f.getEtiquettesSelectionnees();

            // TODO: Appeler le contrôleur / BDD pour mettre à jour
            System.out.println("Contact mis à jour : " + nom + " / " + email);

            // Rafraîchir l'affichage
            construire();
        });
    }
}
