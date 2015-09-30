// import s.u.t.
import GraphRenderer2D from '../../src/presentation/GraphRenderer2D';

import Vertex from '../../src/graphs/Vertex';

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

	describe('checkVertexCollision', () => {
		let v1;
		let v2;

		beforeEach(() => {
			v1 = new Vertex({'name': 'v1'});
			v2 = new Vertex({'name': 'v2'});
		});
		it('returns true if two vertices collide', () => {
			v1.x = 100;
			v1.y = 200;
			v2.x = 100;
			v2.y = 200;

			chai.assert.equal(graphRenderer.checkVertexCollision(v1, v2), true);
		});
		it('returns true if two vertices are too close to each other (diff x/y < 5px)', () => {
			v1.x = 101;
			v1.y = 202;
			v2.x = 103;
			v2.y = 199;

			chai.assert.equal(graphRenderer.checkVertexCollision(v1, v2), true);
		});

		it('returns false if two vertices do not collide', () => {
			v1.x = 100;
			v1.y = 100;
			v2.x = 300;
			v2.y = 300;

			chai.assert.equal(graphRenderer.checkVertexCollision(v1, v2), false);
		});
	});

	describe('findRandomCoordsAtGivenDistance', () => {
		graphRenderer = new GraphRenderer2D();
		let [x, y] = graphRenderer.findRandomCoordsAtGivenDistance(100, 100, 15);

		// x \in [85, 115]
		// y \in [85, 115]
		const dx = Math.abs(x - 100);
		const dy = Math.abs(y - 100);

		chai.assert.isBelow(dx, 16);
		chai.assert.isBelow(dy, 16);
	});
});