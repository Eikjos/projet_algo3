package social.accounts;

import graph.Vertex;
import util.Assert;

/**
 * Représente un utilisateur du réseaux social.
 */
public class User extends Vertex {

    // CONSTANTES

    private static final int AGE_MINIMUM = 16;

    // ATTRIBUTS

    /**
     * Représente le nom de famille de l'utilisateur.
     */
    private final String lastname;

    /**
     * Représente le prénom de l'utilisateur.
     */
    private final String firstname;

    /**
     * Représente l'âge de l'utilisateur.
     */
    private final int age;

    // CONSTRUCTEURS

    /**
     * Créer un utilisateur.
     * @pre
     *      age >= AGE_MINIMUM
     *      lastname ! null && firstname != null
     * @post
     *      getLastname() == lastname
     *      getFirstname() == firstname
     *      getAge() == age
     */
    public User(String lastname, String firstname, int age) {
        Assert.check(lastname != null, "lastname is null");
        Assert.check(firstname != null, "firstname is null");
        Assert.check(age >= AGE_MINIMUM, "age must be greater or equal at 16");
        this.lastname = lastname;
        this.firstname = firstname;
        this.age = age;
    }

    // REQUETES

    /**
     * Donne l'age de l'utilisateur.
     */
    public int getAge() {
        return age;
    }

    /**
     * Donne le nom de famille de l'utilisateur.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Donne le prénom de l'utilisateur.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Permte d'identifier un utilisateur selon son nom et son prénom.
     */
    public String getName() {
        return getLastname() + " " + getFirstname();
    }

    /**
     * Permet de sauvegarder l'utlisateur.
     */
    public String serialize() {
        return "U:" + getLastname() + ":" + getFirstname() + ":" + getAge();
    }

    @Override
    public String toString() {
        return getFirstname() + " " + getLastname() + " " + getAge() + " ans";
    }
}
