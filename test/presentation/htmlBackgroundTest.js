// import s.u.t.
import HTMLBackground from "../../src/presentation/HTMLBackground";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLBackground', () => {

	let bg = null;

	beforeEach(() => {
		bg = new HTMLBackground();
	});

	it('has properties height and width', () => {
		chai.assert.property(bg, 'height');
		chai.assert.property(bg, 'width');
	});

});