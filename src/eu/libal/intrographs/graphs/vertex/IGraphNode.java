package eu.libal.intrographs.graphs.vertex;

import java.util.List;

/**
 * Node in a graph. When a graph is projected to a 2d plane it can be represented
 * as dots for nodes and lines for edges. This Node type logically represents the
 * 'dots' of the graph, and together with the list of adjacent nodes it logically
 * recreates the graph structure.
 *
 *
 *      I----------I
 *      I  Root    I
 *      I----------I
 *      I Adjacent I
 *      I----------I
 *        /      \
 *       /        \
 *      /          \
 *   I----------I   ....
 *   I  Node 2  I
 *   I----------I
 *   I Adjacent I
 *   I----------I
 *     /
 *    /
 *  null
 *
 * @param <VertexType>
 */
public interface IGraphNode<VertexType> {

    Vertex<VertexType> getVertex();
    List<IGraphNode> getAdjacentNodes();


}
