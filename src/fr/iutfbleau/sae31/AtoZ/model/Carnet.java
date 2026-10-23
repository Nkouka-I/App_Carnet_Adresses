package fr.iutfbleau.sae31.AtoZ.model;

import fr.iutfbleau.sae31.AtoZ.controleur.ContactVue;
import fr.iutfbleau.sae31.AtoZ.controleur.EtiquetteVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;
import fr.iutfbleau.sae31.AtoZ.controleur.CritereVue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * La classe <code>Carnet</code> gère la copie en mémoire des données du carnet
 * (contacts, étiquettes, listes de diffusion) lues depuis la base de données.
 */
public class Carnet {

    private final EtiquetteDAO etiquetteDAO;
    private final ContactDAO contactDAO;
    private final ListeDiffusionDAO listeDAO;

    private List<Etiquette> etiquettes = new ArrayList<>();
    private List<Contact> contacts = new ArrayList<>();
    private List<ListeDiffusion> listes = new ArrayList<>();

    public Carnet(EtiquetteDAO etiquetteDAO, ContactDAO contactDAO, ListeDiffusionDAO listeDAO) {
        this.etiquetteDAO = etiquetteDAO;
        this.contactDAO = contactDAO;
        this.listeDAO = listeDAO;
    }

    /**
     * Recharge toutes les données depuis la base de données.
     */
    public void recharger() throws ModeleException {
        try {
            this.etiquettes = new ArrayList<>(etiquetteDAO.trouverToutes());
            this.contacts = new ArrayList<>(contactDAO.trouverTous());
            this.listes = new ArrayList<>(listeDAO.trouverToutes());
        } catch (Exception e) {
            throw new ModeleException("Erreur lors du rechargement du carnet : " + e.getMessage());
        }
    }

    public List<EtiquetteVue> getEtiquettes() {
        List<EtiquetteVue> vues = new ArrayList<>(etiquettes);
        return Collections.unmodifiableList(vues);
    }

    public List<ContactVue> getContacts() {
        List<ContactVue> vues = new ArrayList<>(contacts);
        return Collections.unmodifiableList(vues);
    }

    public List<ListeDiffusionVue> getListes() {
        List<ListeDiffusionVue> vues = new ArrayList<>(listes);
        return Collections.unmodifiableList(vues);
    }

