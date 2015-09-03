/*
 * Import the Software Under Test
 */
import Edge from "../../src/graphs/Edge";

let assert = require("assert");
let chai = require("chai");

describe('Edge', () => {
	it('keeps an array of the two vertices it connects', () => {
		let e = new Edge(["a", "b"]);
		chai.assert.deepEqual(e.connects, ["a", "b"]);
	});
});