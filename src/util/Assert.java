package util;

public final class Assert {
    private Assert() {
    }

    /**
     * Méthode utilitaire simplifiant la vérification de pré-conditions. Si la
     * condition passée en paramètre est fausse, une AssertionError est levée
     * avec la description passée en paramètre.
     * @param condition Condition à vérifier.
     * @param description Description de l'erreur rencontrée.
     */
    public static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
    }

    /**
     * Surcharge de la méthode check(boolean, String) sans possibilité de
     * fournir un message descriptif de l'assertion échouée.
     * @param condition Condition à vérifier.
     */
    public static void check(boolean condition) {
        check(condition, null);
    }
}
