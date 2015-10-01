// import s.u.t.
import HTMLScene from "../../../src/presentation/html/HTMLScene";

import HTMLBackground from "../../../src/presentation/html/HTMLBackground";
import HTMLMinidisplay from "../../../src/presentation/html/HTMLMinidisplay";
import HTMLGraph from "../../../src/presentation/html/HTMLGraph";
import HTMLControls from "../../../src/presentation/html/HTMLControls";
import Observable from "../../../src/common/Observable";

import { mockHTMLElement as mockElement, mockCanvas, mockDocument, mockWindow } from "../../mocks/htmlMocks";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLScene', () => {

	let htmlScene = null;

	let mockGraph = {
		vertices: [{name:'x'}, {name:'y'}],
		edges: [{connects:['x', 'y']}]
	};

	let mockMouseEvent = {
		clientX: 1,
		clientY: 1
	};

	mockDocument.querySelector.returns(mockCanvas);

	beforeEach(() => {
		htmlScene = new HTMLScene(mockGraph, mockWindow);

		sinon.spy(htmlScene.htmlWindow, 'dims');
	});

	describe('constructor', () => {
		it('takes two arguments: an instance of a Graph class and an instance of the Window object', () => {
			let throws = () => {
				const htmlSceneThrows = new HTMLScene();
			};
			let throwsToo = () => {
				const htmlSceneThrows = new HTMLScene(graph);
			};
			let wontThrow = () => {
				const htmlSceneGood = new HTMLScene(mockGraph, mockWindow);
			}

			chai.expect(throws).to.throw(Error);
			chai.expect(throwsToo).to.throw(Error);
			chai.expect(wontThrow).not.to.throw();
		});
		it('assigns the window object to .window property', () => {
			chai.assert.property(htmlScene, 'window');
			chai.assert.deepEqual(htmlScene.window, mockWindow);
		});
		it('assigns the document object to .document property', () => {
			chai.assert.property(htmlScene, 'document');
			chai.assert.deepEqual(htmlScene.document, mockDocument);
		});
	});

	it('uses instances of HTMLBackground, HTMLMinidsplay, HTMLGraph, and HTMLControls', () => {
		assert(htmlScene.background instanceof HTMLBackground);
		assert(htmlScene.minidisplay instanceof HTMLMinidisplay);
		assert(htmlScene.graph instanceof HTMLGraph);
		assert(htmlScene.controls instanceof HTMLControls);
	});

	describe('setupCanvas()', () => {
		it('uses HTMLWindow to set full viewport dimensions on the #canvas element', () => {
			/*
			 * The spies are setup after the object construction but setupCanvas()
			 * is called during construction so the call assertion would not work.
			 * For this reason the method is invoked again here.
			 */
			htmlScene.setupCanvas();

			assert(mockDocument.querySelector.calledWith('#canvas'));
			assert(htmlScene.htmlWindow.dims.calledOnce);

			chai.assert.equal(mockCanvas.width, mockWindow.innerWidth);
			chai.assert.equal(mockCanvas.height, mockWindow.innerHeight);
		});

		it('stores the reference to the canvas', () => {
			htmlScene.setupCanvas();

			chai.assert.property(htmlScene, 'canvas');
		});

		it('creates observables for mousedown, mouseup and mousemove events on the canvas', () => {
			htmlScene.setupCanvas();

			assert(htmlScene.canvasObservableMouseDown instanceof Observable);
			assert(htmlScene.canvasObservableMouseUp instanceof Observable);
			assert(htmlScene.canvasObservableMouseMove instanceof Observable);
		});
	});

	describe('Translating the origin', () => {
		it('is activated on mouse down', () => {
			chai.assert.equal(htmlScene.mouseDown, false);

			htmlScene.canvasMouseDownHandler(mockMouseEvent);

			chai.assert.equal(htmlScene.mouseDown, true);
		});

		it('is deactivated on mouse up', () => {
			htmlScene.canvasMouseDownHandler(mockMouseEvent);

			chai.assert.equal(htmlScene.mouseDown, true);

			htmlScene.canvasMouseUpHandler();

			chai.assert.equal(htmlScene.mouseDown, false);
		});
	});

	describe('Selecting the vertices', () => {
		it('calls getVertexByCoords on mousedown with the translated location of a click', () => {
			sinon.spy(htmlScene.graph, 'getVertexByCoords');

			const originalRect = mockCanvas.getBoundingClientRect();
			mockCanvas.getBoundingClientRect.returns({
				left: 50,
				top: -50
			});

			htmlScene.canvasMouseDownHandler(mockMouseEvent);

			chai.assert.isTrue(htmlScene.graph.getVertexByCoords.calledWith({'x': 1-50, 'y': 1+50}));

			mockCanvas.getBoundingClientRect.returns(originalRect);
		});

		it('sets HTMLScene.vertexDrag to the Array<Vertex> of selected vertices at mouse coords', () => {
			sinon.stub(htmlScene.graph, 'getVertexByCoords');
			htmlScene.graph.getVertexByCoords.returns(['foo', 'bar']);
			htmlScene.canvasMouseDownHandler(mockMouseEvent);

			chai.assert.deepEqual(htmlScene.vertexDrag, ['foo', 'bar']);
		});

		it('sets HTMLScene.vertexDrag to false on mouseUp', () => {
			htmlScene.vertexDrag = 'minky';

			htmlScene.canvasMouseUpHandler();

			chai.assert.equal(htmlScene.vertexDrag, false);
		});

        it('updates x,y of the vertices in HTMLScene.vertexDrag on mouse move if HTMLScene.vertexDrag contains one or more vertices', () => {
            htmlScene.mouseDown = true;
            htmlScene.lastScreenX = 90;
            htmlScene.lastScreenY = 190;
            htmlScene.vertexDrag = [{'x': 1, 'y': 1}];
            htmlScene.graph.renderVertexChanges = sinon.stub();
            htmlScene.canvasMouseMoveHandler({
                screenX: 100,
                screenY: 200
            });

            chai.assert.deepEqual(htmlScene.vertexDrag, [{'x': 1+(100-90), 'y': 1+(200-190)}]);
        });
	});

});
