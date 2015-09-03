/*
 * Scene renders the Graph+Bg+Info.
 *
 * This should not know about HTML.
 *
 */
export default class Scene {

	constructor(graph=null, background=null, minidisplay=null) {
		this.background = background;
		this.graph = graph;
		this.minidisplay = minidisplay;

		this.clock = 0;
	        this.playState = 'paused';
	}

	renderAll() {
		if (this.background)  this.background.render();
		if (this.graph)       this.graph.render();
		if (this.minidisplay) this.minidisplay.render();
	}
}