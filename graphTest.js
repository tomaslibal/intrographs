/*
 * Test subject
 */
var Graph = require("./graph").Graph;

/*
 * Test dependencies
 */
var assert = require("assert");

/*
 * Test the Graph class
 */
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

