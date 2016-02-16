package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.List;
import java.util.Set;

public interface IGraph<VertexType, EdgeClass>
{

    Vertex<VertexType> addVertex(VertexType v);
    Vertex<VertexType> addVertex(VertexType v, String id);
    EdgeClass addEdge(EdgeClass e);
    EdgeClass addEdge(String sourceId, String targetId);

    Set<Vertex<VertexType>> vertexSet();
    Set<EdgeClass> edgeSet();

    boolean removeVertex(String vertexId);
    boolean removeVertex(Vertex<VertexType> v);
    boolean removeEdge(EdgeClass e);
    boolean removeVertices(List<Vertex<VertexType>> vertices);
    boolean removeEdges(List<EdgeClass> edges);

    /**
     * Returns the degree of a node (undirected)
     *
     * @param v
     * @return int
     */
    int degreeOf(Vertex<VertexType> v);

    /**
     * Returns the degree of a node (undirected)
     *
     * @param vertexId
     * @return
     */
    int degreeOf(String vertexId);
}
