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

import Vertex from './Vertex';
import Edge from './Edge';
import CONSTS from '../common/constants';
import MathUtil from '../common/MathUtil';
import { shuffleArray } from '../common/eloquent';

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
             * Kuratowski's theorem: if a graph contains a subgraph of K5 or K_3,3
             * then it is non-planar (West, 1996).
             *
             * Wolfram (2009) accounts that most algorithms for checking graph
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

    /*
     * Graph.vertices is an array of objects but from the mathematical point of
     * view it is just a collection of distinct nodes. The Matrix module works
     * with plain lists too, so this method maps the list of objects to a list
     * of string names of those vertices.
     *
     */
    getVerticesList() {
        return this.vertices.map(vertex => {
            return vertex.name;
        });
    }

    /*
     * Takes a list of names of vertices and checks if the graph
     * has such vertices. Returns the number of vertices that
     * were not found by the names.
     */
    checkNumNonExistingVertices(verList=[]) {
        let nonExistingVertices = verList.map(v => {
            if (-1 === this.lookupVertex(this.vertices, v)) {
                return 1; // vertex not found
            } else {
                return 0; // vertex found
            }
        })
        .filter(val => {
            return val === 1 ? true : false;
        });

        return nonExistingVertices.length;
    }

    /*
     * Takes an array of form [v1, v2]
     */
    addEdge(edge) {
        if (this.checkNumNonExistingVertices(edge) > 0) {
            throw new Error("Cannot add the edge! Some vertices do not exist in the graph");
            return;
        }

        try {
            const e = new Edge(edge);
            return this.edges.push(e);
        }catch(e) {
            return null;
        }
    };

    /*
     * returns an array of adjacent vertices' names
     *
     * v = return vertices that are adjacent to this given vertex
     */
    adj(v, e=null) {
        let list = [];

        if (e === null) {
            e = this.edges;
        }

        e.forEach(e => {
            let pos = e.connects.indexOf(v);
            if (pos > -1 && pos === 0) {
                list.push(e.connects[1]);
            }
            if (pos > -1 && pos === 1) {
                list.push(e.connects[0]);
            }
        });

        return list;
    }

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
     * Isomorphism of two graphs is a relation where the two graphs are said
     * to be isomorphic if there exists a one-to-one correspondence between
     * their vertex sets such that if two vertices are adjacent in one graph
     * they are adjacent in the other graph as well (Trudeau, 1993, p. 35) or
     * simply when the second graph is the copy of the first one (McKay, 1980
     * in Kao, 2008, p. 373).
     *
     * Let there be graphs G1 = (V1, E1) and G2 = (V2, E2). If G1 = G2 the
     * isomorphism  is automorphism of G1 (McKay, 1980 in Kao, 2008, p. 373).
     */
    isIsomorphic(graphB) {

    }

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
     * Returns the degree of a vertex
     *
     */
    deg(v, e) {
        return this.adj(v, e).length;
    }

    /*
     * Returns the name of the lowest degree vertex of a given graph
     *
     */
    findLowestDegreeVertex(vertexObjectList, edgeObjectList) {
        let lowest = null;
        let deg = 999999;

        vertexObjectList = shuffleArray(vertexObjectList);

        vertexObjectList.forEach(vertex => {
            let d = this.deg(vertex.name, edgeObjectList);
            if (d < deg) {
                deg = d;
                lowest = vertex;
            }
        });

        return lowest;
    }

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

export { Graph, getGraph };
