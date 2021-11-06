package graph.exceptions;

import graph.Vertex;

/**
 * Cette exception est levée lorsqu'un sommet x tente d'être ajouté dans un
 * graphe alors qu'il existe déjà dans ce dernier.
 */
public class DuplicateVertex extends Exception {

    //- ATTRIBUTS

    /**
     * Le sommet existant déjà dans le graphe.
     */
    private final Vertex vertex;

    //- CONSTRUCTEURS

    /**
     * Une nouvelle exception DuplicateArc concernant l'arc dénoté par arc.
     * @param vertex Le sommet qui existe déjà dans le graphe.
     */
    public DuplicateVertex(Vertex vertex) {
        super(vertex + " already exists in graph");
        this.vertex = vertex;
    }

    //- REQUÊTES

    /**
     * @return Le sommet existant déjà dans le graphe.
     */
    public Vertex getVertex() {
        return vertex;
    }
}
