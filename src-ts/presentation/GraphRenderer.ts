import { Graph } from '../graphs/Graph';

abstract class AbstractGraphRenderer {

	canvas: HTMLCanvasElement;

	constructor(c: HTMLCanvasElement) {
		this.canvas = c;
	}

	abstract render(g: Graph);
}

export class GraphRenderer2D extends AbstractGraphRenderer {

	constructor(c: HTMLCanvasElement) {
		super(c);
	}

	render(g: Graph) {

	}
}