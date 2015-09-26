// import s.u.t.
import IsolatedSetFinder from '../../src/properties/IsolatedSetFinder';

import { Graph, __RewireAPI__ as GraphRewire } from '../../src/graphs/Graph';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('IsolatedSetFinder', () => {

	let g;

	let ind1, ind2, ind3, ind4;

	beforeEach('setup a graph', () => {
		let low;

		g = new Graph();

		ind1 = g.addVertex({'name': 'A'});
		g.addVertex({'name': 'B'});
		ind2 = g.addVertex({'name': 'C'});
		g.addVertex({'name': 'D'});
		ind3 = g.addVertex({'name': 'E'});
		ind4 = g.addVertex({'name': 'F'});
		low = g.addVertex({'name': 'G'});
		g.addVertex({'name': 'H'});

		g.addEdge(['A', 'B']);
		g.addEdge(['A', 'D']);
		g.addEdge(['B', 'C']);
		g.addEdge(['D', 'C']);
		g.addEdge(['B', 'E']);
		g.addEdge(['E', 'H']);
		g.addEdge(['H', 'G']);
		g.addEdge(['G', 'F']);
		g.addEdge(['F', 'D']);
	});

	it('finds an isolated set using a random lowest degree vertex', () => {
		// do not reshuffle to remove non-determinism
		GraphRewire.__Rewire__('shuffleArray', function(a){return a;});

		const isolated = IsolatedSetFinder.findRandomIsolatedSet(g);

		chai.assert.deepEqual(isolated, [ind1, ind2, ind3, ind4]);
	});
});