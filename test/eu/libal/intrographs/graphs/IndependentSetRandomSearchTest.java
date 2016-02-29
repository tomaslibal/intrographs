package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

public class IndependentSetRandomSearchTest {

    IndependentSetRandomSearch<Integer, Edge<Integer>> search;
    Graph<Integer, Edge<Integer>> graph;

    @Before
    public void setup() {
        search = new IndependentSetRandomSearch<>();
        graph = new Graph<>();
    }

    @Test
    public void shouldReturnSetOfOneVertexIfGraphHasOnlyOneVertex() {
        Vertex<Integer> v1 = graph.addVertex(1);

        Set<Vertex<Integer>> independentSet = this.search.search(graph, (Vertex<Integer> v) -> null);

        assertThat(independentSet, contains(v1));
    }
}