import graph.Graph;
import graph.StdGraph;
import graph.Vertex;
import graph.exceptions.DuplicateArc;
import graph.exceptions.DuplicateVertex;
import graph.exceptions.VertexNotFound;

public class Main {

    /**
     * Démarre l'application de visualisation d'un réseau social représenté à
     * l'aide de graphes.
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) throws DuplicateVertex, DuplicateArc, VertexNotFound {
        Graph testGraph = new StdGraph();

        //- Classe de test pour le sommet
        class TestVertex extends Vertex {
            private final String name;
            public TestVertex(String name) { this.name = name; }
            @Override
            public String getName() {
                return name;
            }
        }

        //- Ajout de quelques sommets
        Vertex v1 = new TestVertex("V1"); testGraph.addVertex(v1);
        Vertex v2 = new TestVertex("V2"); testGraph.addVertex(v2);
        Vertex v3 = new TestVertex("V3"); testGraph.addVertex(v3);
        Vertex v4 = new TestVertex("V4"); testGraph.addVertex(v4);

        //- Ajout de quelques arcs
        testGraph.createArc(v1, v2);
        testGraph.createArc(v4, v3);
        testGraph.createArc(v1, v3);
        testGraph.createArc(v1, v4);
        testGraph.createArc(v4, v1);
        testGraph.createArc(v2, v1);

        //- Affichage
        printGraph(testGraph);

        //- Suppression d'un arc et vérification des nouvelles propriétés
        testGraph.removeVertex(v1);
        printGraph(testGraph);
    }

    private static void printGraph(Graph testGraph) {
        System.out.println(testGraph);
        System.out.println("> vertexSet(): " + testGraph.vertexSet());
        System.out.println("> vertexSetByName(): "
                + testGraph.vertexSetByName());
        System.out.println("> vertexSetByDegree(): "
                + testGraph.vertexSetByDegree());
        System.out.println("> arcSet(): " + testGraph.arcSet());
        System.out.println("> findVertex(\"V3\"): "
                + testGraph.findVertexByName("V3"));
    }
}
