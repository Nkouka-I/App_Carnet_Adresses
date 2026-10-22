package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.vue.FenetreFormulaireContact;
import fr.iutfbleau.sae31.AtoZ.vue.FormulaireListener;

public class ControleurFormulaireContact implements FormulaireListener<FenetreFormulaireContact> {
    private final Controleur controleur;
    private final ContactVue contactAModifier;

    public ControleurFormulaireContact(Controleur controleur) { this(controleur, null); }
    public ControleurFormulaireContact(Controleur controleur, ContactVue contactAModifier) {
        this.controleur = controleur;
        this.contactAModifier = contactAModifier;
    }

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