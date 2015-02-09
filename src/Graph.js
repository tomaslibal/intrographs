/*
 * A graph in this software is understood as a mathematical object that consists
 * of two sets:
 *
 *   a) set of vertices, e.g. { A, B, C, D, E, F }
 *   b) set of edges, e.g. { { A, B }, { A, C }, { B, D } }
 *
 * To get an elementary graph N_1 (Null Graph of one 1 vertex) call the factory
 * function `void getGraph()`. This returns an object which is an instance of
 * `Graph`.
 */

/*
 * Constants definitions
 *
 */
var CONSTS = {
    /*
     * Used in Graph.properties. Unknown is assigned to properties like is 
     * the graph planar, before these properties are calculated/resolved.
     */
    UNKNOWN: -1
};

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

    this.properties = {
        /*
         * A graph is planar if it can be drawn in a 2D space without edge
         * crossings. More precisely, it is planar if it is isomorphic to 
         * a graph that can be drawn without edge crossings (Trudeau (1993, p. 64)).
         *
         * Euler first described the relation between the number of vertices, 
         * edges and faces and planar graphs (Nishizeki and Chiba(2008), p. vii). 
         * 
         * Kuratowski's theorem: ...
         *
         * Wolfram (..) accounts that most algorithms for checking graph 
         * planarity are difficult to implement and have running time O(n^3)
         * citing Auslander and Parter (1961) (Skiena 1990, p. 247). On the
         * other hand, Nishizeki and Chiba say that Hopcroft and Tarjan have
         * reported on an linear time algorithm that tests planarity of a graph
         * (2008, p. vii).
         *
         */
        planar: CONSTS.UNKNOWN,
        /*
         * A graph is in one piece if 
         */
        inOnePiece: CONSTS.UNKNOWN
    };
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

Graph.prototype.addEdge = function(edge) {
    "use strict";

    return this.edges.push(edge);
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
 * Each vertex has a label so that the edges can be defined as a set of two
 * vertices like { A, B } for example. This method takes the label of the 
 * vertex and returns that Vertex object.
 */
Graph.prototype.lookupVertex = function(graphVertices, label) {
    "use strict";

    var l = graphVertices.length;
    var i = 0;
    
    for(; i < l; i++) {
        if (graphVertices[i].name === label) {
            return graphVertices[i];
        }
    }

    return -1;
};

/*
 * takes a vertex set and assigns each vertex a pair of random x, y coordinates
 *
 * @param Vertex[] graphVertices
 * @return Vertex[]
 */
Graph.prototype.spaceOutVerticesRandom = function(graphVertices) {
    "use strict";

    var i = 0;

    for(; i < graphVertices.length; i++) {
        /*
         * NB: the range depends on the size of the canvas. This object does not
         * know about the canvas's dimensions so these values should be 
         * injected.
         */
        graphVertices[i].x = this.getRandom(0, 500);
        graphVertices[i].y = this.getRandom(0, 500);
    }

    return graphVertices;
};

/*
 * Utility function to get an arbitrary random number
 */
Graph.prototype.getRandom = function(min, max) {
    "use strict";

    return Math.ceil((Math.random() * (max - min))) + min;
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
