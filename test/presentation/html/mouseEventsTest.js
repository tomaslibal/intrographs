// import s.u.t.
import MouseEvents from '../../../src/presentation/html/MouseEvents';

import { mockHTMLElement } from '../../mocks/htmlMocks';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('MouseEvents', () => {

	let mouseEvents;

	let mockCallback;

	beforeEach(() => {
		mouseEvents = new MouseEvents();

		mockCallback = sinon.stub();
	});

	describe('static methods', () => {
		it('adds a listener with a callback for mouse click', () => {
			MouseEvents.onClick(mockHTMLElement, mockCallback);

			assert(mockHTMLElement.addEventListener.calledWith('click', mockCallback));
		});

		it('adds a listener with a callback for mouse down event', () => {
			MouseEvents.onMouseDown(mockHTMLElement, mockCallback);

			assert(mockHTMLElement.addEventListener.calledWith('mousedown', mockCallback));
		});

		it('adds a listener with a callback for mouse down up', () => {
			MouseEvents.onMouseUp(mockHTMLElement, mockCallback);

			assert(mockHTMLElement.addEventListener.calledWith('mouseup', mockCallback));
		});
		
		it('adds a listener with a callback for mouse move event', () => {
			MouseEvents.onMouseMove(mockHTMLElement, mockCallback);

			assert(mockHTMLElement.addEventListener.calledWith('mousemove', mockCallback));
		});
	});

});