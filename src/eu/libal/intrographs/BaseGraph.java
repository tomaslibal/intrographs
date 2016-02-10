package eu.libal.intrographs;

import java.util.Set;

abstract public class BaseGraph<VertexType, EdgeClass> implements IGraph<VertexType, EdgeClass> {
    protected Set<Vertex<VertexType>> vertices;
    protected Set<EdgeClass> edges;
}
