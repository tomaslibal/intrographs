// import s.u.t.
import { dfs } from '../../src/search/depthFirstSearch';

import { Graph } from '../../src/graphs/Graph';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('depthFirstSearch', () => {

	let graph;

	beforeEach(() => {
		 graph = new Graph();
	});

	it('visits D before C to be depth-first', () => {
		graph.addVertex({ name: "A" });
		graph.addVertex({ name: "B" });
		graph.addVertex({ name: "C" });
		graph.addVertex({ name: "D" });

		graph.addEdge(["A", "B"]);
		graph.addEdge(["A", "C"]);
		graph.addEdge(["B", "D"]);

		let path = [];

		dfs(graph, 'C', path);

		chai.assert.deepEqual(path, [['A', 'B'], ['B', 'D'], ['A', 'C']]);
	});

});