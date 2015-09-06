// import s.u.t.
import HTMLControls from "../../../src/presentation/html/HTMLControls";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLControls', () => {

	let ctrl = null;

	let mockNewElement = {
		innerHTML: ''
	};

	let mockDocument = {
		createElement: sinon.stub().returns(mockNewElement)
	};

	beforeEach(() => {
		ctrl = new HTMLControls(mockDocument);

		sinon.spy(ctrl, 'createElementAppend');
	});

	describe('contructor', () => {
		it('takes one argument which is the document object', () => {
			chai.assert.property(ctrl, 'document');
			chai.assert.deepEqual(ctrl.document, mockDocument);
		});
	});

	describe('createElementAppend', () => {
		it('creates a new element of specified type and appends it to the parent element', () => {
			let mockParent = {
				appendChild: sinon.stub()
			};

			ctrl.createElementAppend('input', mockParent);

			assert(mockDocument.createElement.calledWith('input'));
			assert(mockParent.appendChild.calledWith(mockNewElement));
		});

		it('returns the newly created element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let element = ctrl.createElementAppend('input', mockParent);
			chai.assert.deepEqual(element, mockNewElement);
		});
	});

	describe('createLabelAppend', () => {
		it('creates a <p> element, assigns innerHTML as the label and appends it to the parent', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			sinon.spy(mockParent, 'appendChild');

			ctrl.createLabelAppend('LabelCaption', mockParent);

			assert(ctrl.createElementAppend.calledWith('p', mockParent));
			chai.assert.equal(mockNewElement.innerHTML, 'LabelCaption');
		});
		it('returns the newly created <p> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let labelElement = ctrl.createLabelAppend('LabelCaption', mockParent);
			
			chai.assert.deepEqual(labelElement, mockNewElement);
		});
	});

	describe('render method', () => {
		it('invokes renderAddVertexForm and renderAddEdgeForm', () => {
			sinon.spy(ctrl, 'renderAddVertexForm');
			sinon.spy(ctrl, 'renderAddEdgeForm');

			ctrl.render();
			assert(ctrl.renderAddVertexForm.calledOnce);
			assert(ctrl.renderAddEdgeForm.calledOnce);
		});
	});

});