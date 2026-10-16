package fr.iutfbleau.sae31.AtoZ.vue;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * La classe <code>PanneauDroit</code> est le panneau de droite de la fenêtre principale.
 * Il affiche les opérations disponibles (modifier, supprimer) sur l'élément
 * sélectionné : un contact, une étiquette ou une liste de diffusion.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class PanneauDroit extends JPanel{

    /**
     * Bouton de suppression de l'élément sélectionné.
     */
    private JButton btnSupprimer = new JButton("Supprimer");

    /**
     * Bouton de modification de l'élément sélectionné.
     */
    private JButton btnModifier = new JButton("Modifier");

    /**
     * Construit le panneau de droite avec une largeur fixe et affiche la vue vide.
     */
    public PanneauDroit(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(250, 0)); // Limité à une largeur fixe
        setBackground(new Color(245, 245, 245));

        afficherVide();
    }

    /**
     * Affiche la vue par défaut, invitant l'utilisateur à sélectionner un élément.
     * Le contenu précédent du panneau est effacé.
     */
    public void afficherVide() {
        removeAll();
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel lblInfo = new JLabel("<html><center>Sélectionnez un élément<br>pour afficher ses détails</center></html>");
        lblInfo.setForeground(new Color(120, 120, 120));
        add(lblInfo, gbc);

        revalidate();
        repaint();
    }

    /**
     * Ajoute au panneau le bouton de suppression, configuré avec le comportement voulu.
     * Un clic ouvre une confirmation ; si l'utilisateur accepte, l'action est exécutée,
     * un message de succès est affiché et le panneau revient à la vue vide.
     *
     * @param gbc les contraintes de placement à utiliser
     * @param ligne l'indice de la ligne de la grille où placer le bouton
     * @param texteBouton le texte affiché sur le bouton
     * @param messageConfirmation le message de la boîte de confirmation
     * @param messageSucces le message affiché après la suppression
     * @param actionAEffectuer l'action à exécuter en cas de confirmation (peut être <code>null</code>)
     */
    public void ajouterBoutonSuppression(GridBagConstraints gbc, int ligne, String texteBouton, String messageConfirmation, String messageSucces, Runnable actionAEffectuer) {

        btnSupprimer.setText(texteBouton);
        btnSupprimer.setForeground(Color.RED);

        // On enleve les anciens écouteurs
        for (ActionListener al : btnSupprimer.getActionListeners()) {
            btnSupprimer.removeActionListener(al);
        }

        // On ajoute le nouveau comportement
        btnSupprimer.addActionListener(e -> {

            Window fenetreParente = SwingUtilities.getWindowAncestor(this);

            // Le pop-up bloquant
            int reponse = JOptionPane.showConfirmDialog(
                fenetreParente, 
                messageConfirmation, 
                "Confirmation de suppression", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE
            );
            
            if (reponse == JOptionPane.YES_OPTION) {
                
                // TODO: Code pour supprimer de la base de données
                if (actionAEffectuer != null) {
                    actionAEffectuer.run();
                }

                // pop-up de confirmation
                JOptionPane.showMessageDialog(
                    fenetreParente, 
                    messageSucces, 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                
                // Remise à zéro de l'affichage
                afficherVide();
            }
        });
        gbc.gridy = ligne;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(btnSupprimer, gbc);
    }

    /**
     * Méthode générique pour ouvrir un formulaire et traiter sa validation via une interface.
     * Le formulaire est modal : l'appel est bloquant jusqu'à sa fermeture. L'écouteur
     * n'est appelé que si le formulaire a été validé.
     *
     * @param <T> le type de formulaire ouvert
     * @param formulaire le formulaire à afficher
     * @param listener l'écouteur appelé si le formulaire est validé
     */
    private <T extends FenetreDialogueAbstraite> void ouvrirFormulaire(T formulaire, FormulaireListener<T> listener) {
        formulaire.setVisible(true);
        
        if (formulaire.isValide()) {
            listener.onValider(formulaire);
        }
    }

    /**
     * Vue dédiée lorsqu'un CONTACT est sélectionné : affiche les boutons pour modifier
     * et supprimer le contact.
     *
     * @param nomActuel le nom actuel du contact
     * @param emailActuel l'email actuel du contact
     * @param toutesLesEtiquettes toutes les étiquettes disponibles
     * @param etiquettesActuelles les étiquettes actuellement associées au contact
     */
    public void afficherOperationsContact(String nomActuel, String emailActuel, 
                                        List<String> toutesLesEtiquettes, List<String> etiquettesActuelles) {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int ligne = 0;

        // Boutons Modifier
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 5, 10);
        btnModifier = new JButton("Modifier");

        btnModifier.addActionListener(e -> {
            Frame fenetreParente = (Frame) SwingUtilities.getWindowAncestor(this);

            FenetreFormulaireContact form = new FenetreFormulaireContact(
                fenetreParente, 
                "Modifier le contact", 
                nomActuel, 
                emailActuel, 
                toutesLesEtiquettes, 
                etiquettesActuelles
            );

            ouvrirFormulaire(form, f -> {
                String nouveauNom = f.getNomSaisi();
                String nouvelEmail = f.getEmailSaisi();
                List<String> nouvellesEtiquettes = f.getEtiquettesSelectionnees();

                // TODO: Appeler le contrôleur / BDD pour mettre à jour
                System.out.println("Contact mis à jour : " + nouveauNom + " / " + nouvelEmail);

                // Rafraîchir l'affichage
                afficherOperationsContact(nouveauNom, nouvelEmail, toutesLesEtiquettes, nouvellesEtiquettes);
            });
        });

        add(btnModifier, gbc);

        // Bouton Supprimer
        ajouterBoutonSuppression(
            gbc, 
            ligne++,
            "Supprimer le contact",
            "Voulez-vous vraiment supprimer le contact de ?",
            "Le contact a bien été supprimé !",
            () -> {
                // TODO: Appeler le contrôleur pour supprimer le contact
                System.out.println("le contact a bien été supprimé");
            }
        );

        revalidate();
        repaint();
    }

    /**
     * Vue dédiée lorsqu'une ÉTIQUETTE est sélectionnée : affiche les boutons pour
     * modifier et supprimer l'étiquette, ainsi que le titre introduisant les listes
     * où elle apparaît.
     *
     * @param nomEtiquette le libellé de l'étiquette sélectionnée
     */
    public void afficherOperationsEtiquette(String nomEtiquette) {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int ligne = 0;

        // Bouton Modifier
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 5, 10);
        btnModifier = new JButton("Modifier l'étiquette");

        btnModifier.addActionListener(e -> {
            Frame fenetreParente = (Frame) SwingUtilities.getWindowAncestor(this);

            FenetreFormulaireEtiquette form = new FenetreFormulaireEtiquette(
                fenetreParente, 
                "Modifier l'étiquette", 
                nomEtiquette
            );

            ouvrirFormulaire(form, f -> {
                String nouveauLibelle = f.getLibelleSaisi();
                
                // TODO: Appeler le contrôleur 
                System.out.println("Étiquette mise à jour : " + nouveauLibelle);
                
                // Rafraîchir l'affichage
                afficherOperationsEtiquette(nouveauLibelle);
            });
        });

        add(btnModifier, gbc);
        
        // Titre explicatif Etiquette
        gbc.gridy = ligne++;
        JLabel lblTitre = new JLabel("<html><b>L'étiquette &quot;" + nomEtiquette + "&quot; apparaît dans les listes :</b></html>");
        lblTitre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitre, gbc);

        /*TODO Boucle que ajoute les differents noms de listes*/

        // Bouton Supprimer
        ajouterBoutonSuppression(
            gbc, 
            ligne++,
            "Supprimer l'étiquette",
            "Voulez-vous vraiment supprimer l'étiquette \"" + nomEtiquette + "\" ?",
            "L'étiquette a bien été supprimée !",
            () -> {
                // TODO: Appeler le contrôleur pour supprimer l'étiquette
                System.out.println("l'étiquette a bien été supprimé...");
            }
        );

        revalidate();
        repaint();
    }

    /**
     * Vue dédiée lorsqu'une LISTE DE DIFFUSION est sélectionnée : affiche les boutons
     * pour modifier et supprimer la liste, ainsi que le titre introduisant ses critères.
     *
     * @param nomListe le nom de la liste de diffusion sélectionnée
     * @param toutesLesEtiquettes toutes les étiquettes disponibles pour définir un critère
     * @param criteresActuels les critères actuellement définis pour la liste
     */
    public void afficherOperationsListeDiffusion(String nomListe, List<String> toutesLesEtiquettes, List<FenetreFormulaireListeDiffusion.CritereData> criteresActuels) {
        removeAll();
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        int ligne = 0;

        // Bouton Modifier
        gbc.gridy = ligne++;
        gbc.insets = new Insets(20, 10, 5, 10);
        btnModifier = new JButton("Modifier la liste");

        btnModifier.addActionListener(e -> {
            Frame fenetreParente = (Frame) SwingUtilities.getWindowAncestor(this);

            FenetreFormulaireListeDiffusion form = new FenetreFormulaireListeDiffusion(
                fenetreParente, 
                "Modifier la liste de diffusion", 
                nomListe, 
                toutesLesEtiquettes, 
                criteresActuels
            );

            ouvrirFormulaire(form, f -> {
                String nouveauNomListe = f.getNomListeSaisi();
                List<FenetreFormulaireListeDiffusion.CritereData> nouveauxCriteres = f.getCriteresList();

                // TODO: Appeler le contrôleur / BDD pour mettre à jour
                System.out.println("Liste mise à jour : " + nouveauNomListe);

                // Rafraîchir l'affichage
                afficherOperationsListeDiffusion(nouveauNomListe, toutesLesEtiquettes, nouveauxCriteres);
            });
        });

        add(btnModifier, gbc);

        // Titre des critères
        gbc.gridy = ligne++;
        gbc.insets = new Insets(10, 10, 5, 10);
        JLabel lblTitre = new JLabel("<html><b>Critères de la liste " + nomListe + " :</b><br></html>", JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitre, gbc);

        /*TODO Boucle que ajoute les differents noms de critere*/
        
        // Bouton Supprimer
        ajouterBoutonSuppression(
            gbc, 
            ligne++,
            "Supprimer la liste de diffusion",
            "Voulez-vous vraiment supprimer la liste de diffusion \"" + nomListe + "\" ?",
            "La liste de diffusion a bien été supprimé !",
            () -> {
                // TODO: Appeler le contrôleur
                System.out.println("La liste de diffusion a bien été supprimé");
            }
        );

        revalidate();
        repaint();
    }
}
