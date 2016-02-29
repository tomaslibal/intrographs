package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
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

    @Test
    public void shouldReturnAnIndependentSet() {
        Vertex<Integer> v1 = graph.addVertex(1, "v1");
        Vertex<Integer> a1 = graph.addVertex(2, "a1");
        Vertex<Integer> a2 = graph.addVertex(3, "a2");
        graph.addEdge("v1", "a1");
        graph.addEdge("v1", "a2");

        Vertex<Integer> v2 = graph.addVertex(4, "v2");
        Vertex<Integer> a3 = graph.addVertex(5, "a3");
        graph.addEdge("v2", "a3");
        graph.addEdge("v1", "a3");

        Set<Vertex<Integer>> independentSet = this.search.search(graph, v1, (Vertex<Integer> v) -> null);

        assertThat(independentSet, containsInAnyOrder(v1, v2));
    }
}