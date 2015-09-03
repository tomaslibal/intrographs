import Scene from "./Scene";
import HTMLGraph from "./HTMLGraph";
import HTMLMinidisplay from "./HTMLMinidisplay";
import HTMLBackground from "./HTMLBackground";

export default class HTMLScene extends Scene {

	constructor(graph) {
		let bg = new HTMLBackground();
		let minidisplay = new HTMLMinidisplay();
		let htmlGraph = new HTMLGraph(graph);

		super(htmlGraph, bg, minidisplay);
	}

}