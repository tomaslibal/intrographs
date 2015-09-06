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
	}

	setUp() {
		if (this.controls) {
			this.ctrlObservable = new Observable(this.controls);
			this.ctrlObservable.subscribe('controls.add.vertex');
			this._boundHandleNewVertexEvent = this._handleNewVertexEvent.bind(this);
			this.ctrlObservable.forEach(this._boundHandleNewVertexEvent);
		}
	}

	_handleNewVertexEvent({ 'id': id, 'label': label }) {
		this.graph.addVertex({ 'name': id, 'label': label });
		this.render();
	}

	render() {
		let graphRenderer = new GraphRenderer2D();
		let canvas = document.querySelector("#canvas");
		let ctx = graphRenderer.getContext(canvas);

		graphRenderer.setCanvas(canvas);
		graphRenderer.clearCanvas(ctx);
		graphRenderer.render(this.graph);
	}

}