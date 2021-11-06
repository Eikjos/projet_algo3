package graph;

/**
 * Le sommet d'un graphe représente un élément contenu par ce dernier. Celui-ci
 * est identifiable par un nom, récupérable via la méthode getName() et on
 * considère ce nom unique dans l'entierté du graphe. Il ne peut donc pas y
 * avoir deux sommets identifiables par un même nom au sein d'un même graphe.
 * Un sommet peut également contenir des métadonnées supplémentaires en
 * fonction de l'implémentation qui en est réalisée.
 */
abstract public class Vertex implements Comparable<Vertex> {

    //- REQUÊTES

    /**
     * @return Le nom de ce sommet et par lequel il est identifiable dans un
     * graphe.
     */
    public abstract String getName();

    /**
     * @param o Un sommet avec lequel comparer ce sommet.
     * @return 0 si les deux sommets sont équivalents, une valeur strictement
     * positive ou négative sinon.
     */
    @Override
    public int compareTo(Vertex o) {
        return getName().compareTo(o.getName());
    }

    /**
     * @param obj Un objet quelconque à comparer avec ce sommet.
     * @return true ou false selon si les deux objets représentent le même
     * sommet.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vertex) {
            Vertex x = (Vertex) obj;
            return getName().equals(x.getName());
        }
        return false;
    }

    /**
     * @return Un hashcode permettant d'identifier le sommet parmi d'autres.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash += 31 * hash + getName().hashCode();
        return hash;
    }

    /**
     * @return Une représentation affichable sur console de ce sommet.
     */
    @Override
    public String toString() {
        return "[Vertex] " + getName();
    }
}
