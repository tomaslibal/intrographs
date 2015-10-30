import Scene from "../Scene";
import HTMLGraph from "./HTMLGraph";
import HTMLMinidisplay from "./HTMLMinidisplay";
import HTMLBackground from "./HTMLBackground";
import HTMLControls from "./HTMLControls";
import HTMLWindow from "./HTMLWindow";
import MenuBar from "../model/MenuBar";
import HTMLMenuBar from "./HTMLMenuBar";
import Observable from "../../common/Observable";

export default class HTMLScene extends Scene {

	constructor(graph=null, windowObj=null) {
		if (graph === null
			|| windowObj === null) {
			throw new Error('supply the constructor with instances of the Graph class and the Window object');
			return;
		}
		let document = windowObj.document;

		let bg = new HTMLBackground(document);
		let minidisplay = new HTMLMinidisplay();
		let htmlGraph = new HTMLGraph(graph, document);
		let controls = new HTMLControls(windowObj);

		let menuBarContainer = document.createElement('div');
		document.body.appendChild(menuBarContainer);
		let menuBarModel = new MenuBar();
		let menuBar = new HTMLMenuBar(menuBarModel, menuBarContainer);

		htmlGraph.controls = controls;

		super(htmlGraph, bg, minidisplay, controls, menuBar);

		this.observables = [];
		
		this.window = windowObj;
		this.document = document;
		this.htmlWindow = new HTMLWindow(this.window);

		this.translatedX = 0;
		this.translatedY = 0;

		this.setupAll();

		this.mouseDown = false;
		this.vertexDrag = false;
	}

	setupAll() {
		this.graph.setUp();
		this.setupCanvas();
	}

	canvasMouseDownHandler(event) {
		this.mouseDown = true;

		let x = event.clientX - this.translatedX;
		let y = event.clientY - this.translatedY;
		const rect = this.canvas.getBoundingClientRect(); // adjust for scroll left/top
        x += - rect.left;
        y += - rect.top;
		const vertexAtClick = this.graph.getVertexByCoords({'x': x, 'y': y});
		if (vertexAtClick.length > 0) {
			this.vertexDrag = vertexAtClick;
		} else {
			this.vertexDrag = false;
		}
	}

	canvasMouseUpHandler() {
		this.mouseDown = false;
		this.vertexDrag = false;
	}

	canvasMouseMoveHandler(ev) {
		if (this.mouseDown && this.vertexDrag === false) {
			const dx = -(this.lastScreenX - ev.screenX);
			const dy = -(this.lastScreenY - ev.screenY);

			this.graph.graphRenderer.tX = dx;
			this.graph.graphRenderer.tY = dy;

			this.translatedX += dx;
			this.translatedY += dy;

			this.graph.render();
		}
		if (this.mouseDown && this.vertexDrag !== false) {
			const dx = -(this.lastScreenX - ev.screenX);
			const dy = -(this.lastScreenY - ev.screenY);

			this.vertexDrag.forEach(v => {
				v.x += dx;
				v.y += dy;
			});

			this.graph.renderVertexChanges({'translatedX': this.translatedX, 'translatedY': this.translatedY});
		}

		this.lastScreenX = ev.screenX;
		this.lastScreenY = ev.screenY;
	}
	
	setupObservable(observedElement, subscribedEvent, callback) {
		const observable = new Observable(observedElement);
		const boundCallback = callback.bind(this);
		
		observable.subscribe(subscribedEvent);
		observable.forEach(boundCallback);
		
		this.observables.push({
			'observedElement': observedElement,
			'subscribedEvent': subscribedEvent,
			'callback': boundCallback,
			'observable': observable
		});
	}

	setupCanvas() {
		let canvas = this.document.querySelector('#canvas');
		const [width, height] = this.htmlWindow.dims();

		this.canvas = canvas;

		canvas.width = width;
		canvas.height = height;

		this.setupObservable(canvas, 'mousedown', this.canvasMouseDownHandler);
		this.setupObservable(canvas, 'mouseup', this.canvasMouseUpHandler);
		this.setupObservable(canvas, 'mousemove', this.canvasMouseMoveHandler);
	}

}