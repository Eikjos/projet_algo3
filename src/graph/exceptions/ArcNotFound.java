package graph.exceptions;

import graph.Arc;

/**
 * Cette exception est levée lorsqu'un arc a été utilisé dans un graphe dans
 * lequel il n'appartient pas.
 */
public class ArcNotFound extends Exception {

    //- ATTRIBUTS

    /**
     * Le sommet n'ayant pas été trouvé dans le graphe.
     */
    private final Arc arc;

    //- CONSTRUCTEURS

    /**
     * Une nouvelle exception ArcNotFound concernant l'arc dénoté par arc.
     * @param arc L'arc n'ayant pas pu être trouvé dans le graphe.
     */
    public ArcNotFound(Arc arc) {
        super("L'arc reliant \"" + arc.getFrom().getName() + "\" à \""
                + arc.getTo().getName() + "\" n'a pas pu être trouvé");
        this.arc = arc;
    }

    //- REQUÊTES

    /**
     * @return L'arc n'ayant pas été trouvé dans le graphe.
     */
    public Arc getArc() {
        return arc;
    }
}
