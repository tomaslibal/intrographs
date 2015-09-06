// import s.u.t.
import HTMLGraph from "../../../src/presentation/html/HTMLGraph";

import Observable from "../../../src/common/Observable";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLGraph', () => {

	let htmlGraph = null;

	let mockEvent = {
		'id': 'v900',
		'label': 'V900'
	};

	let mockControls = {
		addEventListener(eventType, callback) {
			this.callbacks = this.callbacks || [];
			this.callbacks.push({ eventType: eventType, callback: callback });
		},
		removeEventListener: sinon.stub(),
		notify(signalEventType) {
			this.callbacks.forEach(({ eventType: eventType, callback: callback }) => {
				if (signalEventType === eventType) {
					callback.call(undefined, mockEvent);
				}
			});
		}
	};

	let mockGraph = {
		addVertex: sinon.stub()
	};

	beforeEach('setup test fixtures', () => {
		sinon.spy(mockControls, 'addEventListener');

		htmlGraph = new HTMLGraph(mockGraph);
		htmlGraph.controls = mockControls;
		htmlGraph.setUp();
		htmlGraph.render = sinon.stub();
	});

	afterEach('restore spies', () => {
		mockControls.addEventListener.restore();
		mockControls.callbacks = [];
	});

	describe('setUp', () => {
		it('creates an observable from the controls object', () => {
			chai.assert.property(htmlGraph, 'ctrlObservable');
			assert(htmlGraph.ctrlObservable instanceof Observable);
		});

		it('subscribes to "controls.add.vertex" event on the controls observable', () => {
			assert(mockControls.addEventListener.calledWith('controls.add.vertex', sinon.match.func));
		});

		it('adds a forEach callback _handleNewVertexEvent on the controls observable', () => {
			sinon.spy(mockControls.callbacks[0], 'callback');

			mockControls.notify('controls.add.vertex');

			assert(mockControls.callbacks[0].callback.calledOnce);
		});

		it('does not create a new observable if graph.controls is undefined', () => {
			let mockGraphWithoutControls = {};

			let htmlGraphWithoutControls = new HTMLGraph(mockGraphWithoutControls);

			chai.assert.notProperty(htmlGraphWithoutControls, 'ctrlObservable');
		});
	});

	describe('on controls.add.vertex events', () => {
		it('adds the new vertex and repaints the canvas', () => {
			mockControls.notify('controls.add.vertex');

			assert(htmlGraph.graph.addVertex.calledWith({ 'name': 'v900', 'label': 'V900' }));
		});
	})


});