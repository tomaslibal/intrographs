package eu.libal.intrographs.graphs;

import com.sun.jdi.connect.spi.TransportService;
import eu.libal.intrographs.graphs.vertex.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
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
        expectedMap.put(a, Collections.singletonList(b));
        expectedMap.put(b, new LinkedList<>());
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
        expectedMap.put(b, new LinkedList<>());
        expectedMap.put(c, new LinkedList<>());

        assertThat(adjList.getList(), is(expectedMap));
    }

    @Test
    public void shouldReturnTrueForAdjacentNodes() {
        Vertex<Integer> a = new Vertex<>("foo");
        Vertex<Integer> b = new Vertex<>("bar");
        Vertex<Integer> c = new Vertex<>("baz");
        Vertex<Integer> x = new Vertex<>("binky");
        Vertex<Integer> y = new Vertex<>("minky");

        adjList.add(a, b);
        adjList.add(a, c);
        adjList.add(x);

        assertThat(adjList.isAdjacentTo(a, b), is(true));
        assertThat(adjList.isAdjacentTo(c, a), is(true));
        assertThat(adjList.isAdjacentTo(a, x), is(false));
        assertThat(adjList.isAdjacentTo(c, b), is(false));
        assertThat(adjList.isAdjacentTo(y, x), is(false));
    }

    @Test
    public void shouldAddAllNodesToTheKeySet() {
        Vertex<Integer> a = new Vertex<>("foo");
        Vertex<Integer> b = new Vertex<>("bar");
        Vertex<Integer> c = new Vertex<>("baz");
        Vertex<Integer> x = new Vertex<>("binky");

        adjList.add(a, b);
        adjList.add(a, c);
        adjList.add(x);

        LinkedHashSet<Vertex<Integer>> expectedSet = new LinkedHashSet<>();
        expectedSet.add(a);
        expectedSet.add(b);
        expectedSet.add(c);
        expectedSet.add(x);

        assertThat(adjList.getList().keySet(), is(expectedSet));
    }

    @Test
    public void shouldRemoveNodeFromTheMap() {
        Vertex<Integer> a = new Vertex<>("foo");
        Vertex<Integer> b = new Vertex<>("bar");
        Vertex<Integer> c = new Vertex<>("baz");
        Vertex<Integer> x = new Vertex<>("binky");
        Vertex<Integer> y = new Vertex<>("minky");

        adjList.add(a, b);
        adjList.add(a, c);
        adjList.add(x);

        assertThat(adjList.getList().keySet(), contains(a, b, c, x));

        adjList.remove(x);

        assertThat(adjList.getList().keySet(), contains(a, b, c));

        adjList.remove(a);

        assertThat(adjList.getList().keySet(), contains(b, c));
    }

    @Test
    public void shouldReturnListOfAdjacentNodesOfTheRemovedNode() {
        Vertex<Integer> a = new Vertex<>("foo");
        Vertex<Integer> b = new Vertex<>("bar");
        Vertex<Integer> c = new Vertex<>("baz");
        Vertex<Integer> x = new Vertex<>("binky");
        Vertex<Integer> y = new Vertex<>("minky");

        adjList.add(a, b);
        adjList.add(a, c);
        adjList.add(x);

        List<Vertex<Integer>> removed = adjList.remove(x);

        assertThat(removed, is(new LinkedList<>()));

        removed = adjList.remove(a);

        assertThat(removed, contains(b, c));
    }
}