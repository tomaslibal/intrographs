package eu.libal.intrographs;

import java.util.Set;

abstract public class BaseGraph<VertexType, EdgeClass> {
    protected Set<Vertex<VertexType>> vertices;
    protected Set<Edge<VertexType>> edges;
}
