package fr.iutfbleau.sae31.AtoZ.vue;

import java.awt.*;
import javax.swing.*;

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
     */
	public PanneauCentral(NavigationListener listener) {
        setLayout(cartes);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panneauRepertoire = new PanneauRepertoire(listener);
        panneauListeDiffusion = new PanneauListeDiffusion();

        add(panneauRepertoire, CARTE_REPERTOIRE);
        add(panneauListeDiffusion, CARTE_LISTE_DIFFUSION);

        // Par défaut, on charge la vue répertoire
        afficherRepertoire();
    }

    /**
     * Affiche la vue répertoire. Le répertoire n'est pas reconstruit : la recherche
     * saisie est conservée d'un affichage à l'autre.
     */
	public void afficherRepertoire() {
        cartes.show(this, CARTE_REPERTOIRE);
    }

    /**
     * Affiche la page dédiée à une liste de diffusion.
     *
     * @param nomListe le nom de la liste de diffusion à afficher
     *                 (<code>null</code> pour afficher le message d'accueil des listes)
     */
    public void afficherListeDiffusion(String nomListe) {
        panneauListeDiffusion.afficher(nomListe);
        cartes.show(this, CARTE_LISTE_DIFFUSION);
    }
}
