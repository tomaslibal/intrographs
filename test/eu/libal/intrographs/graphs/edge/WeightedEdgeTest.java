package eu.libal.intrographs.graphs.edge;

import eu.libal.intrographs.common.Notifiable;
import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

public class WeightedEdgeTest {

    private WeightedEdge<Integer> wEdge;
    private Vertex<Integer> s;
    private Vertex<Integer> t;

    @Before
    public void before() {
        s = new Vertex<>("foo");
        t = new Vertex<>("bar");
        wEdge = new WeightedEdge<>(s, t);
    }

    @Test
    public void shouldInitializeWithWeightOne() {
        assertThat(wEdge.getWeight(), is(1.0));
    }

    @Test
    public void shouldEmitUpdateEventWhenWeightUpdated() {
        Notifiable notifiable = Mockito.mock(Notifiable.class);

        wEdge.subscribe(notifiable);

        wEdge.setWeight(2.0);

        assertThat(wEdge.getWeight(), is(2.0));
        verify(notifiable).call("2.0");
    }

    @Test
    public void shouldReturnSourceAndTargetVertices() {
        assertThat(wEdge.getSource(), is(s));
        assertThat(wEdge.getTarget(), is(t));
    }

}