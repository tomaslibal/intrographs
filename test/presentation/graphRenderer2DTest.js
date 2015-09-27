// import s.u.t.
import GraphRenderer2D from '../../src/presentation/GraphRenderer2D';

let chai = require('chai');
let sinon = require('sinon');

describe('GraphRenderer2D', () => {

	let graphRenderer;

	beforeEach(() => {
		graphRenderer = new GraphRenderer2D();
	});

	describe('constructor', () => {
		it('adds canvas property', () => {
			chai.assert.property(graphRenderer, 'canvas');
		});
		it('adds vertexCl property', () => {
			chai.assert.property(graphRenderer, 'vertexCl');
		});
		it('adds edgeCl property', () => {
			chai.assert.property(graphRenderer, 'edgeCl');
		});
	});

});