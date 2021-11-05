package graph.exceptions;

import graph.Vertex;

/**
 * Cette exception est levée lorsqu'un sommet a été utilisé dans un graphe dans
 * lequel il n'appartient pas.
 */
public class VertexNotFound extends Exception {

    //- ATTRIBUTS

    /**
     * Le sommet n'ayant pas été trouvé dans le graphe.
     */
    private final Vertex vertex;

    //- CONSTRUCTEURS

    /**
     * Une nouvelle exception VertexNotFound concernant le sommet dénoté par
     * vertex.
     * @param vertex Le sommet n'ayant pas pu être trouvé dans le graphe.
     */
    public VertexNotFound(Vertex vertex) {
        super("Le sommet \"" + vertex.getName() + "\" n'a pas pu être trouvé");
        this.vertex = vertex;
    }

    //- REQUÊTES

    /**
     * @return Le sommet n'ayant pas été trouvé dans le graphe.
     */
    public Vertex getVertex() {
        return vertex;
    }
}
