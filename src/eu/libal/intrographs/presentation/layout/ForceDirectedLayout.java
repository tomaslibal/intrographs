package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.util.Pair;

import java.util.Set;
import java.util.concurrent.Semaphore;

public class ForceDirectedLayout implements Runnable {

    private final GraphRenderer<Integer, Edge<Integer>> graphRenderer;
    private final MessageBus messageBus;
    private final Semaphore canUpdateLayout;
    private final Double K_CONSTANT;
    private final Double MAX_INC = 10d;
    private final Double MAX_FORCE = 5000d;
    private int temperature = 100;

    public ForceDirectedLayout(GraphRenderer<Integer, Edge<Integer>> graphRenderer, MessageBus messageBus, Semaphore canUpdateLayout) {
        this.graphRenderer = graphRenderer;
        this.messageBus = messageBus;
        this.canUpdateLayout = canUpdateLayout;
        K_CONSTANT = Math.sqrt(
                (graphRenderer.getCanvas().getWidth() * graphRenderer.getCanvas().getHeight())
                        /
                graphRenderer.getVertexShapes().size()
        );
    }

    private int getTemperature() {
        return temperature;
    }

    private void cool() {
        temperature--;
    }

    private Pair<Double, Double> forceAttractive(Pair<Double, Double> d) {
        Double xx = d.getKey() * d.getKey();
        Double yy = d.getValue() * d.getValue();

        xx /= K_CONSTANT;
        yy /= K_CONSTANT;

        return new Pair<>(xx, yy);
    }

    private Pair<Double, Double> forceRepulsive(Pair<Double, Double> d) {
        Double x = (1 / Math.abs(d.getKey())) * (K_CONSTANT*K_CONSTANT);
        Double y = (1 / Math.abs(d.getValue())) * (K_CONSTANT*K_CONSTANT);

        return new Pair<>(x, y);
    }


    private Double normalizeForce(Double force) {
//            return force;
        return (force / MAX_FORCE) * MAX_INC;
    }

    private Pair<Double, Double> normalizeVector(Pair<Double, Double> v) {
        Double x = normalizeForce(v.getKey());
        Double y = normalizeForce(v.getValue());

        return new Pair<>(x, y);
    }

    @Override
    public void run() {
        int i = 0;
        int t = 100;
        while(i < 50) {
            Set<VertexShape2D> vertexShapes = graphRenderer.getVertexShapes();

            vertexShapes.forEach(vertexShape2D -> {
                // get adjacent shapes and update their x,y coords
                vertexShape2D.getVertex().getAdjacentVertices()
                        .stream()
                        .map(adjVertex -> {
                            return vertexShapes.stream()
                                    .filter(shape -> shape.getVertex().equals(adjVertex))
                                    .findFirst()
                                    .get();
                        })
                        .forEach(adjShape -> {
                            Pair<Double, Double> difference = getDifference(vertexShape2D, adjShape);
                            Pair<Double, Double> normalizedForce = normalizeVector(forceRepulsive(difference));
                            updateVertexPosition(vertexShape2D, difference, normalizedForce, 1);
                        });

                messageBus.emit("renderer.update", "update");
            });

            graphRenderer.getEdgeShapes()
                    .forEach(edgeShape2D -> {
                        Pair<Double, Double> difference = getDifference(edgeShape2D.getSourceVertexShape2D(), edgeShape2D.getTargetVertexShape2D());
                        Pair<Double, Double> normalizedForce = normalizeVector(forceAttractive(difference));
                        updateVertexPosition(edgeShape2D.getSourceVertexShape2D(), difference, normalizedForce, -1);
                        updateVertexPosition(edgeShape2D.getTargetVertexShape2D(), difference, normalizedForce, 1);
                    });

            vertexShapes.forEach(vertexShape2D -> {
                Integer x = vertexShape2D.getX();
                Integer y = vertexShape2D.getY();

                Integer displacementX = vertexShape2D.getDisplacement().getX();
                x = x + getDisplacementDirection(displacementX) * Math.min(displacementX, getTemperature());
                double width = graphRenderer.getCanvas().getWidth();
                x = (int) Math.round(Math.min(width / 2, Math.max(-width/2, x)));

                Integer displacementY = vertexShape2D.getDisplacement().getY();
                y = y + (getDisplacementDirection(displacementY)) * Math.min(displacementY, getTemperature());
                double height = graphRenderer.getCanvas().getHeight();
                y = (int) Math.round(Math.min(height/2, Math.max(-height/2, y)));

                vertexShape2D.setX(x);
                vertexShape2D.setY(y);

                vertexShape2D.setDisplacementX(0);
                vertexShape2D.setDisplacementY(0);
            });

            messageBus.emit("renderer.update", "render");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            cool();
        }
        canUpdateLayout.release();
    }

    private int getDisplacementDirection(Integer displacement) {
        if (displacement == 0) {
            return 0;
        }

        return displacement/Math.abs(displacement);
    }

    private void updateVertexPosition(VertexShape2D vertexShape2D, Pair<Double, Double> difference, Pair<Double, Double> normalizedForce, int signChange) {
        Integer x = vertexShape2D.getDisplacement().getX();
        Integer y = vertexShape2D.getDisplacement().getY();

        vertexShape2D.setDisplacementX(x + (signChange * (int) Math.round((difference.getKey() / Math.abs(difference.getKey()) * normalizedForce.getKey()))));
        vertexShape2D.setDisplacementY(y + (signChange * (int) Math.round((difference.getValue() / Math.abs(difference.getValue()) * normalizedForce.getValue()))));
    }

    private Pair<Double, Double> getDifference(VertexShape2D v, VertexShape2D u) {
        Double x = (double) v.getCoords().getX() - u.getCoords().getX();
        Double y = (double) v.getCoords().getY() - u.getCoords().getY();

        return new Pair<>(x, y);
    }
}
