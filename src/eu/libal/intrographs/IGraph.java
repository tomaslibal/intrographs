package eu.libal.intrographs;

import java.util.List;
import java.util.Set;

public interface IGraph<VertexType, EdgeClass>
{

    void addVertex(VertexType v);
    void addEdge(EdgeClass e);

    Set<Vertex<VertexType>> vertexSet();
    Set<EdgeClass> edgeSet();

    void removeVertex(Vertex<VertexType> v);
    void removeEdge(EdgeClass e);
    void removeVertices(List<Vertex<VertexType>> vertices);
    void removeEdges(List<EdgeClass> edges);
}
