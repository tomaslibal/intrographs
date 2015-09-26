// import s.u.t
import { spanTree } from '../../src/search/spanningTree';

import { Graph } from '../../src/graphs/Graph';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('Spanning Tree', () => {

	let g;

	beforeEach(() => {
		g = new Graph();

		g.addVertex({ name: "A" });
		g.addVertex({ name: "B" });
		g.addVertex({ name: "C" });
		g.addVertex({ name: "D" });

		g.addEdge(["A", "B"]);
		g.addEdge(["A", "D"]);
		g.addEdge(["B", "C"]);
		g.addEdge(["C", "D"]);
	});

	it('finds a span tree in a cyclic graph C_4 and a given start node "A"', () => {
		const tree = spanTree(g, 'A');

		chai.assert.deepEqual(tree, ['A', 'B', 'C', 'D']);
	});

	it('throws an error if given a start node that does not exist in the graph', () => {
		const throws = () => {
			const tree = spanTree(g, 'X')
		};

		chai.expect(throws).to.throw('Start node does not exist');
	});

});
