package fr.iutfbleau.sae31.AtoZ.vue;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe <code>DonneesTest</code> fournit les données factices utilisées par la vue
 * tant que le modèle n'est pas branché. Elle est destinée à disparaître : chaque méthode
 * sera remplacée par un appel au modèle ou au contrôleur.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public final class DonneesTest {

    /**
     * La classe <code>ContactTest</code> est un contact factice : un nom, un email et des
     * étiquettes.
     */
    public static class ContactTest {

        /**
         * Nom du contact.
         */
        private final String nom;

        /**
         * Adresse email du contact.
         */
        private final String email;

        /**
         * Étiquettes associées au contact.
         */
        private final List<String> etiquettes;

        /**
         * Construit un contact factice.
         *
         * @param nom le nom du contact
         * @param email l'adresse email du contact
         * @param etiquettes les étiquettes associées au contact
         */
        public ContactTest(String nom, String email, List<String> etiquettes) {
            this.nom = nom;
            this.email = email;
            this.etiquettes = etiquettes;
        }

        /**
         * Renvoie le nom du contact.
         *
         * @return le nom du contact
         */
        public String getNom() { return nom; }

        /**
         * Renvoie l'adresse email du contact.
         *
         * @return l'email du contact
         */
        public String getEmail() { return email; }

        /**
         * Renvoie les étiquettes associées au contact.
         *
         * @return la liste des étiquettes du contact
         */
        public List<String> getEtiquettes() { return etiquettes; }
    }

    /**
     * Constructeur privé : la classe n'est pas instanciable.
     */
    private DonneesTest() {
    }

    /**
     * Renvoie toutes les étiquettes factices.
     *
     * @return la liste des libellés d'étiquettes
     */
    public static List<String> etiquettes() {
        return List.of("Client", "Famille", "Travail", "VIP");
    }

    /**
     * Renvoie les noms des listes de diffusion factices.
     *
     * @return la liste des noms de listes de diffusion
     */
    public static List<String> listesDiffusion() {
        return List.of("Projet", "News");
    }

    /**
     * Renvoie les contacts factices.
     *
     * @return la liste des contacts
     */
    public static List<ContactTest> contacts() {
        return List.of(
            new ContactTest("Jean Dupont", "jean.dupont@mail.com", List.of("Client", "VIP", "Projet")),
            new ContactTest("Marie Curie", "marie.curie@mail.com", List.of("Client", "VIP", "Projet"))
        );
    }

    /**
     * Renvoie les critères factices d'une liste de diffusion. Une nouvelle liste est
     * créée à chaque appel, car un {@link CritereData} peut être modifié.
     *
     * @return la liste des critères
     */
    public static List<CritereData> criteres() {
        List<CritereData> criteres = new ArrayList<>();
        criteres.add(new CritereData("Client", true));
        return criteres;
    }
}
