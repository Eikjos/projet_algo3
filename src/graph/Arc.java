package graph;

import util.Assert;

/**
 * Un arc dans un graphe orienté est une relation entre deux sommets. Soit x et
 * y deux sommets dans un graphe, on dit que x est en relation avec y s'il
 * existe un arc partant du sommet dénoté par x et pointant vers le sommet
 * dénoté par y.
 */
public class Arc {

    //- ATTRIBUTS

    /**
     * Le sommet de départ de cet arc.
     */
    private final Vertex from;

    /**
     * Le sommet d'arrivée de cet arc.
     */
    private final Vertex to;

    //- CONSTRUCTEURS

    /**
     * Un arc reliant le sommet dénoté par from et pointant vers le sommet
     * dénoté par to.
     * @param from Le somemt de départ de cet arc.
     * @param to Le sommet d'arrivée de cet arc.
     */
    public Arc(Vertex from, Vertex to) {
        Assert.check(from != null, "Le sommet de départ est null");
        Assert.check(to != null, "Le sommet d'arrivée est null");
        this.from = from;
        this.to = to;
    }

    //- REQUÊTES

    /**
     * @return Le sommet de départ de cet arc.
     */
    public Vertex getFrom() {
        return from;
    }

    /**
     * @return Le sommet d'arrivée de cet arc.
     */
    public Vertex getTo() {
        return to;
    }
}
