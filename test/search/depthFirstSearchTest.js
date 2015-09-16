// import s.u.t.
import { dfs } from '../../src/search/depthFirstSearch';

import { Graph } from '../../src/graphs/Graph';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('depthFirstSearch', () => {

	let graph;
	let vertexC;

	beforeEach(() => {
		graph = new Graph();

		graph.addVertex({ name: "A" });
		graph.addVertex({ name: "B" });
		vertexC = graph.addVertex({ name: "C" });
		graph.addVertex({ name: "D" });

		graph.addEdge(["A", "B"]);
		graph.addEdge(["A", "C"]);
		graph.addEdge(["B", "D"]);
	});

	it('visits D before C to be depth-first', () => {
		let path = [];

		dfs(graph, 'C', path);

		chai.assert.deepEqual(path, [['A', 'B'], ['B', 'D'], ['A', 'C']]);
	});

	it('finds the existing vertex C', () => {
		const r = dfs(graph, 'C');

		chai.assert.deepEqual(r, vertexC);
	});

});