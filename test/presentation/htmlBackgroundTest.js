// import s.u.t.
import HTMLBackground from "../../src/presentation/HTMLBackground";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLBackground', () => {

	let bg = null;
	let mockDocument = {
		createElement: sinon.stub()
	};

	beforeEach(() => {
		bg = new HTMLBackground(mockDocument);
	});

	it('has properties height and width', () => {
		chai.assert.property(bg, 'height');
		chai.assert.property(bg, 'width');
	});



	describe('constructor', () => {
			it('takes one argument which is the document object', () => {
				chai.assert.property(bg, 'document');
				chai.assert.deepEqual(bg.document, mockDocument);
			});

			it('creates a div HTML element that represents the background', () => {
				assert(mockDocument.createElement.calledWith('div'));
				chai.assert.property(bg, 'bg');
			});
	});

});