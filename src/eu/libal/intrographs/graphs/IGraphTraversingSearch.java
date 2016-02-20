package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.Set;
import java.util.function.Function;

/**
 *
 */
public interface IGraphTraversingSearch<T> {

    Set<Vertex<T>> search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode);
}
