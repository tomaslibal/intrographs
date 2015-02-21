/*
 * Enables an interactive layer over a graph that is drawn into a canvas.
 *
 * E.g.: on click, the nearest vertex of the graph is 'grabbed' and for mousemove
 * action the vertex is being dragged on the canvas to a new cursor position.
 *
 * Additionally, this layer should add controls to add new vertices and edges
 * and add means to delete the existing ones.
 */
function InteractiveLayer(graph, canvas)
{

}

if ("undefined" !== typeof process && process.env) {
    module.exports = InteractiveLayer;
}
