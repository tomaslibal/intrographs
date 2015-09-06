// import s.u.t.
import HTMLControls from "../../../src/presentation/html/HTMLControls";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLControls', () => {

	let ctrl = null;

	let mockNewElement = {
		innerHTML: '',
		id: '',
		'type': ''
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

	describe('createButtonAppend', () => {
		it('creates a <button> element, assigns innerHTML as the label and appends it to the parent', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			sinon.spy(mockParent, 'appendChild');

			ctrl.createButtonAppend('ButtonCaption', mockParent);

			assert(ctrl.createElementAppend.calledWith('button', mockParent));
			chai.assert.equal(mockNewElement.innerHTML, 'ButtonCaption');
		});
		it('returns the newly created <button> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let buttonElement = ctrl.createButtonAppend('ButtonCaption', mockParent);

			chai.assert.deepEqual(buttonElement, mockNewElement);
		});
	});

	describe('createInputAppend', () => {
		it(`creates an <input> element, assigns "id" and "type" attributes as specified and appends it
			to the parent`, () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			sinon.spy(mockParent, 'appendChild');

			ctrl.createInputAppend({ id: 'foo', 'type': 'text' }, mockParent);

			assert(ctrl.createElementAppend.calledWith('input', mockParent));
			chai.assert.equal(mockNewElement.id, 'foo');
			chai.assert.equal(mockNewElement.type, 'text');
		});
		it('returns the newly created <input> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let inputElement = ctrl.createInputAppend({ id: 'foo', 'type': 'text' }, mockParent);

			chai.assert.deepEqual(inputElement, mockNewElement);
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

	describe('renderAddVertexForm', () => {
		it('creates a addVertexForm property if it does not exist', () => {
			chai.assert.notProperty(ctrl, 'addVertexForm');

			ctrl.renderAddVertexForm();

			chai.assert.property(ctrl, 'addVertexForm');
		});
	});

});