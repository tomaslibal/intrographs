import Scene from "../Scene";
import HTMLGraph from "./HTMLGraph";
import HTMLMinidisplay from "./HTMLMinidisplay";
import HTMLBackground from "./HTMLBackground";
import HTMLControls from "./HTMLControls";
import HTMLWindow from "./HTMLWindow";
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

		htmlGraph.controls = controls;

		super(htmlGraph, bg, minidisplay, controls);

		this.window = windowObj;
		this.document = document;
		this.htmlWindow = new HTMLWindow(this.window);

		this.translatedX = 0;
		this.translatedY = 0;

		this.setupAll();

		this.mouseDown = false;
	}

	setupAll() {
		this.graph.setUp();
		this.setupCanvas();
	}

	canvasMouseDownHandler(event) {
		this.mouseDown = true;

		let x = event.clientX - this.translatedX;
		let y = event.clientY - this.translatedY;
		const rect = canvas.getBoundingClientRect() // adjust for scroll left/top
        x += - rect.left;
        y += - rect.top;
		const vertexAtClick = this.graph.getVertexByCoords({'x': x, 'y': y});
		console.log(vertexAtClick);
	}

	canvasMouseUpHandler() {
		this.mouseDown = false;
	}

	canvasMouseMoveHandler(ev) {
		if (this.mouseDown) {
			const dx = -(this.lastScreenX - ev.screenX);
			const dy = -(this.lastScreenY - ev.screenY);

			this.graph.graphRenderer.tX = dx;
			this.graph.graphRenderer.tY = dy;

			this.translatedX += dx;
			this.translatedY += dy;

			this.graph.render();
		}

		this.lastScreenX = ev.screenX;
		this.lastScreenY = ev.screenY;
	}

	setupCanvas() {
		let canvas = this.document.querySelector('#canvas');
		const [width, height] = this.htmlWindow.dims();

		this.canvas = canvas;

		canvas.width = width;
		canvas.height = height;

		this.canvasObservableMouseDown = new Observable(canvas);
		this._boundCanvasMouseDownHandler = this.canvasMouseDownHandler.bind(this);
		this.canvasObservableMouseDown.subscribe('mousedown');
		this.canvasObservableMouseDown.forEach(this._boundCanvasMouseDownHandler);

		this.canvasObservableMouseUp = new Observable(canvas);
		this.canvasObservableMouseUp.subscribe('mouseup');
		this._boundCanvasMouseUpHandler = this.canvasMouseUpHandler.bind(this);
		this.canvasObservableMouseUp.forEach(this._boundCanvasMouseUpHandler);

		this.canvasObservableMouseMove = new Observable(canvas);
		this.canvasObservableMouseMove.subscribe('mousemove');
		this._boundCanvasMouseMoveHandler = this.canvasMouseMoveHandler.bind(this);
		this.canvasObservableMouseMove.forEach(this._boundCanvasMouseMoveHandler);
	}

}