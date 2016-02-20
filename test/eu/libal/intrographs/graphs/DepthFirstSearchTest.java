package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

/**
 *
 */
public class DepthFirstSearchTest {

    @Mock
    private Graph<Integer, Edge<Integer>> graph;

    private DepthFirstSearch<Integer> search;

    @Before
    public void setup() {
        search = new DepthFirstSearch<>();
        graph = new Graph<>();
    }

    @Test
    public void shouldExecuteTheCallbackForEachVertexInTheGraph() {
        graph.addVertex(1, "a");
        graph.addVertex(2, "b");
        graph.addVertex(3, "c");
        graph.addVertex(4, "d");
        graph.addVertex(5, "e");
        graph.addVertex(5, "f");

        graph.addEdge("a", "b");
        graph.addEdge("a", "c");
        graph.addEdge("b", "d");
        graph.addEdge("b", "e");
        graph.addEdge("e", "f");

        class SimpleIntegerCounter {
            private int n;
            SimpleIntegerCounter() {
                n = 0;
            }
            public void inc() {
                n++;
            }
            public int getCounter() {
                return n;
            }
        }

        Set<String> visitedVertexIds = new HashSet<>();

        SimpleIntegerCounter numVisitsCounter = new SimpleIntegerCounter();

        search.search(graph, (Vertex<Integer> v) -> {
            numVisitsCounter.inc();
            visitedVertexIds.add(v.getId());
            System.out.println("executing for " + v.getId());
            return null;
        });

        assertThat(numVisitsCounter.getCounter(), is(graph.vertexSet().size()));
        assertThat(visitedVertexIds, containsInAnyOrder("a", "b", "c", "d", "e", "f"));
    }
}