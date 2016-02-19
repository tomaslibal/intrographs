package eu.libal.intrographs.graphs.vertex;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VertexTest {

    @Test
    public void shouldReturnTheStoredValue_WhenGetValueInvoked() throws Exception {
        Vertex<Integer> v1 = new Vertex<>(42, "foo");

        assertThat(v1.getValue(), is(42));
    }

    @Test
    public void shouldReturnTheID_WhenGetIdInvoked() throws Exception {
        Vertex<Integer> v1 = new Vertex<>(42, "foo");

        assertThat(v1.getId(), is("foo"));
    }

    @Test
    public void shouldCreateVertexWithNoAdjacentNodes() throws Exception {
        Vertex<Integer> v = new Vertex<>(42, "bar");

        assertThat(v.getAdjacentVertices().size(), is(0));
    }
}