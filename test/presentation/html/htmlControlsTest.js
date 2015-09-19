// import s.u.t.
import HTMLControls from "../../../src/presentation/html/HTMLControls";
import { mockHTMLElement as mockNewElement, mockDocumentBody as mockBody, mockDocument, mockWindow} from "../../mocks/htmlMocks";

import HTMLElementController from '../../../src/presentation/html/HTMLElementController';

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLControls', () => {

	let ctrl = null;

	beforeEach(() => {
		ctrl = new HTMLControls(mockWindow);

		sinon.spy(ctrl, 'createElementAppend');
		sinon.spy(ctrl, 'createLabelAppend');
		sinon.spy(ctrl, 'createInputAppend');
		sinon.spy(ctrl, 'createButtonAppend');
		sinon.spy(ctrl, 'createSpanAppend');
		sinon.spy(ctrl.cssStyles, 'setStyle');
		sinon.spy(ctrl, 'notify');

		mockDocument.querySelector.withArgs('.addVertexForm').returns(null);
	});

	describe('contructor', () => {
		it('takes one argument which is the window object and assigns .document property to window.document', () => {
			chai.assert.property(ctrl, 'document');
			chai.assert.deepEqual(ctrl.document, mockDocument);
		});
		it('instantiates HTMLElementController which will be used to create element', () => {
			chai.assert.property(ctrl, 'elementCtrl');
			chai.assert.equal(ctrl.elementCtrl instanceof HTMLElementController, true);
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
		it('adds event listener on the button listening to click events', () => {
			ctrl.renderAddEdgeForm();

			assert(mockNewElement.addEventListener.calledWith('click', ctrl._boundAddEdgeButtonHandler));
		});
		it('sends notification to its observers that new edge has been added', () => {
			mockNewElement.querySelector.withArgs('#vertex1').returns({'value': 'x'});
			mockNewElement.querySelector.withArgs('#vertex2').returns({'value': 'y'});
			ctrl.renderAddEdgeForm();
			ctrl._addEdgeButtonHandler();

			assert(ctrl.addEdgeForm.querySelector.calledWith('#vertex1'));
			assert(ctrl.addEdgeForm.querySelector.calledWith('#vertex2'));
			assert(ctrl.notify.calledWith('controls.add.edge', { 'vertex1': 'x', 'vertex2': 'y'}));
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
		it('creates a new space "ID" and appends it to the <div> element', () => {
			ctrl.renderAddVertexForm();

			assert(ctrl.createSpanAppend.calledWith({ innerHTML: 'ID' }, ctrl.addVertexForm));
		});
		it('creates a new input of type text and id="vertexId" and appends it to the <div> element', () => {
			ctrl.renderAddVertexForm();

			assert(ctrl.createInputAppend.calledWith({ id: 'vertexId', 'type': 'text' }, ctrl.addVertexForm));
		});
		it('creates a new space "Label" and appends it to the <div> element', () => {
			ctrl.renderAddVertexForm();

			assert(ctrl.createSpanAppend.calledWith({ innerHTML: 'Label' }, ctrl.addVertexForm));
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

			assert(mockNewElement.addEventListener.calledWith('click', ctrl._boundAddVertexButtonHandler));
		});
		it('sends notification to its observers that new vertex has been added', () => {
			mockNewElement.querySelector.withArgs('#vertexId').returns({'value': 'a'});
			mockNewElement.querySelector.withArgs('#vertexLabel').returns({'value': 'vertexA'});
			ctrl.renderAddVertexForm();
			ctrl._addVertexButtonHandler();

			assert(ctrl.addVertexForm.querySelector.calledWith('#vertexId'));
			assert(ctrl.addVertexForm.querySelector.calledWith('#vertexLabel'));
			assert(ctrl.notify.calledWith('controls.add.vertex', { 'id': 'a', 'label': 'vertexA'}));
		});
		it('removes any text value from the inputs when the add button is clicked', () => {
			mockNewElement.value = 'foo';

			ctrl.renderAddVertexForm();
			ctrl.addVertexForm.querySelector.withArgs('#vertexId').returns(mockNewElement);
			ctrl.addVertexForm.querySelector.withArgs('#vertexLabel').returns(mockNewElement);

			ctrl._addVertexButtonHandler();

			chai.assert.equal(mockNewElement.value, '');
		});
	});

	describe('CSS Styles', () => {
		describe('Add Vertex Form', () => {
			it('sets position absolute on the form', () => {
				ctrl.renderAddVertexForm();

				assert(ctrl.cssStyles.setStyle.calledWith(ctrl.addVertexForm, 'position', 'absolute'));
			});
			it('sets styles top = 10px, right = 10px on the form', () => {
				ctrl.renderAddVertexForm();

				assert(ctrl.cssStyles.setStyle.calledWith(ctrl.addVertexForm, 'top', '10px'));
				assert(ctrl.cssStyles.setStyle.calledWith(ctrl.addVertexForm, 'right', '10px'));
			});
		});

		describe('Add Edge Form', () => {
			it('sets position absolute on the form', () => {
				ctrl.renderAddEdgeForm();

				assert(ctrl.cssStyles.setStyle.calledWith(ctrl.addEdgeForm, 'position', 'absolute'));
			});
			it('sets styles top = 100px, right = 10px on the form', () => {
				ctrl.renderAddEdgeForm();

				assert(ctrl.cssStyles.setStyle.calledWith(ctrl.addEdgeForm, 'top', '100px'));
				assert(ctrl.cssStyles.setStyle.calledWith(ctrl.addEdgeForm, 'right', '10px'));
			});
		});
	});

});