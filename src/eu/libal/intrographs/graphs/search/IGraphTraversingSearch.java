package eu.libal.intrographs.graphs.search;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.Set;
import java.util.function.Function;

/**
 *
 */
public interface IGraphTraversingSearch<T> {

    Set<Vertex<T>> search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode);

    Set<Vertex<T>> search(Graph<T, ?> graph, Vertex<T> v, Function<Vertex<T>, ?> execForEachNode);
}
