package graph;

import graph.exceptions.ArcNotFound;
import graph.exceptions.DuplicateArc;
import graph.exceptions.DuplicateVertex;
import graph.exceptions.VertexNotFound;

import java.util.Set;
import java.util.SortedSet;

/**
 * Un graphe est une structure de données comportent des éléments polymorphes
 * pouvant être mis en relation. Un graphe orienté est un graphe dans lequel
 * une relation est unidirectionnelle (A est en relation avec B, mais B n'est
 * pas en relation avec A). Un élément contenu dans un graphe est appelé un
 * sommet, une relation entre deux sommets d'un graphe est appelée un arc.
 * Par soucis de convention de codage, un sommet sera appelé "vertex" et un arc
 * sera appelé "arc" dans le nom des différentes propriétés et méthodes de
 * l'interface ainsi que dans ses implémentations.
 */
public interface Graph {

    //- Gestion des éléments contenus dans le graphe

    /**
     * Insère le sommet dénoté par x dans le graphe. Ce sommet sera en aucun
     * cas relié à un autre sommet du graphe par une relation. Si le sommet se
     * trouve déjà dans le graphe, une exception DuplicateVertex est levée.
     * @param x Le sommet à ajouter dans le graphe.
     */
    void addVertex(Vertex x) throws DuplicateVertex;

    /**
     * Retire le sommet dénoté par x dans le graphe. Supprime également toutes
     * les relations impliquant ce sommet dans le graphe. Si le sommet ne se
     * trouve pas dans le graphe, une exception VertexNotFound est levée.
     * @param x Le sommet à retirer du graphe.
     */
    void removeVertex(Vertex x) throws VertexNotFound;

    /**
     * Indique si le sommet dénoté par x est contenu dans ce graphe.
     * @param x Le sommet à rechercher dans le graphe.
     * @return true ou false selon si le sommet est contenu dans ce graphe.
     */
    boolean containsVertex(Vertex x);

    /**
     * Recherche l'objet décrivant le sommet dénoté par x dans ce graphe. Si le
     * sommet n'existe pas, une exception VertexNotFound est levée.
     * @param x Le sommet à rechercher dans ce graphe.
     * @return L'objet décrivant le sommet dans le graphe.
     */
    Vertex getVertex(Vertex x) throws VertexNotFound;

    /**
     * Recherche l'objet décrivant le sommet dont le nom est strictement égal à
     * la chaine dénotée par search. On considère le nom d'un graphe comme
     * étant unique, un nom ne peut donc être attribué qu'à un seul graphe.
     * @param search Le nom du sommet à rechercher le graphe.
     * @return L'objet décrivant le sommet trouvé, ou null s'il n'existe pas.
     */
    Vertex findVertexByName(String search);

    //- Gestion des relations dans le graphe

    /**
     * Crée un arc partant du sommet dénoté par x et pointant vers le sommet
     * dénoté par y. Le sommet dénoté par x sera alors en relation avec le
     * sommet dénoté par y. Si un des deux sommets n'est pas contenu dans le
     * graphe, une exception VertexNotFound est levée. Si un arc reliant ces
     * deux sommets existe déjà, une exception DuplicateArc est levée.
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     */
    void createArc(Vertex x, Vertex y) throws DuplicateArc, VertexNotFound;

    /**
     * Supprime l'arc partant du sommet dénoté par x et pointant vers le sommet
     * dénoté par y. Le sommet dénoté par x ne sera alors plus en relation avec
     * le sommet dénoté par y. Si un des deux sommets n'est pas contenu dans le
     * graphe, une exception VertexNotFound est levée. Si l'arc reliant ces
     * deux sommets n'existe pas, une exception ArcNotFound est levée.
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     */
    void deleteArc(Vertex x, Vertex y) throws ArcNotFound, VertexNotFound;

    /**
     * Surcharge de deleteArc(Vertex, Vertex) prenant en charge un objet Arc
     * obtenu à l'aide de la méthode getArc.
     * @param a Arc à supprimer du graphe.
     */
    void deleteArc(Arc a) throws ArcNotFound, VertexNotFound;

    /**
     * Indique si un arc partant du sommet dénoté par x et pointant vers le
     * sommet dénoté par y existe dans le graphe. Si un des deux sommets
     * n'existe pas, une exception VertexNotFound est levée.
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     * @return true ou false selon si un arc de x vers y existe dans le graphe.
     */
    boolean containsArc(Vertex x, Vertex y) throws VertexNotFound;

    /**
     * Recherche l'objet décrivant l'arc du sommet dénoté par x et pointant
     * vers le sommet dénoté par y dans le graphe. Si un des deux sommets
     * n'existe pas, une exception VertexNotFound est levée. Si l'arc reliant
     * ces deux sommets n'existe pas, une exception ArcNotFound est levée.
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     * @return L'objet décrivant l'arc de x vers y dans le graphe.
     */
    Arc getArc(Vertex x, Vertex y) throws ArcNotFound, VertexNotFound;

    //- Informations sur le graphe

    /**
     * @return Le nombre de sommets contenu dans ce graphe.
     */
    int vertexCount();

    /**
     * @return Le nombre d'arcs contenu dans ce graphe.
     */
    int arcCount();

    /**
     * @return Un ensemble contenant tous les sommets contenus dans ce graphe.
     */
    Set<Vertex> vertexSet();

    /**
     * @return Un ensemble contenant tous les sommets contenus dans ce graphe
     * triés dans l'ordre alphabétique par leur nom.
     */
    SortedSet<Vertex> vertexSetByName();

    /**
     * @return Un ensemble contenant tous les sommets contenus dans ce graphe
     * triés par degré sortant.
     */
    SortedSet<Vertex> vertexSetByDegree();

    /**
     * @return Un ensemble contenant tous les arcs contenus dans ce graphe.
     */
    Set<Arc> arcSet();

    /**
     * @return Informations générales sur ce graphe.
     */
    String toString();

    //- Opérations générales

    /**
     * @param x Le sommet avec lequel rechercher.
     * @return Les sommets à destination d'un arc partant du sommet partant du
     * sommet dénoté par x.
     */
    Set<Vertex> vertexFrom(Vertex x);

    /**
     * @param x Le sommet avec lequel rechercher.
     * @return Les sommets au départ d'un arc à destination du sommet dénoté
     * par x.
     */
    Set<Vertex> vertexTo(Vertex x);

    /**
     * Réinitialise ce graphe : retire tous les sommets et supprime toutes les
     * relations. À la fin de la méthode, ce graphe se trouvera dans le même
     * état qu'à sa création.
     */
    void clear();
}
