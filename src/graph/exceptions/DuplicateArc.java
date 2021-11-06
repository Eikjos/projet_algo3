package graph.exceptions;

import graph.Arc;

/**
 * Cette exception est levée lorsqu'un arc reliant un sommet x à un sommet y
 * tente d'être créé dans le graphe, mais existe déjà dans ce dernier.
 */
public class DuplicateArc extends Exception {

    //- ATTRIBUTS

    /**
     * L'arc existant déjà dans le graphe.
     */
    private final Arc arc;

    //- CONSTRUCTEURS

    /**
     * Une nouvelle exception DuplicateArc concernant l'arc dénoté par arc.
     * @param arc L'arc existant déjà dans le graphe.
     */
    public DuplicateArc(Arc arc) {
        super(arc + " already exists in graph");
        this.arc = arc;
    }

    //- REQUÊTES

    /**
     * @return L'arc existant déjà dans le graphe.
     */
    public Arc getArc() {
        return arc;
    }
}
