package eu.libal.intrographs;

import java.util.Set;

/**
 * Created by tlibal on 2/9/16.
 */
abstract public class BaseGraph<VertexType, EdgeClass> {
    protected Set<Vertex<VertexType>> vertices;
    protected Set<Edge<VertexType>> edges;
}
