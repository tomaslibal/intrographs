/*
 * A graph in this software is understood as a mathematical object that consists
 * of two sets:
 *
 *   a) set of vertices, e.g. { A, B, C, D, E, F }
 *   b) set of edges, e.g. { { A, B }, { A, C }, { B, D } }
 *
 * To get an elementary graph N_1 (Null Graph of one 1 vertex) call the factory
 * function `void getGraph()`.
 */
function getGraph() {
    "use strict";

    var g = new Graph();

    g.addVertex({ name: "A" });

    return g;
}

/*
 * Graph JavaScript constructor
 *
 */
function Graph() {
    "use strict";

    this.vertices = [];
    this.edges = [];
}

Graph.prototype.addVertex = function(props) {
    "use strict";

    var v = new Vertex(props);

    this.vertices.push(v);

    return v;
};

/*
 * Vertex JavaScript constructor
 *
 * Each vertex of a graph is an object constructed by this function.
 */
function Vertex(props) {
    "use strict";

    /*
     * Ensure that props is not null but rather is an object;
     */
    props = props || {};

    /*
     * For graphical purposes, a vertex has [x, y] coordinates in the pixel
     * space associated with it.
     */
    this.x = props.x || 0;
    this.y = props.y || 0;

    /*
     * Arbitrary name
     *
     */
    this.name = props.name || null;
 }
