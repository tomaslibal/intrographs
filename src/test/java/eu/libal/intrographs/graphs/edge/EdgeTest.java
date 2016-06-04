package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class EdgeTest {

    Vertex<Integer> v1;
    Vertex<Integer> v2;

    @Before
    public void setup() {
        v1 = new Vertex<>(1, "A");
        v2 = new Vertex<>(2, "B");
    }

    @Test
    public void shouldReturnTargetVertex_WhenGetTargetInvoked() throws Exception {
        Edge<Integer> e1 = new Edge<>(v1, v2);

        assertThat(e1.getTarget(), is(v2));
    }

    @Test
    public void shouldReturnSourceVertex_WhenGetSourceInvoked() throws Exception {
        Edge<Integer> e1 = new Edge<>(v1, v2);

        assertThat(e1.getSource(), is(v1));
    }

    @Test
    public void shouldReturnZeroWhenBothSourceAndTargetVerticesAreEqual() {
        Edge<Integer> e1 = new Edge<>(v1, v2);
        Edge<Integer> e2 = new Edge<>(v1, v2);

        assertThat(e1.compareTo(e2), is(0));
    }

    @Test
    public void shouldReturnNegativeOneWhenSourceVerticesAreNotEqual() {
        Vertex<Integer> v3 = new Vertex<>("baz");

        Edge<Integer> e1 = new Edge<>(v1, v2);
        Edge<Integer> e2 = new Edge<>(v3, v2);

        assertThat(e1.compareTo(e2), is(-1));
    }

    @Test
    public void shouldReturnOneWhenTargetVerticesAreNotEqual() {
        Vertex<Integer> v3 = new Vertex<>("baz");

        Edge<Integer> e1 = new Edge<>(v1, v2);
        Edge<Integer> e2 = new Edge<>(v1, v3);

        assertThat(e1.compareTo(e2), is(1));
    }
}