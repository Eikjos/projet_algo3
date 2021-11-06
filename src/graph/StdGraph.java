package graph;

import graph.exceptions.ArcNotFound;
import graph.exceptions.DuplicateArc;
import graph.exceptions.DuplicateVertex;
import graph.exceptions.VertexNotFound;
import util.Assert;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Une implémentation de l'interface Graph en utilisant une TreeSet pour
 * contenir l'ensemble des sommets du graphe, ainsi qu'une HashSet pour
 * contenir l'ensemble des arcs du graphe.
 */
public class StdGraph implements Graph {

    //- ATTRIBUTS

    /**
     * Un ensemble contenant les sommets de ce graphe.
     */
    private final Set<Vertex> vertices;

    /**
     * Un ensemble contenant les arcs de ce graphe.
     */
    private final Set<Arc> arcs;

    //- CONSTRUCTEURS

    public StdGraph() {
        this.vertices = new HashSet<Vertex>();
        this.arcs = new HashSet<Arc>();
    }

    //- MÉTHODES

    /**
     * Insère le sommet dénoté par x dans le graphe. Ce sommet sera en aucun
     * cas relié à un autre sommet du graphe par une relation. Si le sommet se
     * trouve déjà dans le graphe, une exception DuplicateVertex est levée.
     *
     * @param x Le sommet à ajouter dans le graphe.
     */
    @Override
    public void addVertex(Vertex x) throws DuplicateVertex {
        Assert.check(x != null, "x must not be null");
        if (vertices.contains(x)) {
            throw new DuplicateVertex(x);
        }
        vertices.add(x);
    }

    /**
     * Retire le sommet dénoté par x dans le graphe. Supprime également toutes
     * les relations impliquant ce sommet dans le graphe. Si le sommet ne se
     * trouve pas dans le graphe, une exception VertexNotFound est levée.
     *
     * @param x Le sommet à retirer du graphe.
     */
    @Override
    public void removeVertex(Vertex x) throws VertexNotFound {
        Assert.check(x != null, "x must not be null");
        if (!vertices.contains(x)) {
            throw new VertexNotFound(x);
        }
        for (Iterator<Arc> it = arcs.iterator(); it.hasNext();) {
            Arc a = it.next();
            if (a.isImplied(x)) {
                it.remove();
            }
        }
        vertices.remove(x);
    }

    /**
     * Indique si le sommet dénoté par x est contenu dans ce graphe.
     *
     * @param x Le sommet à rechercher dans le graphe.
     * @return true ou false selon si le sommet est contenu dans ce graphe.
     */
    @Override
    public boolean containsVertex(Vertex x) {
        Assert.check(x != null, "x must not be null");
        return vertices.contains(x);
    }

    /**
     * Recherche l'objet décrivant le sommet dénoté par x dans ce graphe. Si le
     * sommet n'existe pas, une exception VertexNotFound est levée.
     *
     * @param x Le sommet à rechercher dans ce graphe.
     * @return L'objet décrivant le sommet dans le graphe.
     */
    @Override
    public Vertex getVertex(Vertex x) throws VertexNotFound {
        Assert.check(x != null, "x must not be null");
        for (Vertex y : vertices) {
            if (x.equals(y)) {
                return y;
            }
        }
        throw new VertexNotFound(x);
    }

    /**
     * Recherche l'objet décrivant le sommet dont le nom est strictement égal à
     * la chaine dénotée par search. On considère le nom d'un graphe comme
     * étant unique, un nom ne peut donc être attribué qu'à un seul graphe.
     *
     * @param search Le nom du sommet à rechercher le graphe.
     * @return L'objet décrivant le sommet trouvé, ou null s'il n'existe pas.
     */
    @Override
    public Vertex findVertexByName(String search) {
        Assert.check(search != null, "search must not be null");
        Vertex result = null;
        for (Vertex x : vertices) {
            if (x.getName().equals(search)) {
                result = x;
                break;
            }
        }
        return result;
    }

    /**
     * Crée un arc partant du sommet dénoté par x et pointant vers le sommet
     * dénoté par y. Le sommet dénoté par x sera alors en relation avec le
     * sommet dénoté par y. Si un des deux sommets n'est pas contenu dans le
     * graphe, une exception VertexNotFound est levée. Si un arc reliant ces
     * deux sommets existe déjà, une exception DuplicateArc est levée.
     *
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     */
    @Override
    public void createArc(Vertex x, Vertex y) throws DuplicateArc, VertexNotFound {
        Assert.check(x != null, "x must not be null");
        Assert.check(y != null, "y must not be null");
        if (!vertices.contains(x)) {
            throw new VertexNotFound(x);
        }
        if (!vertices.contains(y)) {
            throw new VertexNotFound(y);
        }
        Arc a = new Arc(x, y);
        if (arcs.contains(a)) {
            throw new DuplicateArc(a);
        }
        arcs.add(a);
    }

    /**
     * Supprime l'arc partant du sommet dénoté par x et pointant vers le sommet
     * dénoté par y. Le sommet dénoté par x ne sera alors plus en relation avec
     * le sommet dénoté par y. Si un des deux sommets n'est pas contenu dans le
     * graphe, une exception VertexNotFound est levée. Si l'arc reliant ces
     * deux sommets n'existe pas, une exception ArcNotFound est levée.
     *
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     */
    @Override
    public void deleteArc(Vertex x, Vertex y) throws ArcNotFound, VertexNotFound {
        Assert.check(x != null, "x must not be null");
        Assert.check(y != null, "y must not be null");
        if (!vertices.contains(x)) {
            throw new VertexNotFound(x);
        }
        if (!vertices.contains(y)) {
            throw new VertexNotFound(y);
        }
        Arc a = new Arc(x, y);
        for (Arc b : arcs) {
            if (b.equals(a)) {
                arcs.remove(b);
                return;
            }
        }
        throw new ArcNotFound(a);
    }

