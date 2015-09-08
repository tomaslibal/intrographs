import Scene from "../Scene";
import HTMLGraph from "./HTMLGraph";
import HTMLMinidisplay from "./HTMLMinidisplay";
import HTMLBackground from "./HTMLBackground";
import HTMLControls from "./HTMLControls";
import HTMLWindow from "./HTMLWindow";

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
		let htmlGraph = new HTMLGraph(graph);
		let controls = new HTMLControls(windowObj);

		htmlGraph.controls = controls;

		super(htmlGraph, bg, minidisplay, controls);

		this.window = windowObj;
		this.document = document;
		this.htmlWindow = new HTMLWindow(this.window);

		this.setupAll();
	}

	setupAll() {
		this.graph.setUp();
		this.setupCanvas();
	}

	setupCanvas() {
		let canvas = this.document.querySelector('#canvas');
		const [width, height] = this.htmlWindow.dims();

		canvas.width = width;
		canvas.height = height;
	}

}