package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AdjacencyListTest {

    AdjacencyList<Integer> adjList;

    @Before
    public void setup() {
        adjList = new AdjacencyList<>();
    }

    @Test
    public void shouldInitWithAnEmptyList() {
        assertThat(adjList.getList().size(), is(0));
    }

    @Test
    public void shouldAddVertexOfDegreeZero() {
        Vertex<Integer> a = new Vertex<>("foo");

        assertThat(adjList.add(a), is(true));
        LinkedHashMap<Vertex<Integer>, List<Vertex<Integer>>> expectedMap = new LinkedHashMap<>();
        expectedMap.put(a, new LinkedList<>());
        assertThat(adjList.getList(), is(expectedMap));
    }

    @Test
    public void shouldAddVertexOfDegreeOne() {
        Vertex<Integer> a = new Vertex<>("foo");
        Vertex<Integer> b = new Vertex<>("bar");

        assertThat(adjList.add(a, b), is(true));
        LinkedHashMap<Vertex<Integer>, List<Vertex<Integer>>> expectedMap = new LinkedHashMap<>();
        expectedMap.put(a, Arrays.asList(b));
        assertThat(adjList.getList(), is(expectedMap));
    }

    @Test
    public void shouldAddAdjacentVertexAndKeepPreviousAdjacentNodes() {
        Vertex<Integer> a = new Vertex<>("foo");
        Vertex<Integer> b = new Vertex<>("bar");
        Vertex<Integer> c = new Vertex<>("baz");

        adjList.add(a, b);
        adjList.add(a, c);

        LinkedHashMap<Vertex<Integer>, List<Vertex<Integer>>> expectedMap = new LinkedHashMap<>();
        expectedMap.put(a, Arrays.asList(b, c));

        assertThat(adjList.getList(), is(expectedMap));
    }
}