package eu.libal.intrographs.presentation;

/**
 * A click, mouse move, etc. have different meanings whether the canvas state is panning, adding a vertex and so on. For
 * instance, if the state is panning then a mouse click and move translates the canvas' origin.
 *
 */
public enum CanvasStates {
    PANNING("Panning"),
    TRANSLATING_VERTEX("Translating Vertex"),
    ADDING_VERTEX("Adding Vertex"),
    ADDING_EDGE("Adding Edge");

    String value;

    CanvasStates(String v) {
        value = v;
    }
}
