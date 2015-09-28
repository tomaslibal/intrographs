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

});