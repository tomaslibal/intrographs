package eu.libal.intrographs.graphs.factories;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

public class UtilityGraphFactory {

    private static Vertex<Integer> getVertex(Integer value, String id) {
        return new Vertex<>(value, id);
    }

    public static Graph<Integer, Edge> get() {
        Graph<Integer, Edge> graph = Graph.getNewGraph(Integer.class);
        graph.addVertex(getVertex(0, "a"));
        graph.addVertex(getVertex(1, "b"));
        graph.addVertex(getVertex(2, "c"));

        graph.addVertex(getVertex(100, "m"));
        graph.addVertex(getVertex(101, "n"));
        graph.addVertex(getVertex(102, "o"));

        graph.addEdge("a", "m");
        graph.addEdge("a", "n");
        graph.addEdge("a", "o");

        graph.addEdge("b", "m");
        graph.addEdge("b", "n");
        graph.addEdge("b", "o");

        graph.addEdge("c", "m");
        graph.addEdge("c", "n");
        graph.addEdge("c", "o");

        return graph;
    }
}
