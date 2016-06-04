package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This type represents a graph. It is an undirected graph, which does not have to be simple (edges having the same
 * vertex as the source and the target are allowed). This is not a multigraph, so two edges cannot be incident on the
 * same vertex tuple. Because the Edge class is a parameter on this generic class, it is possible to use weighted or
 * unweighted edges as needed.
 *
 * @param <T> The type of the value which each Vertex stores
 * @param <U> The type of the Edge
 */
public class Graph<T, U extends Edge> extends BaseGraph<T, U> {

    public Graph() {
    }

    /**
     * Creates a new instance of a Graph class, parameterized by V.
     *
     * @param cls Class type of the Vertex and Edge.
     * @param <V> Type that will be used by the vertices and edges in the graph.
     * @return Graph
     */
    public static <V> Graph<V, Edge> getNewGraph(Class<V> cls) {
        return new Graph<>();
    }
}
