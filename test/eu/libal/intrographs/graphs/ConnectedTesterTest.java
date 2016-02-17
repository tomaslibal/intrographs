package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ConnectedTesterTest {

    private Graph<Integer, Edge<Integer>> g;

    @Before
    public void setup() {
        g = new Graph<>();
    }

    @Test
    public void shouldReturnTrueIfAllVerticesAreConnected() {
        g.addVertex(0, "a");
        g.addVertex(1, "b");
        g.addVertex(2, "c");
        g.addVertex(3, "d");

        g.addEdge("a", "b");
        g.addEdge("a", "c");
        g.addEdge("c", "d");

        assertThat(ConnectedTester.isConnected(g), is(true));
    }

}