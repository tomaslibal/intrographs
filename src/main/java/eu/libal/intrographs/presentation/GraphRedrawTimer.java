package eu.libal.intrographs.presentation;

import eu.libal.intrographs.common.MessageBus;
import eu.libal.intrographs.graphs.edge.Edge;
import javafx.animation.AnimationTimer;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class GraphRedrawTimer extends AnimationTimer {

    private final AtomicReference<Boolean> needsRedraw = new AtomicReference<>(false);
    private final GraphRenderer<Integer, Edge> graphRenderer;
    private final MessageBus messageBus;

    private final Semaphore redrawLock = new Semaphore(1);

    public GraphRedrawTimer(GraphRenderer<Integer, Edge> graphRenderer, MessageBus messageBus) {
        this.graphRenderer = graphRenderer;
        this.messageBus = messageBus;

        this.messageBus.subscribe("renderer.update", msg -> {
            needsRedraw.set(true);
            start();
        });
    }

    @Override
    public void handle(long now) {
        Boolean redraw = needsRedraw.getAndSet(false);

        if (redraw) {
            try {
                redrawLock.acquire();
                graphRenderer.render();
                redrawLock.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
