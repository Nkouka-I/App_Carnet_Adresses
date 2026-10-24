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
 * Comme les objets sont recréés à chaque rechargement, les comparaisons entre deux objets
 * se font à partir de leur identifiant en base de données.
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class Carnet {

    /**
     * Accès aux étiquettes dans la base de données.
     */
    private final EtiquetteDAO etiquetteDAO;

    /**
     * Accès aux contacts dans la base de données.
     */
    private final ContactDAO contactDAO;

    /**
     * Accès aux listes de diffusion dans la base de données.
     */
    private final ListeDiffusionDAO listeDAO;

    /**
     * Les étiquettes du carnet, telles que lues lors du dernier rechargement.
     */
    private List<Etiquette> etiquettes = new ArrayList<>();

    /**
     * Les contacts du carnet, tels que lus lors du dernier rechargement.
     */
    private List<Contact> contacts = new ArrayList<>();

    /**
     * Les listes de diffusion du carnet, telles que lues lors du dernier rechargement.
     */
    private List<ListeDiffusion> listes = new ArrayList<>();

    /**
     * Constructeur d'un carnet vide : il faut appeler <code>recharger</code> pour le remplir.
     *
     * @param etiquetteDAO l'accès aux étiquettes
     * @param contactDAO l'accès aux contacts
     * @param listeDAO l'accès aux listes de diffusion
     */
    public Carnet(EtiquetteDAO etiquetteDAO, ContactDAO contactDAO, ListeDiffusionDAO listeDAO) {
        this.etiquetteDAO = etiquetteDAO;
        this.contactDAO = contactDAO;
        this.listeDAO = listeDAO;
    }

    /**
     * Recharge toutes les données depuis la base de données.
     *
     * @throws ModeleException si la lecture de la base de données échoue
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

    /**
     * Renvoie toutes les étiquettes du carnet.
     *
     * @return la liste des étiquettes (non modifiable)
     */
    public List<EtiquetteVue> getEtiquettes() {
        List<EtiquetteVue> vues = new ArrayList<>(etiquettes);
        return Collections.unmodifiableList(vues);
    }

    /**
     * Renvoie tous les contacts du carnet.
     *
     * @return la liste des contacts (non modifiable)
     */
    public List<ContactVue> getContacts() {
        List<ContactVue> vues = new ArrayList<>(contacts);
        return Collections.unmodifiableList(vues);
    }

    /**
     * Renvoie toutes les listes de diffusion du carnet.
     *
     * @return la liste des listes de diffusion (non modifiable)
     */
    public List<ListeDiffusionVue> getListes() {
        List<ListeDiffusionVue> vues = new ArrayList<>(listes);
        return Collections.unmodifiableList(vues);
    }

    /**
     * Renvoie le contact qui a un identifiant donné.
     *
     * @param id l'identifiant du contact
     * @return le contact, ou <code>null</code> s'il n'existe pas
     */
    public ContactVue contactDeId(int id) {
        for (Contact c : contacts) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    /**
     * Renvoie l'étiquette qui a un identifiant donné.
     *
     * @param id l'identifiant de l'étiquette
     * @return l'étiquette, ou <code>null</code> si elle n'existe pas
     */
    public EtiquetteVue etiquetteDeId(int id) {
        for (Etiquette e : etiquettes) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    /**
     * Renvoie la liste de diffusion qui a un identifiant donné.
     *
     * @param id l'identifiant de la liste
     * @return la liste, ou <code>null</code> si elle n'existe pas
     */
    public ListeDiffusionVue listeDeId(int id) {
        for (ListeDiffusion l : listes) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    /**
     * Renvoie l'objet du modèle qui correspond à la vue d'un contact. Si la vue n'est pas
     * déjà un contact, il est recherché d'après son nom et son adresse mail.
     *
     * @param vue la vue du contact
     * @return le contact du modèle, ou <code>null</code> s'il n'est pas trouvé
     */
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

    /**
     * Renvoie l'objet du modèle qui correspond à la vue d'une étiquette. Si la vue n'est pas
     * déjà une étiquette, elle est recherchée d'après son libellé.
     *
     * @param vue la vue de l'étiquette (non <code>null</code>)
     * @return l'étiquette du modèle, ou <code>null</code> si elle n'est pas trouvée
     */
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

    /**
     * Renvoie l'objet du modèle qui correspond à la vue d'une liste de diffusion. Si la vue
     * n'est pas déjà une liste, elle est recherchée d'après son nom.
     *
     * @param vue la vue de la liste (non <code>null</code>)
     * @return la liste du modèle, ou <code>null</code> si elle n'est pas trouvée
     */
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

    /**
     * Convertit des vues d'étiquettes en étiquettes du modèle. Les étiquettes introuvables
     * sont ignorées.
     *
     * @param vues les vues des étiquettes (peut être <code>null</code>)
     * @return les étiquettes du modèle correspondantes
     */
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

    /**
     * Convertit des vues de critères en critères du modèle. Les critères dont l'étiquette est
     * introuvable sont ignorés.
     *
     * @param criteresVue les vues des critères (peut être <code>null</code>)
     * @return les critères du modèle correspondants
     */
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

    /**
     * Renvoie les contacts membres d'une liste de diffusion, c'est-à-dire ceux qui respectent
     * ses critères. Ils sont lus dans la base de données.
     *
     * @param listeVue la liste de diffusion
     * @return les contacts membres de la liste (vide si la liste est introuvable)
     * @throws ModeleException si la lecture de la base de données échoue
     */
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

    /**
     * Renvoie les listes de diffusion qui utilisent une étiquette dans leurs critères.
     *
     * @param etiquetteVue l'étiquette concernée
     * @return les listes qui utilisent cette étiquette
     * @throws ModeleException en cas d'erreur du modèle
     */
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

    /**
     * Renvoie une liste de diffusion dont l'étiquette donnée est le seul critère.
     *
     * @param etiquetteVue l'étiquette concernée
     * @return la liste trouvée, ou <code>null</code> si aucune liste n'est dans ce cas
     */
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
     *
     * @param a la première étiquette
     * @param b la seconde étiquette
     * @return <code>true</code> si elles désignent la même étiquette
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
     *
     * @param a le premier contact
     * @param b le second contact
     * @return <code>true</code> s'ils désignent le même contact
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
     *
     * @param a la première liste
     * @param b la seconde liste
     * @return <code>true</code> si elles désignent la même liste
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
     * Renvoie la version actuelle (après rechargement) d'une étiquette.
     *
     * @param vue l'étiquette (éventuellement périmée)
     * @return l'étiquette à jour, ou <code>null</code> si elle n'existe plus
     */
    public EtiquetteVue etiquetteActuelle(EtiquetteVue vue) {
        Etiquette e = (vue == null) ? null : modeleDe(vue);
        return (e == null) ? null : etiquetteDeId(e.getId());
    }

    /**
     * Renvoie la version actuelle (après rechargement) d'un contact.
     *
     * @param vue le contact (éventuellement périmé)
     * @return le contact à jour, ou <code>null</code> s'il n'existe plus
     */
    public ContactVue contactActuel(ContactVue vue) {
        Contact c = modeleDe(vue);
        return (c == null) ? null : contactDeId(c.getId());
    }

    /**
     * Renvoie la version actuelle (après rechargement) d'une liste de diffusion.
     *
     * @param vue la liste (éventuellement périmée)
     * @return la liste à jour, ou <code>null</code> si elle n'existe plus
     */
    public ListeDiffusionVue listeActuelle(ListeDiffusionVue vue) {
        ListeDiffusion l = (vue == null) ? null : modeleDe(vue);
        return (l == null) ? null : listeDeId(l.getId());
    }

}