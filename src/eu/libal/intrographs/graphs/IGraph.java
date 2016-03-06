package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IGraph<T, U extends Edge<T>>
{

    Vertex<T> addVertex(T v);
    Vertex<T> addVertex(T v, String id);
    Optional<Vertex<T>> lookupVertex(String id);
    U addEdge(U e);
    U addEdge(String sourceId, String targetId);
    Set<U> incidentEdges(Vertex<T> v);

    Set<Vertex<T>> vertexSet();
    Set<U> edgeSet();

    boolean removeVertex(String vertexId);
    boolean removeVertex(Vertex<T> v);
    boolean removeEdge(U e);
    boolean removeVertices(List<Vertex<T>> vertices);
    boolean removeEdges(List<U> edges);

    /**
     * Returns the degree of a node (undirected)
     *
     * @param v vertex whose degree will be returned
     * @return int
     */
    int degreeOf(Vertex<T> v);

    /**
     * Returns the degree of a node (undirected)
     *
     * @param vertexId string id value of the vertex whose degree will be returned
     * @return int
     */
    int degreeOf(String vertexId);
}
