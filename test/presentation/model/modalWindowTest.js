// import s.u.t.
import ModalWindow from "../../../src/presentation/model/ModalWindow";

import { mockWindow, mockDocument, mockHTMLElement } from '../../mocks/htmlMocks';

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('ModalWindow', () => {

	let mwin;

	beforeEach(() => {
		mwin = new ModalWindow(mockWindow);
	});

	describe('basic properties', () => {
		it('has (x, y) coords', () => {
			chai.assert.property(mwin, 'x');
			chai.assert.property(mwin, 'y');
		});

		it('has width and height', () => {
			chai.assert.property(mwin, 'width');
			chai.assert.property(mwin, 'height');
		});

		it('is init\'d hidden' , () => {
			chai.assert.equal(mwin.display, false);
		});
	});

	describe('toggle visibility', () => {
		it('sets display to true and dispatches an event when show() called', () => {
			sinon.spy(mwin, 'notify');

			mwin.display = false;
			mwin.show();

			chai.assert.equal(mwin.display, true);
			assert(mwin.notify.calledWith('visibilityChange', {'display': true}));
		});

		it('sets display to false and dispatches an event when hide() called', () => {
			sinon.spy(mwin, 'notify');

			mwin.display = true;
			mwin.hide();

			chai.assert.equal(mwin.display, false);
			assert(mwin.notify.calledWith('visibilityChange', {'display': false}));
		});

		it('does not dispatch visibilityChange event when display is false and hide() is called', () => {
			sinon.spy(mwin, 'notify');
			mwin.display = false;
			mwin.hide();

			assert(mwin.notify.notCalled);
		});

		it('does not dispatch visibilityChange event when display is true and show() is called', () => {
			sinon.spy(mwin, 'notify');
			mwin.display = true;
			mwin.show();

			assert(mwin.notify.notCalled);
		});

		it('changes style.display of the containerElement if it is not null', () => {
			mwin.render();

			chai.assert.equal(mwin.containerElement.style.display, 'none');

			mwin.show();

			chai.assert.equal(mwin.containerElement.style.display, 'block');

			mwin.hide();

			chai.assert.equal(mwin.containerElement.style.display, 'none');
		});
	});

	describe('rendering', () => {
		it('creates a containerElement if this.containerElement is null', () => {
			mockDocument.createElement.returns(mockHTMLElement);
			mwin.innerHTML = 'foo';
			mwin.render();

			chai.assert.deepEqual(mwin.containerElement, mockHTMLElement);
			assert(mockDocument.createElement.calledWith('div'));
			chai.assert.equal(mwin.containerElement.innerHTML, mwin.innerHTML);
			assert(mockDocument.body.appendChild.calledWith(mockHTMLElement));
		});
	});

});