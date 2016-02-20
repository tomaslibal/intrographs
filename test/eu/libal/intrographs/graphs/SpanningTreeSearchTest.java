package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.*;

/**
 *
 */
public class SpanningTreeSearchTest {

    @Mock
    private Graph<Integer, Edge<Integer>> graph;

    private SpanningTreeSearch<Integer> search;

    @Before
    public void setup() {
        search = new SpanningTreeSearch<>();
        graph = new Graph<>();
    }

    @Test
    public void shouldFindASpanningTree() {
        Vertex<Integer> a = graph.addVertex(1, "a");
        Vertex<Integer> b = graph.addVertex(2, "b");
        Vertex<Integer> c = graph.addVertex(3, "c");
        Vertex<Integer> d = graph.addVertex(4, "d");
        Vertex<Integer> e = graph.addVertex(5, "e");
        Vertex<Integer> f = graph.addVertex(5, "f");

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "d");
        graph.addEdge("b", "e");
        graph.addEdge("e", "f");

        Set<Vertex<Integer>> spanTree = this.search.search(graph, (Vertex<Integer> v) -> null);

        assertThat(spanTree, containsInAnyOrder(a, b, c, d, e, f));
    }

    @Test
    public void shouldFindSpanTreeAndShouldNotIncludeDisjointVertices() {
        graph.addVertex(1, "a");
        graph.addVertex(2, "b");
        graph.addVertex(3, "c");
        graph.addVertex(4, "d");
        graph.addVertex(5, "e");
        graph.addVertex(5, "f");

        graph.addVertex(101, "aa");
        graph.addVertex(102, "bb");

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "d");
        graph.addEdge("b", "e");
        graph.addEdge("e", "f");

        graph.addEdge("aa", "bb");

        Set<Vertex<Integer>> spanTree = this.search.search(graph, (Vertex<Integer> v) -> null);

        // the algorithm chooses a random starting point so it could have two outcomes
        assertTrue(spanTree.size() == 6 || spanTree.size() == 2);
    }

}