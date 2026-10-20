package fr.iutfbleau.sae31.AtoZ.model;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Début du test du modèle AtoZ ===");

        try {
            // 1. Connexion à la base de données
            Connection cx = Connexion.getInstance();
            System.out.println("[OK] Connexion à la base de données réussie.");

            // Initialisation des DAO
            EtiquetteDAO etiquetteDAO = new EtiquetteDAO(cx);
            ContactDAO contactDAO = new ContactDAO(cx);
            ListeDiffusionDAO listeDAO = new ListeDiffusionDAO(cx);

            // 2. Test des Étiquettes
            System.out.println("\n--- Test des Étiquettes ---");
            Etiquette e1 = new Etiquette("VIP");
            Etiquette e2 = new Etiquette("Travail");

            etiquetteDAO.creer(e1);
            etiquetteDAO.creer(e2);
            System.out.println("Étiquettes créées : " + e1 + ", " + e2);

            // 3. Test des Contacts
            System.out.println("\n--- Test des Contacts ---");
            Contact c1 = new Contact("Alice Dupont", "alice@exemple.fr");
            Contact c2 = new Contact("Bob Martin", "bob@exemple.fr");

            c1.ajouterEtiquette(e1);
            c2.ajouterEtiquette(e2);

            contactDAO.creer(c1);
            contactDAO.creer(c2);
            System.out.println("Contacts créés : " + c1 + " et " + c2);

            // 4. Test des Listes de diffusion
            System.out.println("\n--- Test des Listes de diffusion ---");
            ListeDiffusion liste = new ListeDiffusion("Projets Pro");
            liste.ajouterCritere(new Critere(e2, true)); // Avec l'étiquette 'Travail'

            listeDAO.creer(liste);
            System.out.println("Liste créée : " + liste);

            // 5. Affichage des membres de la liste
            System.out.println("\nMembres de la liste '" + liste.getNom() + "' :");
            List<Contact> membres = listeDAO.membres(liste);
            for (Contact m : membres) {
                System.out.println(" - " + m);
            }

            // Nettoyage optionnel (suppression des éléments de test pour garder la base propre)
            System.out.println("\n--- Nettoyage ---");
            contactDAO.supprimer(c1.getId());
            contactDAO.supprimer(c2.getId());
            etiquetteDAO.supprimer(e1.getId());
            etiquetteDAO.supprimer(e2.getId());
            listeDAO.supprimer(liste.getId());
            System.out.println("[OK] Données de test supprimées de la base.");

            cx.close();
            System.out.println("\n=== Fin des tests avec succès ===");

        } catch (ModeleException e) {
            System.err.println("[ERREUR MODÈLE] : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("[ERREUR TECHNIQUE] : " + e.getMessage());
            e.printStackTrace();
        }
    }
}