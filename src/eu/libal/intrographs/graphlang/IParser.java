package eu.libal.intrographs.graphlang;

import eu.libal.intrographs.graphs.Graph;
import eu.libal.intrographs.graphs.edge.Edge;

/**
 *
 */
public interface IParser {

    /**
     * Constructs a parse tree from input sentence.
     *
     * @param sentence is the input to be parse
     * @return a parse tree representing the input sentence as a graph
     */
    Graph<BasicOperation, Edge<BasicOperation>> parse(String sentence);
}
