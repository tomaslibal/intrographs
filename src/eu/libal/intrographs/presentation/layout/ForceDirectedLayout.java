package eu.libal.intrographs.presentation.layout;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.edge.Edge;
import eu.libal.intrographs.presentation.GraphRenderer;
import eu.libal.intrographs.presentation.shapes.VertexShape2D;
import javafx.util.Pair;

import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Layout which calculates attractive and repulsive forces on edges and vertices respectively to find a minimal energy
 * state.
 *
 * attractive f_a(x) = x*x / k
 * repulsive f_r(x) = k*k / |x|
 *
 * where x is the euclidean distance between two vertices.
 */
public class ForceDirectedLayout implements Runnable {

    /**
     * Graph renderer is used to obtain the canvas shapes of the vertices and edges.
     */
    private final GraphRenderer<Integer, Edge> graphRenderer;

    /**
     * This message bus is used to request a graph canvas redraw
     */
    private final MessageBus messageBus;

    /**
     * Shared semaphore. The current thread must release this lock otherwise other threads will be blocked from updating
     * the layout.
     */
    private final Semaphore canUpdateLayout;

    /**
     * Constant multiplier of the ideal space between vertices k
     */
    private final static double C = 100;

    /**
     * The ideal space between vertices
     */
    private Double k;
    private Double speed = 1d;
    private final int NUM_STEPS = 50;
    private double temperature;

    public ForceDirectedLayout(GraphRenderer<Integer, Edge> graphRenderer, MessageBus messageBus, Semaphore canUpdateLayout) {
        this.graphRenderer = graphRenderer;
        this.messageBus = messageBus;
        this.canUpdateLayout = canUpdateLayout;
    }

    private double getTemperature() {
        return temperature;
    }

    private void cool() {
        temperature -= temperature / NUM_STEPS;
    }

    private Pair<Double, Double> getDxAndDy(VertexShape2D v, VertexShape2D u) {
        Double x = (double) v.getCoords().getX() - u.getCoords().getX();
        Double y = (double) v.getCoords().getY() - u.getCoords().getY();

        return new Pair<>(x, y);
    }

    private double getDistance(Pair<Double, Double> deltas) {
        return Math.sqrt(deltas.getKey()*deltas.getKey() + deltas.getValue()*deltas.getValue());
    }

    @Override
    public void run() {
        double area = 10000;
        k = C * Math.sqrt( area / (1 + graphRenderer.getVertexShapes().size()) );
        temperature = 15;
        speed = 1d;
        Double maxDisplace = Math.sqrt(C * area / 10);

        for (int i = 0; i < NUM_STEPS; i++) {
            Set<VertexShape2D<Integer>> vertexShapes = graphRenderer.getVertexShapes();

            vertexShapes.forEach(vertexShape2D -> {
                vertexShape2D.setDisplacementX(0);
                vertexShape2D.setDisplacementY(0);

                vertexShapes.forEach(anotherVertexShape2D -> {
                    if (!vertexShape2D.equals(anotherVertexShape2D)) {
                        Pair<Double, Double> dxAndDy = getDxAndDy(vertexShape2D, anotherVertexShape2D);
                        Double dist = getDistance(dxAndDy);
                        Double fRepulsive = (k * k) / Math.abs(dist);

                        vertexShape2D.addDisplacementX(getDisplacementDirection(dxAndDy.getKey()) * fRepulsive);
                        vertexShape2D.addDisplacementY(getDisplacementDirection(dxAndDy.getValue()) * fRepulsive);
                    }
                });

                messageBus.emit("renderer.update", "update");
            });

            graphRenderer.getEdgeShapes()
                    .forEach(edgeShape2D -> {
                        Pair<Double, Double> difference = getDxAndDy(edgeShape2D.getSourceVertexShape2D(), edgeShape2D.getTargetVertexShape2D());
                        Double dist = getDistance(difference);
                        Double fAttractive = dist * dist / k;

                        VertexShape2D source = edgeShape2D.getSourceVertexShape2D();
                        VertexShape2D target = edgeShape2D.getTargetVertexShape2D();

                        if (dist > 0) {
                            source.addDisplacementX(-1* (difference.getKey() / dist *fAttractive));
                            source.addDisplacementY(-1* (difference.getKey() / dist *fAttractive));

                            target.addDisplacementX(difference.getValue() / dist *fAttractive);
                            target.addDisplacementY(difference.getValue() / dist *fAttractive);
                        }
                    });

            vertexShapes.forEach(s -> {
                double d = Math.sqrt(s.getX() * s.getX() + s.getY() * s.getY());
                double fGravity = 0.05f * k * 9.37 * d;
                s.addDisplacementX(-1 * (fGravity * s.getX() / d));
                s.addDisplacementY(-1 * (fGravity * s.getY() / d));

                double speedDivisor = 400;
                s.multDisplacementX(speed / speedDivisor);
                s.multDisplacementY(speed / speedDivisor);
                Double x = s.getX();
                Double y = s.getY();

                Double displX = s.getDisplacement().getX();
                Double displY = s.getDisplacement().getY();
                double d2 = Math.sqrt((displX * displX) + (displY * displY));

                if (d2 > 0) {
                    double limitor = Math.min(maxDisplace * (speed / speedDivisor), d2);
                    Double nx = x + displX / d2 * limitor;
                    Double ny = y + displY / d2 * limitor;
                    s.setX(nx);
                    s.setY(ny);
                }
            });

            cool();

            messageBus.emit("renderer.update", "render");

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        canUpdateLayout.release();
    }

    private double getDisplacementDirection(Double displacement) {
        if (displacement == 0) {
            return 1;
        }

        return displacement/Math.abs(displacement);
    }
}
