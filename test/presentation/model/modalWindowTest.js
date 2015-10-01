// import s.u.t.
import ModalWindow from "../../../src/presentation/model/ModalWindow";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('ModalWindow', () => {

	let mwin;

	beforeEach(() => {
		mwin = new ModalWindow();
	});

	describe('basic properties', () => {
		it('has (x, y) coords', () => {
			chai.assert.property(mwin, 'x');
			chai.assert.property(mwin, 'y');
		});

		it('has width and height', () => {
			chai.assert.property(mwin, 'width');
			chai.assert.property(mwin, 'height');
		});

		it('is init\'d hidden' , () => {
			chai.assert.equal(mwin.display, false);
		});
	});

});