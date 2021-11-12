package social.accounts;

import graph.Vertex;
import util.Assert;

/**
 * Représente une page du réseau social.
 */
public class Page extends Vertex {

    // ATTRIBUTS

    /**
     * Le nom de la page.
     */
    private final String name;

    // CONSTRUCTEUR

    /**
     * @pre
     *      name != null
     * @post
     *      getName() == name
     */
    public Page(String name) {
        Assert.check(name != null, "name is null");
        this.name = name;
    }

    // REQUETES

    public String getName() {
        return name;
    }

    public String serialize() {
        return "P:" + getName();
    }

    @Override
    public String toString() {
        return getName();
    }
}
