package eu.libal.intrographs.graphs.factories;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.LinkedList;
import java.util.List;

/**
 * Factory for creating graphs from vertex adjacency matrix, which is a n*n matrix where a_ii = arc from and to the same
 * vertex, and a_ij = arc originating at vertices(i) and ending in vertices(j).
 */
public final class FromAdjacencyMatrixGraphFactory {

    /**
     * Given a n*n matrix this method returns a new instance of Graph.
     *
     * @param adjMatrix n*n matrix
     * @return Graph
     */
    public static Graph<Integer, Edge> get(int[][] adjMatrix) {
        Graph<Integer, Edge> graph = new Graph<>();
        List<Vertex<Integer>> vertices = new LinkedList<>();

        // create vertices first
        int idx = 0;
        for (int[] ver : adjMatrix) {
            Vertex<Integer> v = new Vertex<>(String.valueOf(idx));
            //graph.addVertex(v);
            vertices.add(v);
            idx++;
        }
        // add adjacent vertices
        for (int i = 0; i < adjMatrix.length; i++) {
            Vertex<Integer> c = vertices.get(i);
            int[] adjacent = adjMatrix[i];
            for (int j = 0; j < adjacent.length; j++) {
                if (adjacent[j] == 1) {
                    c.addAdjacentVertex(vertices.get(j));
                    Edge<Integer> e = new Edge<>(c, vertices.get(j));
                    graph.addEdge(e);
                }
            }
        }

        graph.addAllVertices(vertices);

        return graph;
    }
}
