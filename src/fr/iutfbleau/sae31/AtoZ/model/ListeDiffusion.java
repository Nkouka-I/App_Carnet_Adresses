package fr.iutfbleau.sae31.AtoZ.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Liste de diffusion : un nom unique et des critères sur les étiquettes.
 * Ses membres ne sont pas stockés, ils sont calculés dynamiquement
 * (voir {@link ListeDiffusionDAO#membres(ListeDiffusion)}).
 */
public class ListeDiffusion {

    /** Longueur maximale du nom, en caractères. */
    public static final int NOM_MAX = 50;

    private int id;
    private String nom;
    private final List<Critere> criteres = new ArrayList<>();

    /**
     * Crée une liste pas encore enregistrée (identifiant 0).
     *
     * @param nom nom de la liste
     * @throws ModeleException si le nom est invalide
     */
    public ListeDiffusion(String nom) throws ModeleException {
        this(0, nom);
    }

    /**
     * Crée une liste avec son identifiant en base.
     *
     * @param id  identifiant
     * @param nom nom de la liste
     * @throws ModeleException si le nom est invalide
     */
    public ListeDiffusion(int id, String nom) throws ModeleException {
        this.id = id;
        setNom(nom);
    }

    /** @return l'identifiant (0 si pas encore enregistrée) */
    public int getId() {
        return id;
    }

    /** @param id nouvel identifiant */
    public void setId(int id) {
        this.id = id;
    }

    /** @return le nom */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom (non vide, 50 caractères maximum).
     *
     * @param nom nouveau nom
     * @throws ModeleException si le nom est invalide
     */
    public void setNom(String nom) throws ModeleException {
        if (nom == null || nom.trim().isEmpty()) {
            throw new ModeleException("Le nom de la liste ne doit pas être vide.");
        }
        if (nom.length() > NOM_MAX) {
            throw new ModeleException("Le nom de la liste ne doit pas dépasser "
                + NOM_MAX + " caractères.");
        }
        this.nom = nom.trim();
    }

    /** @return la liste (non modifiable) des critères */
    public List<Critere> getCriteres() {
        return Collections.unmodifiableList(criteres);
    }

    /**
     * Ajoute un critère.
     *
     * @param c critère à ajouter
     */
    public void ajouterCritere(Critere c) {
        criteres.add(c);
    }

    /**
     * Remplace tous les critères.
     *
     * @param nouveaux nouveaux critères
     */
    public void setCriteres(List<Critere> nouveaux) {
        criteres.clear();
        criteres.addAll(nouveaux);
    }

    @Override
    public String toString() {
        return nom;
    }
}