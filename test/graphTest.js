/*
 * Import the Software Under Test
 */
import { Graph, getGraph } from "../src/graphs/Graph";

let assert = require("assert");

describe('Graph', function() {
    "use strict";

    describe('constructor', function() {
        it('is defined and is a function', function() {
            assert.equal(true, "function" === typeof Graph);    
        });
        it('returns a new object of Graph', function() {
            var tmp = new Graph();
            assert.equal(true, true === tmp instanceof Graph);
        });
    });

});

