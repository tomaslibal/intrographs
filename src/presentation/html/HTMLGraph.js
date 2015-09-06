import GraphRenderer2D from "../GraphRenderer2D";
import { ObservableRenderable } from "../../presentation/ObservableRenderable";
import Observable from "../../common/Observable";

export default class HTMLGraph extends ObservableRenderable {

	constructor(graph) {
		const x = 0;
		const y = 0;

		super({ 'posX': x, 'posY': y });

		this.graph = graph;
		this.x = x;
		this.y = y;

		this.setUp();
	}

	setUp() {
		if (this.graph.controls) {
			this.ctrlObservable = new Observable(this.graph.controls);
			this.ctrlObservable.subscribe('controls.add.vertex');
			this.ctrlObservable.forEach(this._handleNewVertexEvent);
		}
	}

	_handleNewVertexEvent(event) {
		
	}

	render() {
		let graphRenderer = new GraphRenderer2D();
		let canvas = document.querySelector("#canvas");
		let ctx = graphRenderer.getContext(canvas);

		graphRenderer.setCanvas(canvas);
		graphRenderer.render(this.graph);
	}

}