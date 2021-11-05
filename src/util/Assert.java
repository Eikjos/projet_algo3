package util;

public class Assert {

    /**
     * Méthode utilitaire simplifiant la vérification de pré-conditions. Si la
     * condition passée en paramètre est fausse, une AssertionError est levée
     * avec la description passée en paramètre.
     * @param condition Condition à vérifier.
     * @param description Description de l'erreur rencontrée.
     */
    public static void check(boolean condition, String description) {
        if (description == null) {
            throw new AssertionError("La chaine descriptive est null");
        }
        if (!condition) {
            throw new AssertionError(description);
        }
    }
}
