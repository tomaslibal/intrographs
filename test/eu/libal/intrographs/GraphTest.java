package eu.libal.intrographs;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GraphTest {

    Graph<Integer, Edge<Integer>> g;

    @Before
    public void setup() {
        g = new Graph<>();
    }

    @Test
    public void shouldInitWithEmptyVertexSet() {
        assertThat(g.vertexSet().size(), is(0));
    }

    @Test
    public void shouldInitWithEmptyEdgeSet() {
        assertThat(g.edgeSet().size(), is(0));
    }
}
