import Scene from "../Scene";
import HTMLGraph from "./HTMLGraph";
import HTMLMinidisplay from "./HTMLMinidisplay";
import HTMLBackground from "./HTMLBackground";
import HTMLControls from "./HTMLControls";

export default class HTMLScene extends Scene {

	constructor(graph, document) {
		let bg = new HTMLBackground(document);
		let minidisplay = new HTMLMinidisplay();
		let htmlGraph = new HTMLGraph(graph);
		let controls = new HTMLControls(document);

		super(htmlGraph, bg, minidisplay, controls);
	}

}