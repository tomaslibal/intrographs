/*
 * Import the Software Under Test
 */
import Vertex from '../../src/graphs/Vertex';

let assert = require('assert');
let chai = require('chai');

describe('Vertex', () => {
	it(`constructor takes an optional object
		{ name: String, label: String }
		'as the first argument`, () => {
		let v = new Vertex({ name: 'v1', label: 'Saphire'});

		chai.assert.equal(v.name, 'v1');
		chai.assert.equal(v.label, 'Saphire');
	});

	it('constructor creates a new Symbol and assigns it to this.symbol', () => {
		let v = new Vertex({ name: 'v1' });

		chai.assert.property(v, 'symbol');
		chai.assert.equal(typeof v.symbol, 'symbol');
	});
});