    /**
     * Surcharge de deleteArc(Vertex, Vertex) prenant en charge un objet Arc
     * obtenu à l'aide de la méthode getArc.
     *
     * @param a Arc à supprimer du graphe.
     */
    @Override
    public void deleteArc(Arc a) throws ArcNotFound, VertexNotFound {
        deleteArc(a.getFrom(), a.getTo());
    }

    /**
     * Indique si un arc partant du sommet dénoté par x et pointant vers le
     * sommet dénoté par y existe dans le graphe. Si un des deux sommets
     * n'existe pas, une exception VertexNotFound est levée.
     *
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     * @return true ou false selon si un arc de x vers y existe dans le graphe.
     */
    @Override
    public boolean containsArc(Vertex x, Vertex y) throws VertexNotFound {
        Assert.check(x != null, "x must not be null");
        Assert.check(y != null, "y must not be null");
        if (!vertices.contains(x)) {
            throw new VertexNotFound(x);
        }
        if (!vertices.contains(y)) {
            throw new VertexNotFound(y);
        }
        Arc a = new Arc(x, y);
        return arcs.contains(a);
    }

    /**
     * Recherche l'objet décrivant l'arc du sommet dénoté par x et pointant
     * vers le sommet dénoté par y dans le graphe. Si un des deux sommets
     * n'existe pas, une exception VertexNotFound est levée. Si l'arc reliant
     * ces deux sommets n'existe pas, une exception ArcNotFound est levée.
     *
     * @param x Le sommet de départ de l'arc.
     * @param y Le sommet d'arrivée de l'arc.
     * @return L'objet décrivant l'arc de x vers y dans le graphe.
     */
    @Override
    public Arc getArc(Vertex x, Vertex y) throws ArcNotFound, VertexNotFound {
        Assert.check(x != null, "x must not be null");
        Assert.check(y != null, "y must not be null");
        if (!vertices.contains(x)) {
            throw new VertexNotFound(x);
        }
        if (!vertices.contains(y)) {
            throw new VertexNotFound(y);
        }
        Arc a = new Arc(x, y);
        for (Arc b : arcs) {
            if (b.equals(a)) {
                return b;
            }
        }
        throw new ArcNotFound(a);
    }

    /**
     * @return Le nombre de sommets contenu dans ce graphe.
     */
    @Override
    public int vertexCount() {
        return vertices.size();
    }

    /**
     * @return Le nombre d'arcs contenu dans ce graphe.
     */
    @Override
    public int arcCount() {
        return arcs.size();
    }

    /**
     * @return Un ensemble contenant tous les sommets contenus dans ce graphe.
     */
    @Override
    public Set<Vertex> vertexSet() {
        return new HashSet<Vertex>(vertices);
    }

    /**
     * @return Un ensemble contenant tous les sommets contenus dans ce graphe
     * triés dans l'ordre alphabétique par leur nom.
     */
    @Override
    public SortedSet<Vertex> vertexSetByName() {
        return new TreeSet<Vertex>(vertices);
    }

    /**
     * @return Un ensemble contenant tous les sommets contenus dans ce graphe
     * triés par degré sortant.
     */
    @Override
    public SortedSet<Vertex> vertexSetByDegree() {
        SortedSet<Vertex> set = new TreeSet<Vertex>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                int d = vertexFrom(o2).size() - vertexFrom(o1).size();
                if (d != 0) return d;
                return o1.compareTo(o2);
            }
        });
        set.addAll(vertices);
        return set;
    }

    /**
     * @return Un ensemble contenant tous les arcs contenus dans ce graphe.
     */
    @Override
    public Set<Arc> arcSet() {
        return new HashSet<Arc>(arcs);
    }

    /**
     * @return Informations générales sur ce graphe.
     */
    @Override
    public String toString() {
        return "[Graph] " + vertices.size() + " vertices - " + arcs.size()
                + " arcs";
    }

    /**
     * @param x Le sommet avec lequel rechercher.
     * @return Les sommets à destination d'un arc partant du sommet partant du
     * sommet dénoté par x.
     */
    public Set<Vertex> vertexFrom(Vertex x) {
        Set<Vertex> set = new HashSet<Vertex>();
        for (Arc a : arcs) {
            if (a.getFrom().equals(x)) {
                set.add(a.getTo());
            }
        }
        return set;
    }

    /**
     * @param x Le sommet avec lequel rechercher.
     * @return Les sommets au départ d'un arc à destination du sommet dénoté
     * par x.
     */
    public Set<Vertex> vertexTo(Vertex x) {
        Set<Vertex> set = new HashSet<Vertex>();
        for (Arc a : arcs) {
            if (a.getTo().equals(x)) {
                set.add(a.getFrom());
            }
        }
        return set;
    }

    /**
     * Réinitialise ce graphe : retire tous les sommets et supprime toutes les
     * relations. À la fin de la méthode, ce graphe se trouvera dans le même
     * état qu'à sa création.
     */
    @Override
    public void clear() {
        vertices.clear();
        arcs.clear();
    }
}