    public ContactVue contactDeId(int id) {
        for (Contact c : contacts) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public EtiquetteVue etiquetteDeId(int id) {
        for (Etiquette e : etiquettes) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public ListeDiffusionVue listeDeId(int id) {
        for (ListeDiffusion l : listes) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    public Contact modeleDe(ContactVue vue) {
        if (vue == null) {
            return null;
        }
        if (vue instanceof Contact) {
            return (Contact) vue;
        }
        for (Contact c : contacts) {
            if (c.getNom().equals(vue.getNom()) && c.getEmail().equals(vue.getEmail())) {
                return c;
            }
        }
        return null;
    }

    public Etiquette modeleDe(EtiquetteVue vue) {
        if (vue instanceof Etiquette) {
            return (Etiquette) vue;
        }
        for (Etiquette e : etiquettes) {
            if (e.getLibelle().equals(vue.getNom())) {
                return e;
            }
        }
        return null;
    }

    public ListeDiffusion modeleDe(ListeDiffusionVue vue) {
        if (vue instanceof ListeDiffusion) {
            return (ListeDiffusion) vue;
        }
        for (ListeDiffusion l : listes) {
            if (l.getNom().equals(vue.getNom())) {
                return l;
            }
        }
        return null;
    }

    public List<Etiquette> modelesDe(List<EtiquetteVue> vues) {
        List<Etiquette> result = new ArrayList<>();
        if (vues != null) {
            for (EtiquetteVue v : vues) {
                Etiquette e = modeleDe(v);
                if (e != null) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    public List<Critere> modelesDeCriteres(List<CritereVue> criteresVue) {
        List<Critere> result = new ArrayList<>();
        if (criteresVue != null) {
            for (CritereVue cv : criteresVue) {
                Etiquette e = modeleDe(cv.getEtiquette());
                if (e != null) {
                    result.add(new Critere(e, cv.isInclus()));
                }
            }
        }
        return result;
    }

    public List<ContactVue> membres(ListeDiffusionVue listeVue) throws ModeleException {
        ListeDiffusion liste = modeleDe(listeVue);
        List<ContactVue> membres = new ArrayList<>();
        if (liste != null) {
            List<Contact> resultatsDAO = listeDAO.membres(liste);
            for (Contact c : resultatsDAO) {
                membres.add(c);
            }
        }
        return membres;
    }

    public List<ListeDiffusionVue> listesUtilisant(EtiquetteVue etiquetteVue) throws ModeleException {
        Etiquette etiquette = modeleDe(etiquetteVue);
        List<ListeDiffusionVue> result = new ArrayList<>();
        if (etiquette != null) {
            for (ListeDiffusion l : listes) {
                for (Critere c : l.getCritere()) {
                    if (c.getEtiquette().getId() == etiquette.getId()) {
                        result.add(l);
                        break;
                    }
                }
            }
        }
        return result;
    }

    public ListeDiffusionVue listeAvecSeulCritere(EtiquetteVue etiquetteVue) {
        Etiquette etiquette = modeleDe(etiquetteVue);
        if (etiquette != null) {
            for (ListeDiffusion l : listes) {
                List<Critere> criteres = l.getCritere();
                if (criteres.size() == 1 && criteres.get(0).getEtiquette().getId() == etiquette.getId()) {
                    return l;
                }
            }
        }
        return null;
    }

    /**
     * Renvoie les contacts qui possèdent une étiquette. Les étiquettes sont comparées par leur
     * identifiant (celui de la table <code>Contact_etiquette</code>), car les objets sont
     * recréés à chaque rechargement depuis la base.
     *
     * @param vue l'étiquette recherchée
     * @return les contacts ayant cette étiquette (liste vide si l'étiquette est inconnue)
     */
    public List<ContactVue> contactsAvecEtiquette(EtiquetteVue vue) {
        List<ContactVue> result = new ArrayList<>();
        Etiquette cible = (vue == null) ? null : modeleDe(vue);
        if (cible == null) {
            return result;
        }
        for (Contact c : contacts) {
            for (Etiquette e : c.getEtiquette()) {
                if (e.getId() == cible.getId()) {
                    result.add(c);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Indique si deux vues désignent la même étiquette (même identifiant en base).
     */
    public boolean memeEtiquette(EtiquetteVue a, EtiquetteVue b) {
        if (a == null || b == null) {
            return a == b;
        }
        Etiquette ea = modeleDe(a);
        Etiquette eb = modeleDe(b);
        return ea != null && eb != null && (ea == eb || (ea.getId() != 0 && ea.getId() == eb.getId()));
    }

    /**
     * Indique si deux vues désignent le même contact (même identifiant en base).
     */
    public boolean memeContact(ContactVue a, ContactVue b) {
        if (a == null || b == null) {
            return a == b;
        }
        Contact ca = modeleDe(a);
        Contact cb = modeleDe(b);
        return ca != null && cb != null && (ca == cb || (ca.getId() != 0 && ca.getId() == cb.getId()));
    }

    /**
     * Indique si deux vues désignent la même liste de diffusion (même identifiant en base).
     */
    public boolean memeListe(ListeDiffusionVue a, ListeDiffusionVue b) {
        if (a == null || b == null) {
            return a == b;
        }
        ListeDiffusion la = modeleDe(a);
        ListeDiffusion lb = modeleDe(b);
        return la != null && lb != null && (la == lb || (la.getId() != 0 && la.getId() == lb.getId()));
    }

    /**
     * Renvoie la version actuelle (après rechargement) d'une étiquette, ou <code>null</code>
     * si elle n'existe plus.
     */
    public EtiquetteVue etiquetteActuelle(EtiquetteVue vue) {
        Etiquette e = (vue == null) ? null : modeleDe(vue);
        return (e == null) ? null : etiquetteDeId(e.getId());
    }

    /**
     * Renvoie la version actuelle (après rechargement) d'un contact, ou <code>null</code>
     * s'il n'existe plus.
     */
    public ContactVue contactActuel(ContactVue vue) {
        Contact c = modeleDe(vue);
        return (c == null) ? null : contactDeId(c.getId());
    }

    /**
     * Renvoie la version actuelle (après rechargement) d'une liste, ou <code>null</code>
     * si elle n'existe plus.
     */
    public ListeDiffusionVue listeActuelle(ListeDiffusionVue vue) {
        ListeDiffusion l = (vue == null) ? null : modeleDe(vue);
        return (l == null) ? null : listeDeId(l.getId());
    }
}
