import { Graph } from '../graphs/Graph';
import { Vertex, VertexPosition2D } from '../graphs/Vertex';
import { MathUtil } from '../common/MathUtil';

abstract class AbstractGraphRenderer {

	canvas: HTMLCanvasElement;

	constructor(c: HTMLCanvasElement) {
		this.canvas = c;
	}

	abstract render(g: Graph);
}

export class GraphLayoutBasic {

	static getRandomVertexPosition(min: number = 0, max: number = 800): VertexPosition2D {
		return {
			x: MathUtil.getRandomArbitrary(min, max),
			y: MathUtil.getRandomArbitrary(min, max)
		};
	}

	static spaceOutVerticesRandom(vSet: Array<Vertex>, min:number = 0, max: number = 800): Array<Vertex> {
		for(let i = 0; i < vSet.length; i++) {
			vSet[i].setPosition(GraphLayoutBasic.getRandomVertexPosition(min, max));
		}

		return vSet;
	}

	static spaceOutVerticesAtFixedDistanceRandom(vSet: Array<Vertex>, distance: number = 15): Array<Vertex> {
		let lastVertexX: number;
		let lastVertexY: number;

		let x;
		let y;

		vSet.forEach((vertex, idx, all) => {
			if (idx === 0 && !lastVertexX) {
				const { x: x2, y: y2 } = GraphLayoutBasic.getRandomVertexPosition();
				x = x2;
				y = y2;
			} else if (idx === 0 && lastVertexX) {
				[x, y] = GraphLayoutBasic.findRandomCoordsAtGivenDistance(lastVertexX, lastVertexY, distance);
			} else {
				const prev: Vertex = all[idx - 1];
				[x, y] = GraphLayoutBasic.findRandomCoordsAtGivenDistance(prev.getPosition().x, prev.getPosition().y, distance);
			}

			vertex.setPosition({
					x: x,
					y: y
				});

			lastVertexX = x;
			lastVertexY = y;
 		});

		return vSet;
	}

	static findRandomCoordsAtGivenDistance(originX: number, originY: number, distance: number = 15): Array<number> {

	}
}


export class GraphRenderer2D extends AbstractGraphRenderer {

	constructor(c: HTMLCanvasElement) {
		super(c);
	}

	render(g: Graph) {

	}
}