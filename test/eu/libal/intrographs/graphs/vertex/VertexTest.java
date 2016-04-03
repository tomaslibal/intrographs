package eu.libal.intrographs.graphs.vertex;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.internal.util.collections.Sets.newSet;

public class VertexTest {

    @Test
    public void shouldReturnTheStoredValueWhenGetValueInvoked() {
        Vertex<Integer> v1 = new Vertex<>(42, "foo");

        assertThat(v1.getValue(), is(42));
    }

    @Test
    public void shouldReturnTheIDWhenGetIdInvoked() {
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

    @Test
    public void shouldEqualWithAnotherVertexIfIDsMatch() {
        Vertex<Integer> v = new Vertex<>("foo");
        Vertex<Integer> w = new Vertex<>("foo");
        Vertex<Integer> x = new Vertex<>("bar");

        assertThat(v.equals(w), is(true));
        assertThat(w.equals(v), is(true));
        assertThat(x.equals(v), is(false));
    }

    @Test
    public void shouldReturn0WhenIdsAreComparable() {
        Vertex<Integer> v = new Vertex<>("foo");
        Vertex<Integer> w = new Vertex<>("foo");

        assertThat(v.compareTo(w), is(0));
    }

    @Test
    public void shouldReturnNumberGreaterThanZeroWhenFirstIdComesAfterTheSecondId() {
        Vertex<Integer> v = new Vertex<>("b");
        Vertex<Integer> w = new Vertex<>("a");

        assertThat(v.compareTo(w), is(Matchers.greaterThan(0)));
    }

    @Test
    public void shouldReturnNumberLessThanZeroWhenFirstIdComesBeforeTheSecondId() {
        Vertex<Integer> v = new Vertex<>("x");
        Vertex<Integer> w = new Vertex<>("y");

        assertThat(v.compareTo(w), is(Matchers.lessThan(0)));
    }
}