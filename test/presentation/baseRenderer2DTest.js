// import s.u.t.
import BaseRenderer2D from "../../src/presentation/BaseRenderer2D";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('BaseRenderer2D', () => {

	let rend = null;

	let mockCtx = {
		clearRect: sinon.stub()
	};

	let mockCanvas = {
		width: 123,
		height: 123
	};

	beforeEach(() => {
		rend = new BaseRenderer2D();
	});

	it('.clearCanvas() clears the whole canvas by calling clearRect on the context2D', () => {
		rend.setCanvas(mockCanvas);
		rend.clearCanvas(mockCtx);

		assert(mockCtx.clearRect.calledWith(0, 0, mockCanvas.width, mockCanvas.height));
	});
});