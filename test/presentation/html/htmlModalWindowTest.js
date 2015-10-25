// import s.u.t
import HTMLModalWindow from '../../../src/presentation/html/HTMLModalWindow';

import { mockWindow, mockDocument, mockHTMLElement } from '../../mocks/htmlMocks';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('HTMLModalWindow', () => {

	let htmlModal;

	beforeEach(() => {
		htmlModal = new HTMLModalWindow(mockWindow);
	});


	describe('rendering', () => {
		it('creates a containerElement if this.containerElement is null', () => {
			mockDocument.createElement.returns(mockHTMLElement);
			htmlModal.innerHTML = 'foo';
			htmlModal.render();

			chai.assert.deepEqual(htmlModal.containerElement, mockHTMLElement);
			assert(mockDocument.createElement.calledWith('div'));
			chai.assert.equal(htmlModal.containerElement.innerHTML, htmlModal.innerHTML);
			assert(mockDocument.body.appendChild.calledWith(mockHTMLElement));
		});
	});
});
