// import s.u.t.
import HTMLGraph from "../../../src/presentation/html/HTMLGraph";

import HTMLList from '../../../src/presentation/html/HTMLList';
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
		notify(signalEventType, event=mockEvent) {
			this.callbacks.forEach(({ eventType: eventType, callback: callback }) => {
				if (signalEventType === eventType) {
					callback.call(undefined, event);
				}
			});
		}
	};

	let mockGraph = {
		addVertex: sinon.stub(),
		addEdge: sinon.stub(),
		vertices: [{name:'x'}, {name:'y'}],
		edges: [{connects:['x', 'y']}]
	};

	let mockElement = {
		innerHTML: '',
		appendChild: sinon.stub(),
		style: {}
	};

	let mockDocument = {
		createElement: sinon.stub().returns(mockElement),
		querySelector: sinon.stub(),
		appendChild: sinon.stub(),
		body: mockElement
	};

	beforeEach('setup test fixtures', () => {
		sinon.spy(mockControls, 'addEventListener');

		htmlGraph = new HTMLGraph(mockGraph, mockDocument);
		htmlGraph.controls = mockControls;
		htmlGraph.setUp();
		htmlGraph.render = sinon.stub();
	});

	afterEach('restore spies', () => {
		mockControls.addEventListener.restore();
		mockControls.callbacks = [];
	});

	describe('constructor', () => {
		it('instantiates two instances of HTMLList (for vertex and edge lists)', () => {
			chai.assert.property(htmlGraph, 'vertexList');
			chai.assert.property(htmlGraph, 'edgeList');

			assert(htmlGraph.vertexList instanceof HTMLList);
			assert(htmlGraph.edgeList instanceof HTMLList);
		});
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

			let htmlGraphWithoutControls = new HTMLGraph(mockGraphWithoutControls, mockDocument);

			chai.assert.notProperty(htmlGraphWithoutControls, 'ctrlObservable');
		});
		it('pushes the existing vertices to the vertexList.list array', () => {
			chai.assert.deepEqual(htmlGraph.vertexList.list, ['x', 'y']);
			chai.assert.deepEqual(htmlGraph.edgeList.list, [['x', 'y']]);
		});
	});

	describe('on controls.add.vertex events', () => {
		it('adds the new vertex and repaints the canvas', () => {
			mockControls.notify('controls.add.vertex');

			assert(htmlGraph.graph.addVertex.calledWith({ 'name': 'v900', 'label': 'V900' }));
			assert(htmlGraph.render.calledOnce);
		});
		it('adds the new vertex to the vertexList and repaints the list', () => {
			sinon.spy(htmlGraph.vertexList, 'render');
			sinon.spy(htmlGraph.vertexList.list, 'push');

			mockControls.notify('controls.add.vertex', { 'id': 'ver1' });

			assert(htmlGraph.vertexList.list.push.calledWith('ver1'));
			assert(htmlGraph.vertexList.render.calledOnce);
		});
	});

	describe('on controls.add.edge events', () => {
		it('adds the new edge and repaints the canvas', () => {
			mockControls.notify('controls.add.edge', { 'vertex1': 'm', 'vertex2': 'n' });

			assert(htmlGraph.graph.addEdge.calledWith(['m', 'n']));
			assert(htmlGraph.render.calledOnce);
		});
		it('adds the new edge to the edgeList and repaints the list', () => {
			sinon.spy(htmlGraph.edgeList, 'render');
			sinon.spy(htmlGraph.edgeList.list, 'push');

			mockControls.notify('controls.add.edge', { 'vertex1': 'ver1', 'vertex2': 'ver2' });

			assert(htmlGraph.edgeList.list.push.calledWith(['ver1', 'ver2']));
			assert(htmlGraph.edgeList.render.calledOnce);
		});
	});

	describe('render', () => {
		// skipped because the GraphRenderer2D in the render() method needs to be mocked
		it.skip('invokes this.vertexList.render', () => {
			sinon.spy(htmlGraph.vertexList, 'render');
			htmlGraph.render();
			assert(htmlGraph.vertexList.render.calledOnce);
		});
		// skipped because the GraphRenderer2D in the render() method needs to be mocked
		it.skip('invokes this.edgeList.render', () => {
			sinon.spy(htmlGraph.edgeList, 'render');
			htmlGraph.render();
			assert(htmlGraph.edgeList.render.calledOnce);
		});
	});
});