package fr.iutfbleau.sae31.AtoZ.vue;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.Controleur;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * La classe <code>PanneauCentral</code> est le panneau central de la fenêtre principale.
 * Il alterne, grâce à un <code>CardLayout</code>, entre le répertoire de contacts
 * ({@link PanneauRepertoire}) et la page d'une liste de diffusion
 * ({@link PanneauListeDiffusion}).
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauCentral extends JPanel{

    /**
     * Nom de la carte affichant le répertoire.
     */
    private static final String CARTE_REPERTOIRE = "repertoire";

    /**
     * Nom de la carte affichant une liste de diffusion.
     */
    private static final String CARTE_LISTE_DIFFUSION = "listeDiffusion";

    /**
     * Gestionnaire de disposition qui alterne entre les deux vues.
     */
    private final CardLayout cartes = new CardLayout();

    /**
     * Vue du répertoire de contacts.
     */
    private final PanneauRepertoire panneauRepertoire;

    /**
     * Vue d'une liste de diffusion.
     */
    private final PanneauListeDiffusion panneauListeDiffusion;

    /**
     * Construit le panneau central et affiche par défaut la vue répertoire.
     *
     * @param listener l'écouteur à notifier lors des clics sur les contacts
     * @param controleur le contrôleur fournissant les données
     */
	public PanneauCentral(NavigationListener listener, Controleur controleur) {
        setLayout(cartes);
        setBackground(Style.FOND_CONTENU);
        Style.bordureContenu(this);

        panneauRepertoire = new PanneauRepertoire(listener, controleur);
        panneauListeDiffusion = new PanneauListeDiffusion(controleur);

        add(panneauRepertoire, CARTE_REPERTOIRE);
        add(panneauListeDiffusion, CARTE_LISTE_DIFFUSION);

        // Par défaut, on charge la vue répertoire
        afficherRepertoire();
    }

    /**
     * Affiche la vue répertoire telle qu'elle est (même filtre, même recherche).
     */
	public void afficherRepertoire() {
        cartes.show(this, CARTE_REPERTOIRE);
    }

    /**
     * Affiche la vue répertoire avec un filtre.
     *
     * @param filtre l'étiquette que les contacts doivent avoir (<code>null</code> pour tous les contacts)
     */
    public void afficherRepertoire(EtiquetteVue filtre) {
        panneauRepertoire.afficherContacts(filtre);
        cartes.show(this, CARTE_REPERTOIRE);
    }

    /**
     * Affiche la page dédiée à une liste de diffusion.
     *
     * @param liste la liste de diffusion à afficher
     *              (<code>null</code> pour afficher le message d'accueil des listes)
     */
    public void afficherListeDiffusion(ListeDiffusionVue liste) {
        panneauListeDiffusion.afficher(liste);
        cartes.show(this, CARTE_LISTE_DIFFUSION);
    }

    /**
     * Met un contact en évidence dans le répertoire.
     *
     * @param contact le contact à mettre en évidence (<code>null</code> pour ne rien sélectionner)
     */
    public void selectionnerContact(ContactVue contact) {
        panneauRepertoire.selectionnerContact(contact);
    }

    /**
     * Recharge les deux vues à partir des données actuelles, sans changer la vue affichée.
     *
     * @param filtre l'étiquette qui filtre le répertoire (<code>null</code> pour aucun filtre)
     * @param liste la liste de diffusion affichée (<code>null</code> pour le message d'accueil)
     */
    public void actualiser(EtiquetteVue filtre, ListeDiffusionVue liste) {
        panneauRepertoire.actualiser(filtre);
        panneauListeDiffusion.afficher(liste);
    }
}
