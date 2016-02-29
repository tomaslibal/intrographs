package eu.libal.intrographs.graphs;

import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.graphs.vertex.Vertex;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class IndependentSetRandomSearch<T, U extends Edge<T>> implements IGraphTraversingSearch<T> {

    @Override
    public Set<Vertex<T>> search(Graph<T, ?> graph, Function<Vertex<T>, ?> execForEachNode) {
        Optional<Vertex<T>> randomVertex = graph.vertexSet()
                .stream()
                .findAny();

        if (randomVertex.isPresent()) {
            return search(graph, randomVertex.get(), execForEachNode);
        } else {
            return null;
        }
    }

    @Override
    public Set<Vertex<T>> search(Graph<T, ?> graph, Vertex<T> v, Function<Vertex<T>, ?> execForEachNode) {

        Set<Vertex<T>> independentSet = new HashSet<>();
        Set<Vertex<T>> vertices = graph.vertexSet();

        while(vertices.size() > 0) {
            Optional<Vertex<T>> lowestDegreeVertex = getLowestDegreeVertex(graph);

            if (!lowestDegreeVertex.isPresent()) {
                break;
            }

            independentSet.add(lowestDegreeVertex.get());

            // get all adjacent vertices to the lowest degree vertex
            Set<U> incidentEdges = (Set<U>) graph.incidentEdges(lowestDegreeVertex.get());

            incidentEdges.stream()
                    .forEach(edge -> {
                        Vertex<T> source = edge.getSource();
                        Vertex<T> target = edge.getTarget();

                        if (vertices.contains(source)) {
                           vertices.remove(source);
                        }

                        if (vertices.contains(target)) {
                            vertices.remove(target);
                        }
                    });

            // if the vertex was of degree 0, delete it
            if (vertices.contains(lowestDegreeVertex.get())) {
                vertices.remove(lowestDegreeVertex.get());
            }
        }

        return independentSet;
    }

    private Optional<Vertex<T>> getLowestDegreeVertex(Graph<T, ?> graph) {
        return graph.vertexSet().stream()
                .sorted(this::sortByLowestDegree)
                .findFirst();
    }

    private int sortByLowestDegree(Vertex<T> first, Vertex<T> second) {
        return first.degreeOf() < second.degreeOf() ? -1 : 1;
    }
}
