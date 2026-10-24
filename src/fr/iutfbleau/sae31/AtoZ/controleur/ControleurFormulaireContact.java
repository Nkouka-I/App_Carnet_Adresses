package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.FenetreFormulaireContact;
import fr.iutfbleau.sae31.AtoZ.vue.FormulaireListener;

/**
 * La classe <code>ControleurFormulaireContact</code> réagit à la validation du formulaire d'un
 * contact : elle crée un nouveau contact, ou modifie un contact existant, avec le nom,
 * l'adresse mail et les étiquettes saisis par l'utilisateur.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class ControleurFormulaireContact implements FormulaireListener<FenetreFormulaireContact> {

    /**
     * Le contrôleur principal de l'application.
     */
    private final Controleur controleur;

    /**
     * Le contact à modifier, ou <code>null</code> si le formulaire sert à en créer un.
     */
    private final ContactVue contactAModifier;

    /**
     * Constructeur d'un contrôleur de formulaire destiné à la création d'un contact.
     *
     * @param controleur le contrôleur principal de l'application
     */
    public ControleurFormulaireContact(Controleur controleur) { this(controleur, null); }

    /**
     * Constructeur d'un contrôleur de formulaire destiné à la création ou à la modification
     * d'un contact.
     *
     * @param controleur le contrôleur principal de l'application
     * @param contactAModifier le contact à modifier, ou <code>null</code> pour en créer un
     */
    public ControleurFormulaireContact(Controleur controleur, ContactVue contactAModifier) {
        this.controleur = controleur;
        this.contactAModifier = contactAModifier;
    }

    /**
     * Récupère les valeurs saisies dans le formulaire puis crée ou modifie le contact.
     *
     * @param formulaire le formulaire de contact qui vient d'être validé
     */
    @Override
    public void onValider(FenetreFormulaireContact formulaire) {
        String nom = formulaire.getNomSaisi();
        String email = formulaire.getEmailSaisi();
        var etiquettes = formulaire.getEtiquettesSelectionnees();

        if (contactAModifier == null) {
            controleur.creerContact(nom, email, etiquettes);
        } else {
            controleur.modifierContact(contactAModifier, nom, email, etiquettes);
        }
    }

}