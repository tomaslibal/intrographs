package eu.libal.intrographs;

/**
 * Created by tlibal on 2/9/16.
 */
public interface IGraph<VertexType, EdgeType>
{

    void addVertex(Vertex<VertexType> v);
    void addEdge();

    void removeVertex(Vertex<VertexType> v);
    void removeEdge();
    void removeVertices();
    void removeEdges();
}
