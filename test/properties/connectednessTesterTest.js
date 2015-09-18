// import s.u.t.
import ConnectednessTest from '../../src/properties/ConnectednessTester';


import { Graph } from '../../src/graphs/Graph';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('Connectednes Tester', () => {

	describe('isConnected', () => {
		it('returns true if the graph is connected', () => {
			let g = new Graph();
			g.addVertex({name:'a'});
			g.addVertex({name:'b'});
			g.addVertex({name:'c'});
			g.addVertex({name:'d'});
			g.addVertex({name:'e'});

			g.addEdge(['a', 'b']);
			g.addEdge(['a', 'c']);
			g.addEdge(['a', 'd']);
			g.addEdge(['c', 'e']);
			g.addEdge(['e', 'd']);

			const res = ConnectednessTest.isConnected(g);
			chai.assert.equal(res, true);
		});
	});

});