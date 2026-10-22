package fr.iutfbleau.sae31.AtoZ.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.iutfbleau.sae31.AtoZ.controleur.CritereVue;
import fr.iutfbleau.sae31.AtoZ.controleur.ListeDiffusionVue;

/**
 * La classe <code>ListeDiffusion</code> représente une liste de diffusion : un nom unique et des
 * critères portant sur les étiquettes. Ses membres ne sont pas stockés, ils sont calculés
 * dynamiquement (voir {@link ListeDiffusionDAO#membres(ListeDiffusion)}).
 *
 * @version 1.0
 * @author Tiago Alves--Le Du
 */
public class ListeDiffusion implements ListeDiffusionVue {

    /**
     * Longueur maximale du nom d'une liste, exprimée en caractères.
     */
    public static final int NOM_MAX = 50;

    /**
     * Identifiant de la liste en base de données (0 tant qu'elle n'est pas enregistrée).
     */
    private int id;
    /**
     * Nom de la liste (unique).
     */
    private String nom;
    /**
     * Critères que doit satisfaire un contact pour faire partie de la liste.
     */
    private final List<Critere> criteres = new ArrayList<>();

    /**
     * Constructeur destiné à la création d'une nouvelle liste, pas encore enregistrée en base :
     * son identifiant vaut 0 et sera attribué par la base de données.
     *
     * @param nom le nom de la liste
     * @throws ModeleException si le nom est invalide
     */
    public ListeDiffusion(String nom) throws ModeleException {
        this(0, nom);
    }

    /**
     * Constructeur destiné à la création d'une liste dont l'identifiant en base est connu.
     * Le nom est vérifié lors de la création.
     *
     * @param id l'identifiant de la liste
     * @param nom le nom de la liste
     * @throws ModeleException si le nom est invalide
     */
    public ListeDiffusion(int id, String nom) throws ModeleException {
        this.id = id;
        setNom(nom);
    }

    /**
     * Renvoie l'identifiant de la liste.
     *
     * @return l'identifiant de la liste (0 si elle n'est pas encore enregistrée)
     */
    public int getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la liste. Cette méthode est appelée après l'enregistrement en base,
     * lorsque l'identifiant généré est connu.
     *
     * @param id le nouvel identifiant
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Renvoie le nom de la liste.
     *
     * @return le nom de la liste
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la liste. Le nom ne doit pas être vide et ne doit pas dépasser
     * {@link #NOM_MAX} caractères. Les espaces de début et de fin sont retirés.
     *
     * @param nom le nouveau nom
     * @throws ModeleException si le nom ne respecte pas ces règles
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

    /**
     * Renvoie la liste des critères. La liste renvoyée n'est pas modifiable : pour changer les
     * critères, il faut utiliser les méthodes de la classe.
     *
     * @return la liste (non modifiable) des critères
     */
    public List<Critere> getCritere() {
        return Collections.unmodifiableList(this.criteres);
    }
    @Override
    public List<CritereVue> getCriteres(){
        List<CritereVue> vues = new ArrayList<>();
        for (Critere c : criteres) {
            vues.add((CritereVue) (Object) c);
        }
        return Collections.unmodifiableList(vues);
    }


    /**
     * Ajoute un critère à la liste.
     *
     * @param c le critère à ajouter
     */
    public void ajouterCritere(Critere c) {
        criteres.add(c);
    }

    /**
     * Remplace tous les critères de la liste par ceux de la liste donnée.
     *
     * @param nouveaux les nouveaux critères
     */
    public void setCriteres(List<Critere> nouveaux) {
        criteres.clear();
        criteres.addAll(nouveaux);
    }

    /**
     * Renvoie une représentation textuelle de la liste, c'est-à-dire son nom.
     *
     * @return le nom de la liste
     */
    @Override
    public String toString() {
        return nom;
    }
}