import { Graph } from '../graphs/Graph';
import { Canvas, CanvasSettings } from './Canvas';

abstract class Scene {

	canvas;
	graph: Graph;
	console;

	constructor(g: Graph) {
		this.graph = g;
	}

	abstract render();

}

interface HTMLSceneConfig {
	canvasId: string;
	usePolymer: boolean;
}

export class HTMLScene extends Scene {

	canvas: Canvas;
	canvasSettings: CanvasSettings = {
		width: 800,
		height: 640
	};

	private window: Window;

	constructor(g: Graph, w: Window) {
		super(g);

		this.window = w;
		this.setupCanvas();
	}

	private setupCanvas() {
		const canvasEl = <HTMLCanvasElement> this.window.document.querySelector('#canvas');
		const [width, height] =  [this.window.innerWidth, this.window.innerHeight];

		this.canvasSettings.width = width;
		this.canvasSettings.height = height;
		this.canvas = new Canvas(canvasEl, this.canvasSettings);
	}

	render() {

	}
}