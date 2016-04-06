package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.Coordinates2D;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 *
 */
public class RandomGraphLayout implements Runnable {
    private final GraphRenderer<Integer, Edge> graphRenderer;
    private final MessageBus messageBus;
    private final Semaphore canUpdateLayout;
    public final static int NUM_STEPS = 25;

    public RandomGraphLayout(GraphRenderer<Integer, Edge> graphRenderer, MessageBus messageBus, Semaphore canUpdateLayout) {
        this.graphRenderer = graphRenderer;
        this.messageBus = messageBus;
        this.canUpdateLayout = canUpdateLayout;
    }

    @Override
    public void run() {
        Set<VertexShape2D<Integer>> vertexShapes = graphRenderer.getVertexShapes();
        Double canvasWidth = graphRenderer.getCanvas().getWidth();
        Double canvasHeight = graphRenderer.getCanvas().getHeight();
        Map<VertexShape2D, Coordinates2D> finalCoordinates = new HashMap<>();

        // choose new destination x, y coords
        vertexShapes.stream().forEach(vertexShape2D -> {
            Double x = Math.random() * canvasWidth;
            Double y = Math.random() * canvasHeight;

            finalCoordinates.put(vertexShape2D, new Coordinates2D(x, y));
        });

        for(int i = 0; i < NUM_STEPS; i++) {
            int numStepsRemaining = NUM_STEPS - i;

            vertexShapes.forEach(vertexShape2D -> {
                Double x = vertexShape2D.getX();
                Double y = vertexShape2D.getY();

                Coordinates2D destination = finalCoordinates.get(vertexShape2D);

                Double dx = (destination.getX() - x) / numStepsRemaining;
                Double dy = (destination.getY() - y) / numStepsRemaining;

                vertexShape2D.setX(x + dx);
                vertexShape2D.setY(y + dy);
            });

            messageBus.emit("renderer.update", "update");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        canUpdateLayout.release();
    }
}
