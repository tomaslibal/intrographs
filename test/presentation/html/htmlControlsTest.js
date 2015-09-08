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
		'type': '',
		className: '',
		style: {},
		appendChild(child) { return child; },
		addEventListener: sinon.stub()
	};

	let mockBody = {
		querySelector: sinon.stub().returns(null),
		appendChild: sinon.stub()
	}

	let mockDocument = {
		createElement: sinon.stub().returns(mockNewElement),
		querySelector: sinon.stub(),
		body: mockBody,
		appendChild: sinon.stub()
	};

	let mockWindow = {
		document: mockDocument,
		getComputedStyle: sinon.stub()
	}

	beforeEach(() => {
		ctrl = new HTMLControls(mockWindow);

		sinon.spy(ctrl, 'createElementAppend');
		sinon.spy(ctrl, 'createLabelAppend');
		sinon.spy(ctrl, 'createInputAppend');
		sinon.spy(ctrl, 'createButtonAppend');
		sinon.spy(ctrl, 'createSpanAppend');
		sinon.spy(ctrl.cssStyles, 'setStyle');

		mockDocument.querySelector.withArgs('.addVertexForm').returns(null);
	});

	describe('contructor', () => {
		it('takes one argument which is the window object and assigns .document property to window.document', () => {
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

	describe('createSpanAppend', () => {
		it('creates a <span> element with specified innerHTML and appends it to the parent', () => {
			let mockParent = {
				appendChild(child) { return child }
			};

			sinon.spy(mockParent, 'appendChild');

			ctrl.createSpanAppend({ innerHTML: 'bar' }, mockParent);

			assert(ctrl.createElementAppend.calledWith('span', mockParent));
			chai.assert.equal(mockNewElement.innerHTML, 'bar');
		});
		it('returns the newly created <span> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let spanElement = ctrl.createSpanAppend({ innerHTML: 'foo' }, mockParent);

			chai.assert.deepEqual(spanElement, mockNewElement);
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

	describe('renderAddEdgeForm', () => {
		it('creates "addEdgeForm" property if it does not exist and assigns it a new <div> element with class "addEdgeForm"', () => {
			chai.assert.notProperty(ctrl, 'addEdgeForm');

			ctrl.renderAddEdgeForm();

			chai.assert.property(ctrl, 'addEdgeForm');
			assert(ctrl.document.createElement.calledWith('div'));
			chai.assert.equal(ctrl.addEdgeForm.className, 'addEdgeForm');
		});
		it('creates a new label "Add Edge" and appends it to the <div> element', () => {
			ctrl.renderAddEdgeForm();

			assert(ctrl.createLabelAppend.calledWith('Add Edge', ctrl.addEdgeForm));
		});
		it(`creates two text inputs with IDs "vertex1" and "vertex2" and corresponding two spans
			to give captions to the inputs and appends it to the <div> element`, () => {
			ctrl.renderAddEdgeForm();

			assert(ctrl.createSpanAppend.calledWith({ innerHTML: 'Vertex 1' }, ctrl.addEdgeForm));
			assert(ctrl.createInputAppend.calledWith({ id: 'vertex1', 'type': 'text' }, ctrl.addEdgeForm));
			assert(ctrl.createSpanAppend.calledWith({ innerHTML: 'Vertex 2' }, ctrl.addEdgeForm));
			assert(ctrl.createInputAppend.calledWith({ id: 'vertex2', 'type': 'text' }, ctrl.addEdgeForm));
		});
		it('creates a button with caption "Add Edge" and appends it to the <div> element', () => {
			ctrl.renderAddEdgeForm();

			assert(ctrl.createButtonAppend.calledWith('Add Edge', ctrl.addEdgeForm));
		});
		it('appends addEdgeForm to document.body if not found in document.body', () => {
			ctrl.renderAddEdgeForm();

			assert(mockBody.querySelector.calledWith('.addEdgeForm'));
			assert(mockBody.appendChild.calledWith(ctrl.addEdgeForm));
		});
	});

	describe('renderAddVertexForm', () => {
		it('creates a addVertexForm property if it does not exist and assigns it a new <div> element with class "addVertexForm"', () => {
			chai.assert.notProperty(ctrl, 'addVertexForm');

			ctrl.renderAddVertexForm();

			chai.assert.property(ctrl, 'addVertexForm');
			assert(ctrl.document.createElement.calledWith('div'));
			chai.assert.equal(ctrl.addVertexForm.className, 'addVertexForm');
		});
		it('creates a new label "Add Vertex" and appends it to the <div> element', () => {
			ctrl.renderAddVertexForm();

			assert(ctrl.createLabelAppend.calledWith('Add Vertex', ctrl.addVertexForm));
		});
		it('creates a new input of type text and id="vertexId" and appends it to the <div> element', () => {
			ctrl.renderAddVertexForm();

			assert(ctrl.createInputAppend.calledWith({ id: 'vertexId', 'type': 'text' }, ctrl.addVertexForm));
		});
		it('creates a new input of type text and id="vertexLabel" and appends it to the <div> element', () => {
			ctrl.renderAddVertexForm();

			assert(ctrl.createInputAppend.calledWith({ id: 'vertexLabel', 'type': 'text' }, ctrl.addVertexForm));
		});
		it('creates a new button with innerHTML "Add" and appends it to the <div> element', () => {
			ctrl.renderAddVertexForm();

			assert(ctrl.createButtonAppend.calledWith('Add', ctrl.addVertexForm));
		});
		it('appends addVertexForm to document.body if not found in document.body', () => {
			ctrl.renderAddVertexForm();

			assert(mockBody.querySelector.calledWith('.addVertexForm'));
			assert(mockBody.appendChild.calledWith(ctrl.addVertexForm));
		});
		it('adds event listener on the button listening to click events', () => {
			ctrl.renderAddVertexForm();

			assert(mockNewElement.addEventListener.calledWith('click', sinon.match.func));
		});
	});

	describe('appendElementIfNotPresent', () => {
		it('returns boolean: true if appended, false if it did not append', () => {
			sinon.spy(ctrl, 'appendElementIfNotPresent');
			const ret1 = ctrl.appendElementIfNotPresent(mockNewElement, mockBody);
			mockBody.querySelector.returns(mockNewElement);
			const ret2 = ctrl.appendElementIfNotPresent(mockNewElement, mockBody);

			chai.assert.equal(ret1, true);
			chai.assert.equal(ret2, false);
		});
	});

	describe('CSS Styles', () => {
		describe('Add Vertex Form', () => {
			it('sets position absolute on the form', () => {
				ctrl.renderAddVertexForm();

				assert(ctrl.cssStyles.setStyle.calledWith(ctrl.addVertexForm, 'position', 'absolute'));
			});
		});
	});

});