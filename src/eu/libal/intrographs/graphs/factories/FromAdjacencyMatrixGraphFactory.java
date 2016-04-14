package eu.libal.intrographs.graphs.factories;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.LinkedList;
import java.util.List;

public class FromAdjacencyMatrixGraphFactory {
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
