export default class Scene {

	constructor(graph=null, background=null, minidisplay=null) {
		this.background = background;
		this.graph = graph;
		this.minidisplay = minidisplay;

		this.clock = 0;
	        this.playState = 'paused';
	}

}
