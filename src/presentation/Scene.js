/*
 * Scene renders the Graph+Bg+Info.
 *
 * This should not know about HTML.
 *
 */
import EventBus from '../common/EventBus';

import GraphConsoleInterpreter from '../interpreter/GraphConsoleInterpreter';

export default class Scene {

	constructor(graph=null, background=null, minidisplay=null, controls=null, menubar=null) {
		this.background = background;
		this.graph = graph;
		this.minidisplay = minidisplay;
		this.controls = controls;
		this.menubar = menubar;
        this.eventBus = new EventBus('Scene_Event_Bus');
		this.interpreter = new GraphConsoleInterpreter(this.eventBus);

		this.clock = 0;
	    this.playState = 'paused';
	}

	renderAll() {
		if (this.background)  this.background.render();
		if (this.graph)       this.graph.render();
		if (this.minidisplay) this.minidisplay.render();
		if (this.controls)    this.controls.render();
		if (this.menubar)     this.menubar.render();
	}
}
