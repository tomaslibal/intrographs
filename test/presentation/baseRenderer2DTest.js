// import s.u.t.
import BaseRenderer2D from '../../src/presentation/BaseRenderer2D';

import { mockCanvas, mockCtx2d as mockCtx } from '../mocks/htmlMocks';

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('BaseRenderer2D', () => {

	let rend = null;

	beforeEach(() => {
		rend = new BaseRenderer2D(mockCanvas);
	});

	describe('constructor', () => {
		it('stores the reference to the canvas and gets the 2d context', () => {
			chai.assert.deepEqual(rend.canvas, mockCanvas);
			chai.expect(mockCanvas.getContext.calledWith('2d')).to.be.true;
			chai.assert.deepEqual(rend.ctx, mockCtx);
		});
	});

	it('.clearCanvas() clears the whole canvas by calling clearRect on the context2D', () => {
		rend.setCanvas(mockCanvas);
		rend.clearCanvas(mockCtx);

		assert(mockCtx.clearRect.calledWith(0, 0, mockCanvas.width, mockCanvas.height));
	});

	it('setCanvas stores the references to the canvas', () => {
		rend.setCanvas(mockCanvas);

		chai.assert.deepEqual(rend.canvas, mockCanvas);
	});

	it('stores the CanvasRenderingContext2D as a property when setting a canvas', () => {
		rend.setCanvas(mockCanvas);

		chai.expect(rend.canvas.getContext.calledWith(mockCanvas)).to.be.true;
		chai.assert.property(rend, 'ctx');

	});

	it('clearCanvas uses clearRect to clear all pixels of the canvas', () => {
		sinon.spy(rend, 'pushTranslate');
		sinon.spy(rend, 'popTranslate');

		rend.setCanvas(mockCanvas);

		rend.clearCanvas();

		chai.expect(rend.ctx.clearRect.calledWith(0, 0, rend.canvas.width, rend.canvas.height)).to.be.true;

	});
});