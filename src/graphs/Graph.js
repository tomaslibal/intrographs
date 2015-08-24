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

import Vertex from "Vertex";
import Edge from "Edge";
import CONSTS from "../common/constants";

class Graph {

    constructor() {
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
             * A graph is in one piece if is it connected, i.e. every vertex is
             * connected by a walk. A walk is a sequence of vertices v1, v2, ..., vm
             * (vertices do not have to be distinct in a walk) where v1 is connected
             * by an edge to v2, v2 is connected to v3 and v_(m-1) is connected to
             * v_m.
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
    addVertex(props) {
        let v = new Vertex(props);

        this.vertices.push(v);

        return v;
    };

    addEdge(edge) {
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
    getName() {
        "use strict";

        /*
         * For now the name is returned in its general form which consists of
         * the number of vertices and edges in the graph.
         */
        return "v=" + this.vertices.length + ", e=" + this.edges.length;
    };

    isCycleGraph() {}
    isConnectedGraph() {}
    isNullGraph() {}
    isGearGraph() {}
    isPrismGraph() {}
    isStarGraph() {}
    isWheelGraph() {}
    isAntiprismGraph() {}

    /*
     * Each vertex has a label so that the edges can be defined as a set of two
     * vertices like { A, B } for example. This method takes the label of the
     * vertex and returns that Vertex object.
     */
    lookupVertex(graphVertices, label) {
        "use strict";

        var l = graphVertices.length;
        var i = 0;

        /*
         * NB: to improve efficiency it might be good to explore if this lookup
         * can be implemented using a hash table. Then looking up a vertex would
         * be as simple as checking if(graphVerticesTable[lable])...
         */
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
    spaceOutVerticesRandom(graphVertices) {
        "use strict";

        var i = 0;

        for(; i < graphVertices.length; i++) {
            /*
             * NB: the range depends on the size of the canvas. This object does not
             * know about the canvas's dimensions so these values should be
             * injected.
             */
            graphVertices[i].x = mathUtil.getRandomArbitrary(0, 500);
            graphVertices[i].y = mathUtil.getRandomArbitrary(0, 500);
        }

        return graphVertices;
    };


}


/*
 * getGraph factory returns a new Graph object
 *
 * @return object{Graph}
 */
function getGraph() {
    "use strict";

    /*
     * calling 'new' inside a function tighly couples Graph to this function
     * however, the Graph class is defined in this file so it is fine for now.
     */
    var g = new Graph();

    /*
     * By definition, the vertex set is a non empty set so we assign one vertex
     * when the graph's created.
     */
    g.addVertex({ name: "A" });

    return g;
}

/*
 * NODE_JS_ONLY
 */
if ("undefined" !== typeof process && process.env) {
    var mathUtil = require('./common/mathUtil');
}
/*
 * #NODE_JS_ONLY
 */


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
