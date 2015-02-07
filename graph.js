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

/*
 * Creates a new vertex and passes the @param props to Vertex's constructor.
 *
 * @todo add a method that takes a Vertex and pushes it onto Graph.vertices
 * directly. At the moment "new" is called on Vertex inside Graph which tighly
 * couples these two classes/objects together.
 */
Graph.prototype.addVertex = function(props) {
    "use strict";

    var v = new Vertex(props);

    this.vertices.push(v);

    return v;
};

/*
 * Name of the graph is a conventional name based on number of vertices and
 * edges in the graph (or graphs that are isomorphic to that graph).
 *
 * For instance, a graph of one vertex and zero edges is known as N_1. It is 
 * an instance of a null graph. Null graph is a graph N_k of k vertices and 
 * zero edges.
 *
 * C_k...cyclic graphs (cycle graph)
 *    Is a graph on k nodes containing a single cycle through all nodes 
 *    (Weisstein, Eric W. "Cycle Graph." 
 *    http://mathworld.wolfram.com/CycleGraph.html)
 *
 * K_k...connected graphs (complete graph)
 *    In complete graph every vertex is connected to all other vertices.
 *
 * N_k...null graphs (empty graph)
 *    A null graph is a graph with one or more vertices and no edges.
 *
 * X_k...antiprism graph, 
 * ...gear graph, 
 * ...prism graph, 
 * ...star graph, 
 * ...and wheel graph.
 */
Graph.prototype.getName = function() {
    "use strict";

    /* 
     * For now the name is returned in its general form which consists of 
     * the number of vertices and edges in the graph.
     */
    return "v=" + this.vertices.length + ", e=" + this.edges.length;
};

/*
 * Utility function that returns void 0 for methods on objects that have not
 * yet been implemented.
 *
 * @return void
 */
function voidFunction() {
    "use strict";

    return void 0;
}

Graph.prototype.isCycleGraph = voidFunction;
Graph.prototype.isConnectedGraph = voidFunction;
Graph.prototype.isNullGraph = voidFunction;
Graph.prototype.isGearGraph = voidFunction;
Graph.prototype.isPrismGraph = voidFunction;
Graph.prototype.isStarGraph = voidFunction;
Graph.prototype.isWheelGraph = voidFunction;
Graph.prototype.isAntiprismGraph = voidFunction;

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

/*
 * #NODE_JS_ENV
 *
 * For node.js environment only
 */
if ("undefined" !== typeof process && process.env) {
    module.exports.Graph = Graph;
}
/*
 * #/NODE_JS_ENV/
 */
