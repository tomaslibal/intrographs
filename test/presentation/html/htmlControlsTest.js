// import s.u.t.
import HTMLControls from "../../../src/presentation/html/HTMLControls";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLControls', () => {

	let ctrl = null;

	let mockDocument = {

	};

	beforeEach(() => {
		ctrl = new HTMLControls(mockDocument);
	});

	describe('contructor', () => {
		it('takes one argument which is the document object', () => {
			chai.assert.property(ctrl, 'document');
			chai.assert.deepEqual(ctrl.document, mockDocument);
		});
	});

});