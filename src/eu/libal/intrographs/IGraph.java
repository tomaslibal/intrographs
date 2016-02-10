package eu.libal.intrographs;

import eu.libal.intrographs.vertex.Vertex;

import java.util.List;
import java.util.Set;

public interface IGraph<VertexType, EdgeClass>
{

    Vertex<VertexType> addVertex(VertexType v);
    void addEdge(EdgeClass e);

    Set<Vertex<VertexType>> vertexSet();
    Set<EdgeClass> edgeSet();

    void removeVertex(Vertex<VertexType> v);
    void removeEdge(EdgeClass e);
    void removeVertices(List<Vertex<VertexType>> vertices);
    void removeEdges(List<EdgeClass> edges);

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
