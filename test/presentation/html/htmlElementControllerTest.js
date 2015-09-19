// import s.u.t.
import HTMLElementController from '../../../src/presentation/html/HTMLElementController';

import { mockHTMLElement as mockNewElement, mockDocumentBody as mockBody, mockDocument } from '../../mocks/htmlMocks';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('HTMLElementController', () => {

	let elementCtrl;

	beforeEach(() => {
		elementCtrl = new HTMLElementController(mockDocument);

		sinon.spy(elementCtrl, 'createElementAppend');
		sinon.spy(elementCtrl, 'createLabelAppend');
		sinon.spy(elementCtrl, 'createInputAppend');
		sinon.spy(elementCtrl, 'createButtonAppend');
		sinon.spy(elementCtrl, 'createSpanAppend');
	});

	it('stores the reference to the document', () => {
		chai.assert.deepEqual(elementCtrl.document, mockDocument);
	});

	describe('createElementAppend', () => {
		it('creates a new element of specified type and appends it to the parent element', () => {
			let mockParent = {
				appendChild: sinon.stub()
			};

			elementCtrl.createElementAppend('input', mockParent);

			assert(mockDocument.createElement.calledWith('input'));
			assert(mockParent.appendChild.calledWith(mockNewElement));
		});

		it('returns the newly created element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let element = elementCtrl.createElementAppend('input', mockParent);
			chai.assert.deepEqual(element, mockNewElement);
		});
	});

	describe('appendElementIfNotPresent', () => {
		it('returns boolean: true if appended, false if it did not append', () => {
			sinon.spy(elementCtrl, 'appendElementIfNotPresent');
			const ret1 = elementCtrl.appendElementIfNotPresent(mockNewElement, mockBody);
			mockBody.querySelector.returns(mockNewElement);
			const ret2 = elementCtrl.appendElementIfNotPresent(mockNewElement, mockBody);

			chai.assert.equal(ret1, true);
			chai.assert.equal(ret2, false);
		});
	});

	describe('createLabelAppend', () => {
		it('creates a <p> element, assigns innerHTML as the label and appends it to the parent', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			sinon.spy(mockParent, 'appendChild');

			elementCtrl.createLabelAppend('LabelCaption', mockParent);

			assert(elementCtrl.createElementAppend.calledWith('p', mockParent));
			chai.assert.equal(mockNewElement.innerHTML, 'LabelCaption');
		});
		it('returns the newly created <p> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let labelElement = elementCtrl.createLabelAppend('LabelCaption', mockParent);

			chai.assert.deepEqual(labelElement, mockNewElement);
		});
	});

	describe('createButtonAppend', () => {
		it('creates a <button> element, assigns innerHTML as the label and appends it to the parent', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			sinon.spy(mockParent, 'appendChild');

			elementCtrl.createButtonAppend('ButtonCaption', mockParent);

			assert(elementCtrl.createElementAppend.calledWith('button', mockParent));
			chai.assert.equal(mockNewElement.innerHTML, 'ButtonCaption');
		});
		it('returns the newly created <button> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let buttonElement = elementCtrl.createButtonAppend('ButtonCaption', mockParent);

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

			elementCtrl.createInputAppend({ id: 'foo', 'type': 'text' }, mockParent);

			assert(elementCtrl.createElementAppend.calledWith('input', mockParent));
			chai.assert.equal(mockNewElement.id, 'foo');
			chai.assert.equal(mockNewElement.type, 'text');
		});
		it('returns the newly created <input> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let inputElement = elementCtrl.createInputAppend({ id: 'foo', 'type': 'text' }, mockParent);

			chai.assert.deepEqual(inputElement, mockNewElement);
		});
	});

	describe('createSpanAppend', () => {
		it('creates a <span> element with specified innerHTML and appends it to the parent', () => {
			let mockParent = {
				appendChild(child) { return child }
			};

			sinon.spy(mockParent, 'appendChild');

			elementCtrl.createSpanAppend({ innerHTML: 'bar' }, mockParent);

			assert(elementCtrl.createElementAppend.calledWith('span', mockParent));
			chai.assert.equal(mockNewElement.innerHTML, 'bar');
		});
		it('returns the newly created <span> element', () => {
			let mockParent = {
				appendChild(child) { return child; }
			};

			let spanElement = elementCtrl.createSpanAppend({ innerHTML: 'foo' }, mockParent);

			chai.assert.deepEqual(spanElement, mockNewElement);
		});
	});
});