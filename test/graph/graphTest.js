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

    describe('dev(v)', () => {
       it('returns the degree of a vertex', () => {
           let g = new Graph();
           g.addVertex({'name':'A'});
           g.addVertex({'name':'B'});
           g.addVertex({'name':'C'});
           g.addVertex({'name':'D'});

           g.addEdge(['A', 'B']);
           g.addEdge(['A', 'C']);
           g.addEdge(['A', 'D']);

           g.addEdge(['C', 'D']);

           chai.assert.equal(g.deg('A'), 3);
           chai.assert.equal(g.deg('B'), 1);
           chai.assert.equal(g.deg('C'), 2);
           chai.assert.equal(g.deg('D'), 2);
       });
    });

    describe('findLowestDegreeVertex', () => {
        it('correctly finds the lowest degree vertex B', () => {
           let g = new Graph();
           g.addVertex({'name':'A'});
           const lowest = g.addVertex({'name':'B'});
           g.addVertex({'name':'C'});
           g.addVertex({'name':'D'});

           g.addEdge(['A', 'B']);
           g.addEdge(['A', 'C']);
           g.addEdge(['A', 'D']);
           g.addEdge(['C', 'D']);

           chai.assert.deepEqual(g.findLowestDegreeVertex(g.vertices, g.edges), lowest);
        });
    });

    describe('removing a vertex by id', () => {
        it('removes the vertex from the list of vertices', () => {
            let g = new Graph();
            g.addVertex({'name':'A'});

            chai.assert.equal(g.vertices.length, 1);

            g.removeVertexById('A');

            chai.assert.equal(g.vertices.length, 0);
        });

        it('removes all edges incident on the removed vertex', () => {
            let g = new Graph();
            g.addVertex({'name':'A'});
            g.addVertex({'name':'B'});
            g.addVertex({'name':'C'});

            g.addEdge(['A', 'B']);
            g.addEdge(['A', 'C']);
            g.addEdge(['B', 'C']);

            chai.assert.equal(g.edges.length, 3);

            g.removeVertexById('A');

            chai.assert.equal(g.edges.length, 1);
            chai.assert.deepEqual(g.edges[0].connects, ['B', 'C']);
        });

        it('throws an error if no id supplied', () => {
            const throws = () => {
                let g = new Graph();
                g.removeVertexById();
            };

            chai.expect(throws).to.throw("No ID supplied");
        });
    });
});

