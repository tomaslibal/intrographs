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
}