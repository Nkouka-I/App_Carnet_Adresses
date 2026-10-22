package fr.iutfbleau.sae31.AtoZ.controleur;

import fr.iutfbleau.sae31.AtoZ.model.Contact;
import fr.iutfbleau.sae31.AtoZ.model.ContactDAO;
import fr.iutfbleau.sae31.AtoZ.model.Etiquette;
import fr.iutfbleau.sae31.AtoZ.model.EtiquetteDAO;
import fr.iutfbleau.sae31.AtoZ.model.ListeDiffusion;
import fr.iutfbleau.sae31.AtoZ.model.ListeDiffusionDAO;
import fr.iutfbleau.sae31.AtoZ.model.ModeleException;
import fr.iutfbleau.sae31.AtoZ.model.Carnet;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;

public class Controleur {

    private final EtiquetteDAO etiquetteDAO;
    private final ContactDAO contactDAO;
    private final ListeDiffusionDAO listeDAO;
    private final Carnet carnet;
    private final List<Runnable> ecouteurs = new ArrayList<>();
    private Consumer<String> gestionnaireErreurs = message ->
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);

    public Controleur(Connection cx) {
        etiquetteDAO = new EtiquetteDAO(cx);
        contactDAO = new ContactDAO(cx);
        listeDAO = new ListeDiffusionDAO(cx);
        carnet = new Carnet(etiquetteDAO, contactDAO, listeDAO);

        try {
            carnet.recharger();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    public List<EtiquetteVue> getEtiquettes() { return carnet.getEtiquettes(); }
    public List<ContactVue> getContacts() { return carnet.getContacts(); }
    
    public List<ContactVue> getContacts(EtiquetteVue filtre) {
        if (filtre == null) { return carnet.getContacts(); }
        List<ContactVue> resultat = new ArrayList<>();
        for (ContactVue contact : carnet.getContacts()) {
            if (contact.getEtiquettes().contains(filtre)) { resultat.add(contact); }
        }
        return resultat;
    }

    public List<ListeDiffusionVue> getListesDiffusion() { return carnet.getListes(); }

    public List<ListeDiffusionVue> getListesUtilisant(EtiquetteVue etiquette) {
        try {
            return carnet.listesUtilisant(etiquette);
        } catch (ModeleException e) {
            signaler(e);
            return new ArrayList<>();
        }
    }

    public List<ContactVue> getContactsDe(ListeDiffusionVue liste) {
        try {
            return carnet.membres(liste);
        } catch (ModeleException e) {
            signaler(e);
            return new ArrayList<>();
        }
    }

    public ContactVue creerContact(String nom, String email, List<EtiquetteVue> etiquettesDuContact) {
        try {
            Contact c = new Contact(nom, email);
            for (Etiquette e : carnet.modelesDe(etiquettesDuContact)) { c.ajouterEtiquette(e); }
            contactDAO.creer(c);
            carnet.recharger();
            notifier();
            return carnet.contactDeId(c.getId());
        } catch (ModeleException e) {
            signaler(e);
            return null;
        }
    }

    public void modifierContact(ContactVue contact, String nom, String email, List<EtiquetteVue> etiquettesDuContact) {
        try {
            Contact c = new Contact(carnet.modeleDe(contact).getId(), nom, email);
            for (Etiquette e : carnet.modelesDe(etiquettesDuContact)) { c.ajouterEtiquette(e); }
            contactDAO.modifier(c);
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    public void supprimerContact(ContactVue contact) {
        try {
            contactDAO.supprimer(carnet.modeleDe(contact).getId());
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    public EtiquetteVue creerEtiquette(String libelle) {
        try {
            Etiquette e = new Etiquette(libelle);
            etiquetteDAO.creer(e);
            carnet.recharger();
            notifier();
            return carnet.etiquetteDeId(e.getId());
        } catch (ModeleException e) {
            signaler(e);
            return null;
        }
    }

    public void modifierEtiquette(EtiquetteVue etiquette, String libelle) {
        try {
            etiquetteDAO.modifier(new Etiquette(carnet.modeleDe(etiquette).getId(), libelle));
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    public boolean supprimerEtiquette(EtiquetteVue etiquette) {
        try {
            ListeDiffusionVue concernee = carnet.listeAvecSeulCritere(etiquette);
            if (concernee != null) {
                signaler("Impossible de supprimer l'étiquette : elle est le seul critère de la liste " + concernee.getNom());
                return false;
            }
            etiquetteDAO.supprimer(carnet.modeleDe(etiquette).getId());
            carnet.recharger();
            notifier();
            return true;
        } catch (ModeleException e) {
            signaler(e);
            return false;
        }
    }

    public ListeDiffusionVue creerListeDiffusion(String nom, List<CritereVue> criteres) {
        try {
            ListeDiffusion l = new ListeDiffusion(nom);
            l.setCriteres(carnet.modelesDeCriteres(criteres));
            listeDAO.creer(l);
            carnet.recharger();
            notifier();
            return carnet.listeDeId(l.getId());
        } catch (ModeleException e) {
            signaler(e);
            return null;
        }
    }

    public void modifierListeDiffusion(ListeDiffusionVue liste, String nom, List<CritereVue> criteres) {
        try {
            ListeDiffusion l = new ListeDiffusion(carnet.modeleDe(liste).getId(), nom);
            l.setCriteres(carnet.modelesDeCriteres(criteres));
            listeDAO.modifier(l);
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    public void supprimerListeDiffusion(ListeDiffusionVue liste) {
        try {
            listeDAO.supprimer(carnet.modeleDe(liste).getId());
            carnet.recharger();
            notifier();
        } catch (ModeleException e) {
            signaler(e);
        }
    }

    public void ajouterEcouteur(Runnable ecouteur) { ecouteurs.add(ecouteur); }
    public void setGestionnaireErreurs(Consumer<String> gestionnaire) { this.gestionnaireErreurs = gestionnaire; }
    private void notifier() { for (Runnable ecouteur : ecouteurs) { ecouteur.run(); } }
    private void signaler(ModeleException e) { signaler(e.getMessage()); }
    private void signaler(String message) { gestionnaireErreurs.accept(message); }
}