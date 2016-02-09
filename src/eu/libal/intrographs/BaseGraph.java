package eu.libal.intrographs;

import java.util.List;

/**
 * Created by tlibal on 2/9/16.
 */
abstract public class BaseGraph<VertexType, EdgeClass> {
    protected List<Vertex<VertexType>> vertices;
    protected List<Edge<VertexType>> edges;
}
