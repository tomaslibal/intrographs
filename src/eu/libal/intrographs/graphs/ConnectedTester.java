package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.IVertex;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class ConnectedTester {

    public static boolean isConnected(Graph g) {
        Set<String> visited = new LinkedHashSet<>();

        Optional someVertex = g.vertexSet().stream().findAny();

        if (!someVertex.isPresent()) {
            // should throw - vertex set should be non empty for any graph
            return false;
        }

        Vertex vertex = (Vertex) someVertex.get();

        Set<Edge<Integer>> incidentEdges = g.incidentEdges(vertex);

        visited.add(vertex.getId());

        visitAdjacentNodes(vertex, incidentEdges, visited, g);

        // all visited = graph is connected
        if (visited.size() == g.vertexSet().size()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getNumberOfComponents(Graph g) {
        return -1;
    }

    private static void visitAdjacentNodes(IVertex v, Set<Edge<Integer>> incidentEdges, Set<String> visited, Graph g) {
        visited.add(v.getId());

        for (Edge<Integer> e: incidentEdges) {
            String targetId = e.getTarget().getId();
            String sourceId = e.getSource().getId();

            if (!visited.contains(targetId)) {
                visited.add(targetId);
                Set<Edge<Integer>> additionalEdgesToVisit = g.incidentEdges((Vertex) e.getTarget());
                visitAdjacentNodes(e.getTarget(), additionalEdgesToVisit, visited, g);
            }

            if (!visited.contains(sourceId)) {
                visited.add(sourceId);
                Set<Edge<Integer>> additionalEdgesToVisit = g.incidentEdges((Vertex) e.getSource());
                visitAdjacentNodes(e.getSource(), additionalEdgesToVisit, visited, g);
            }
        }
    }
}
