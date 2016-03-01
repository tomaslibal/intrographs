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

    /**
     * Given a parse tree this method prints its string representation. This
     * is a reverse operation of {@code parse}. Some information like whitespace
     * is lost during parsing so this is not preserved.
     *
     * @param parseTree obtained from parsing a string
     * @return a string that would produce the same parse tree if parsed
     */
    String print(Graph<BasicOperation, Edge<BasicOperation>> parseTree);
}
