package eu.libal.intrographs;

/**
 * Created by tlibal on 2/9/16.
 */
public interface IEdge<T>
{
    IVertex<T> getTarget();
    IVertex<T> getSource();
}