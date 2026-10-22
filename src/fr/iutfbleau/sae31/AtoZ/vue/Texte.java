package fr.iutfbleau.sae31.AtoZ.vue;

/**
 * La classe <code>Texte</code> regroupe un petit outil de comparaison de textes : la mise
 * en minuscules sans accents. Elle sert à la recherche et au tri des contacts, pour que
 * « Chloé », « chloe » et « CHLOÉ » soient considérés comme identiques.
 *
 * @version 1.0
 * @author Nkouka Ildevert
 */
public final class Texte {

    /**
     * Lettres accentuées (en minuscules) que l'on sait convertir.
     */
    private static final String ACCENTUEES = "àáâãäåçèéêëìíîïñòóôõöùúûüýÿ";

    /**
     * Lettre sans accent correspondant, position par position, à {@link #ACCENTUEES}.
     */
    private static final String SANS_ACCENT = "aaaaaaceeeeiiiinooooouuuuyy";

    /**
     * Constructeur privé : la classe n'est pas instanciable.
     */
    private Texte() { }

    /**
     * Met un texte en minuscules et remplace les lettres accentuées par leur version
     * sans accent (« œ » devient « oe », « æ » devient « ae » et « ß » devient « ss »).
     *
     * @param texte le texte à convertir
     * @return le texte en minuscules, sans accents
     */
    public static String sansAccents(String texte) {
        String minuscules = texte.toLowerCase();
        StringBuilder resultat = new StringBuilder();

        for (int i = 0; i < minuscules.length(); i++) {
            char c = minuscules.charAt(i);
            int position = ACCENTUEES.indexOf(c);

            if (position >= 0) {
                resultat.append(SANS_ACCENT.charAt(position));
            } else if (c == 'œ') {
                resultat.append("oe");
            } else if (c == 'æ') {
                resultat.append("ae");
            } else if (c == 'ß') {
                resultat.append("ss");
            } else {
                resultat.append(c);
            }
        }
        return resultat.toString();
    }
}
