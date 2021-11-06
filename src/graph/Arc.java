package graph;

import util.Assert;

/**
 * Un arc dans un graphe orienté est une relation entre deux sommets. Soit x et
 * y deux sommets dans un graphe, on dit que x est en relation avec y s'il
 * existe un arc partant du sommet dénoté par x et pointant vers le sommet
 * dénoté par y.
 */
public class Arc implements Comparable<Arc> {

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

    /**
     * @param x Le sommet à comparer à cet arc.
     * @return true ou false selon si le sommet dénoté par x est impliqué dans
     * cet arc.
     */
    public boolean isImplied(Vertex x) {
        return getFrom().equals(x) || getTo().equals(x);
    }

    /**
     * @param o Un arc avec lequel comparer cet arc.
     * @return 0 si les deux arcs sont équivalents, une valeur strictement
     * positive ou négative sinon.
     */
    @Override
    public int compareTo(Arc o) {
        int d = 0;
        d += getFrom().compareTo(o.getFrom());
        if (d != 0) {
            return d;
        }
        d += getTo().compareTo(o.getTo());
        return d;
    }

    /**
     * @param obj Un objet quelconque à comparer avec cet arc.
     * @return true ou false selon si les deux objets représentent le même
     * arc.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Arc) {
            Arc x = (Arc) obj;
            return getFrom().equals(x.getFrom())
                    && getTo().equals(x.getTo());
        }
        return false;
    }

    /**
     * @return Un hashcode permettant d'identifier l'arc parmi d'autres.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash += 31 * hash + getFrom().hashCode() * -1;
        hash += 31 * hash + getTo().hashCode();
        return hash;
    }

    /**
     * @return Une représentation affichable sur console de cet arc.
     */
    @Override
    public String toString() {
        return "[Arc] \"" + getFrom().getName() + "\" -> \"" + getTo().getName() + "\"";
    }
}
