import IRenderable from "../IRenderable";
import GraphRenderer2D from "../GraphRenderer2D";

export default class HTMLGraph extends IRenderable {

	constructor(graph) {
		const x = 0;
		const y = 0;

		super({ 'posX': x, 'posY': y });

		this.graph = graph;
		this.x = x;
		this.y = y;
	}

	render() {
		let graphRenderer = new GraphRenderer2D();
		let canvas = document.querySelector("#canvas");
		let ctx = graphRenderer.getContext(canvas);

		graphRenderer.setCanvas(canvas);
		graphRenderer.render(this.graph);
	}

}