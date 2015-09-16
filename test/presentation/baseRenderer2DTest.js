// import s.u.t.
import BaseRenderer2D from '../../src/presentation/BaseRenderer2D';

import { mockHTMLElement as mockCanvas, mockCtx2d as mockCtx } from '../mocks/htmlMocks';

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('BaseRenderer2D', () => {

	let rend = null;

	beforeEach(() => {
		rend = new BaseRenderer2D();
	});

	it('.clearCanvas() clears the whole canvas by calling clearRect on the context2D', () => {
		rend.setCanvas(mockCanvas);
		rend.clearCanvas(mockCtx);

		assert(mockCtx.clearRect.calledWith(0, 0, mockCanvas.width, mockCanvas.height));
	});
});