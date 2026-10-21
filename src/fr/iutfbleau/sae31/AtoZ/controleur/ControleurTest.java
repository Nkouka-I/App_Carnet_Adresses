package fr.iutfbleau.sae31.AtoZ.controleur;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe <code>ControleurTest</code> est un contrôleur provisoire qui garde toutes les
 * données en mémoire. Il permet de faire fonctionner la vue avant que le vrai modèle
 * existe. Ses classes internes implémentent les interfaces <code>*Vue</code> ; elles
 * seront remplacées par les classes du vrai modèle. Rien n'est sauvegardé.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public class ControleurTest implements Controleur {

    /**
     * Contact de test.
     */
    private static class ContactTest implements ContactVue {
        private String nom;
        private String email;
        private List<EtiquetteVue> etiquettes;

        ContactTest(String nom, String email, List<EtiquetteVue> etiquettes) {
            this.nom = nom;
            this.email = email;
            this.etiquettes = new ArrayList<>(etiquettes);
        }

        @Override public String getNom() { return nom; }
        @Override public String getEmail() { return email; }
        @Override public List<EtiquetteVue> getEtiquettes() { return new ArrayList<>(etiquettes); }
    }

    /**
     * Étiquette de test.
     */
    private static class EtiquetteTest implements EtiquetteVue {
        private String nom;

        EtiquetteTest(String nom) { this.nom = nom; }

        @Override public String getNom() { return nom; }
    }

    /**
     * Critère de test.
     */
    private static class CritereTest implements CritereVue {
        private final EtiquetteVue etiquette;
        private final boolean inclus;

        CritereTest(EtiquetteVue etiquette, boolean inclus) {
            this.etiquette = etiquette;
            this.inclus = inclus;
        }

        @Override public EtiquetteVue getEtiquette() { return etiquette; }
        @Override public boolean isInclus() { return inclus; }
    }

    /**
     * Liste de diffusion de test.
     */
    private static class ListeTest implements ListeDiffusionVue {
        private String nom;
        private List<CritereVue> criteres;

        ListeTest(String nom, List<CritereVue> criteres) {
            this.nom = nom;
            this.criteres = new ArrayList<>(criteres);
        }

        @Override public String getNom() { return nom; }
        @Override public List<CritereVue> getCriteres() { return new ArrayList<>(criteres); }
    }

    private final List<ContactTest> contacts = new ArrayList<>();
    private final List<EtiquetteTest> etiquettes = new ArrayList<>();
    private final List<ListeTest> listes = new ArrayList<>();
    private final List<CarnetListener> ecouteurs = new ArrayList<>();

    /**
     * Construit le contrôleur avec quelques données d'exemple.
     */
    public ControleurTest() {
        EtiquetteTest client = new EtiquetteTest("Client");
        EtiquetteTest famille = new EtiquetteTest("Famille");
        EtiquetteTest travail = new EtiquetteTest("Travail");
        EtiquetteTest spam = new EtiquetteTest("Spam");
        etiquettes.addAll(List.of(client, famille, travail, spam));

        contacts.add(new ContactTest("Alice Martin", "alice.martin@mail.fr", List.of(client, travail)));
        contacts.add(new ContactTest("Bob Durand", "bob.durand@mail.fr", List.of(famille)));
        contacts.add(new ContactTest("Chloé Petit", "chloe.petit@mail.fr", List.of(client, spam)));
        contacts.add(new ContactTest("David Leroy", "david.leroy@mail.fr", List.of()));

        listes.add(new ListeTest("Clients sans spam",
            List.of(new CritereTest(client, true), new CritereTest(spam, false))));
        listes.add(new ListeTest("Proches", List.of(new CritereTest(famille, true))));
    }

    private void notifier() {
        for (CarnetListener e : new ArrayList<>(ecouteurs)) {
            e.carnetModifie();
        }
    }

    private boolean accepte(ListeTest liste, ContactTest contact) {
        for (CritereVue c : liste.criteres) {
            if (contact.etiquettes.contains(c.getEtiquette()) != c.isInclus()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ContactVue> getContacts() { return new ArrayList<>(contacts); }

    @Override
    public List<ContactVue> getContacts(EtiquetteVue etiquette) {
        if (etiquette == null) {
            return getContacts();
        }
        List<ContactVue> resultat = new ArrayList<>();
        for (ContactTest c : contacts) {
            if (c.etiquettes.contains(etiquette)) {
                resultat.add(c);
            }
        }
        return resultat;
    }

    @Override
    public List<ContactVue> getContactsDe(ListeDiffusionVue liste) {
        List<ContactVue> resultat = new ArrayList<>();
        for (ContactTest c : contacts) {
            if (accepte((ListeTest) liste, c)) {
                resultat.add(c);
            }
        }
        return resultat;
    }

    @Override
    public List<EtiquetteVue> getEtiquettes() { return new ArrayList<>(etiquettes); }

    @Override
    public List<ListeDiffusionVue> getListesDiffusion() { return new ArrayList<>(listes); }

    @Override
    public List<ListeDiffusionVue> getListesUtilisant(EtiquetteVue etiquette) {
        List<ListeDiffusionVue> resultat = new ArrayList<>();
        for (ListeTest l : listes) {
            for (CritereVue c : l.criteres) {
                if (c.getEtiquette() == etiquette) {
                    resultat.add(l);
                    break;
                }
            }
        }
        return resultat;
    }

    @Override
    public ContactVue creerContact(String nom, String email, List<EtiquetteVue> etiquettes) {
        ContactTest c = new ContactTest(nom, email, etiquettes);
        contacts.add(c);
        notifier();
        return c;
    }

    @Override
    public void modifierContact(ContactVue contact, String nom, String email, List<EtiquetteVue> etiquettes) {
        ContactTest c = (ContactTest) contact;
        c.nom = nom;
        c.email = email;
        c.etiquettes = new ArrayList<>(etiquettes);
        notifier();
    }

    @Override
    public void supprimerContact(ContactVue contact) {
        contacts.remove(contact);
        notifier();
    }

    @Override
    public EtiquetteVue creerEtiquette(String nom) {
        EtiquetteTest e = new EtiquetteTest(nom);
        etiquettes.add(e);
        notifier();
        return e;
    }

    @Override
    public void modifierEtiquette(EtiquetteVue etiquette, String nom) {
        ((EtiquetteTest) etiquette).nom = nom;
        notifier();
    }

    @Override
    public void supprimerEtiquette(EtiquetteVue etiquette) {
        etiquettes.remove(etiquette);
        for (ContactTest c : contacts) {
            c.etiquettes.remove(etiquette);
        }
        for (ListeTest l : listes) {
            l.criteres.removeIf(c -> c.getEtiquette() == etiquette);
        }
        notifier();
    }

    @Override
    public ListeDiffusionVue creerListeDiffusion(String nom, List<CritereVue> criteres) {
        ListeTest l = new ListeTest(nom, copier(criteres));
        listes.add(l);
        notifier();
        return l;
    }

    @Override
    public void modifierListeDiffusion(ListeDiffusionVue liste, String nom, List<CritereVue> criteres) {
        ListeTest l = (ListeTest) liste;
        l.nom = nom;
        l.criteres = copier(criteres);
        notifier();
    }

    @Override
    public void supprimerListeDiffusion(ListeDiffusionVue liste) {
        listes.remove(liste);
        notifier();
    }

    @Override
    public void ajouterEcouteur(CarnetListener ecouteur) { ecouteurs.add(ecouteur); }

    /**
     * Recopie des critères reçus de la vue en critères propres au contrôleur.
     */
    private List<CritereVue> copier(List<CritereVue> criteres) {
        List<CritereVue> copie = new ArrayList<>();
        for (CritereVue c : criteres) {
            copie.add(new CritereTest(c.getEtiquette(), c.isInclus()));
        }
        return copie;
    }
}
