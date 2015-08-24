/*
 * Import the Software Under Test
 */
import { Graph, getGraph } from "../src/graphs/Graph";

let assert = require("assert");

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
    });

});

