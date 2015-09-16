/*
 * Import the Software Under Test
 */
import { Graph, getGraph } from "../../src/graphs/Graph";

let assert = require("assert");
let chai = require("chai");

describe('Graph', () => {
    "use strict";

    describe('constructor', () => {
        it('is defined and is a function', function() {
            assert.equal(true, "function" === typeof Graph);
        });

        it('returns a new object of Graph', () => {
            let tmp = new Graph();
            assert.equal(true, true === tmp instanceof Graph);
        });

        it('has an empty set of vertices', () => {
            let g = new Graph();
            assert.equal(0, g.vertices.length);
            assert.equal(0, g.edges.length);
        });
    });

    describe('vertices', () => {

        let g;

        beforeEach(() => {
            g = new Graph();
        });

        it('adds a new Vertex', () => {
            g.addVertex();
            assert.equal(1, g.vertices.length);
            assert.equal(0, g.edges.length);
        });

        it('adds a second Vertex', () => {
            g.addVertex();
            g.addVertex();
            assert.equal(2, g.vertices.length);
            assert.equal(0, g.edges.length);
        });

        it('assigns the new vertex\'s name as supplied', () => {
            g.addVertex({'name':'Sun'});
            assert.equal('Sun', g.vertices[0].name);
        });

        it('getVerticesList() returns an array of names of all vertices', () => {
            g.addVertex({'name': 'foo'});
            g.addVertex({'name': 'Z'});

            assert.deepEqual(['foo', 'Z'], g.getVerticesList());
        });
    });

    describe('edges', () => {
        let g;

        beforeEach(() => {
            g = new Graph();
        });

        it('adds a new edge if both vertices exist', () => {
            g.addVertex({'name':'A'});
            g.addVertex({'name':'B'});

            g.addEdge(['A', 'B']);
            assert.equal(1, g.edges.length);
        });

        it('adds a second edge if all vertices exist', () => {
            g.addVertex({'name':'A'});
            g.addVertex({'name':'B'});
            g.addVertex({'name':'C'});

            g.addEdge(['A', 'B']);
            g.addEdge(['A', 'C']);

            assert.equal(2, g.edges.length);
        });

        it('won\'t add an edge if at least one of the vertices does not exist', () => {
            g.addVertex({'name':'Foo'});

            chai.expect(() => {
                g.addEdge(['Foo', 'Bar']);
            }).to.throw('Cannot add the edge! Some vertices do not exist in the graph');

            assert.equal(0, g.edges.length);
        });

        it('won\'t add an edge if neither vertex exists', () => {
            chai.expect(() => {
                g.addEdge(['Minky', 'Binky'])
            }).to.throw('Cannot add the edge! Some vertices do not exist in the graph');

            assert.equal(0, g.edges.length);
        });
    });

    describe('adj(v)', () => {
        let g;

        beforeEach(() => {
            g = new Graph();
        });

        it('finds all adjacent vertices to vertex v', () => {
            g.addVertex({'name':'A'});
            g.addVertex({'name':'B'});
            g.addVertex({'name':'C'});
            g.addVertex({'name':'D'});
            g.addVertex({'name':'E'});

            g.addEdge(['A', 'B']);
            g.addEdge(['A', 'D']);
            g.addEdge(['B', 'D']);
            g.addEdge(['B', 'E']);
            g.addEdge(['B', 'C']);

            const r = g.adj('B');

            chai.assert.deepEqual(r, ['A', 'D', 'E', 'C']);
       });
    });

});

