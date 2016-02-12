package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.IVertex;

/**
 * Created by tlibal on 2/9/16.
 */
public interface IEdge<T>
{
    IVertex<T> getTarget();
    IVertex<T> getSource();
}