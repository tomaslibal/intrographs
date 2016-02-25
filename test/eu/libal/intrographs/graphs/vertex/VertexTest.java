package eu.libal.intrographs.graphs.vertex;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.internal.util.collections.Sets.newSet;

public class VertexTest {

    @Test
    public void shouldReturnTheStoredValue_WhenGetValueInvoked() {
        Vertex<Integer> v1 = new Vertex<>(42, "foo");

        assertThat(v1.getValue(), is(42));
    }

    @Test
    public void shouldReturnTheID_WhenGetIdInvoked() {
        Vertex<Integer> v1 = new Vertex<>(42, "foo");

        assertThat(v1.getId(), is("foo"));
    }

    @Test
    public void shouldCreateVertexWithNoAdjacentNodes() {
        Vertex<Integer> v = new Vertex<>(42, "foo");

        assertThat(v.getAdjacentVertices().size(), is(0));
    }

    @Test
    public void shouldAddAdjacentVertex() {
        Vertex<Integer> v = new Vertex<>(42, "foo");
        Vertex<Integer> w = new Vertex<>(43, "bar");

        v.addAdjacentVertex(w);

        assertThat(v.getAdjacentVertices(), is(newSet(w)));
    }

    @Test
    public void shouldReturnDegreeOfTheVertex() {
        Vertex<Integer> v = new Vertex<>("v");
        Vertex<Integer> w = new Vertex<>("w");
        Vertex<Integer> x = new Vertex<>("x");

        v.addAdjacentVertex(w);
        v.addAdjacentVertex(x);

        assertThat(v.degreeOf(), is(2));
    }
}