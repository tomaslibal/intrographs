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

	describe('toggle visibility', () => {
		it('changes style.display of the containerElement if it is not null', () => {
			htmlModal.render();

			chai.assert.equal(htmlModal.containerElement.style.display, 'none');

			htmlModal.show();

			chai.assert.equal(htmlModal.containerElement.style.display, 'block');

			htmlModal.hide();

			chai.assert.equal(htmlModal.containerElement.style.display, 'none');
		});
	});

	describe('rendering', () => {
		it('creates a containerElement if this.containerElement is null', () => {
			mockDocument.createElement.returns(mockHTMLElement);
			htmlModal.content = 'foo';
			htmlModal.render();

			chai.assert.deepEqual(htmlModal.containerElement, mockHTMLElement);
			assert(mockDocument.createElement.calledWith('div'));
			chai.assert.equal(htmlModal.containerElement.innerHTML, htmlModal.content);
			assert(mockDocument.body.appendChild.calledWith(mockHTMLElement));
		});

		it('adds "close window" button/link', () => {
			sinon.spy(htmlModal, 'getCloseWindowButton');
			htmlModal.render();

			assert(htmlModal.getCloseWindowButton.calledOnce);
		});

		it('updates the content if already rendered', () => {
			htmlModal.content = 'foo';
			htmlModal.render();

			chai.assert.equal(htmlModal.rendered, true);
			chai.assert.equal(htmlModal.containerElement.innerHTML, 'foo');

			htmlModal.content = 'bar';

			htmlModal.render();

			chai.assert.equal(htmlModal.containerElement.innerHTML, 'bar');
		});
	});
});
