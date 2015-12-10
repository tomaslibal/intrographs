import { Graph } from '../graphs/Graph';
import { Vertex, VertexPosition2D } from '../graphs/Vertex';
import { Edge } from '../graphs/Edge';
import { MathUtil } from '../common/MathUtil';
import { Canvas, Point2D, Draw2D } from './Canvas';

abstract class AbstractGraphRenderer {

	canvas: Canvas;

	constructor(c: Canvas) {
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
		const randX = MathUtil.getRandomArbitrary(originX - distance, originX + distance);

		const c = (distance*distance - originX*originX - originY*originY + 2*originX*randX - randX*randX);
		const b = 2*originY;
		const a = -1;
		let solutions: Array<number> = MathUtil.quadRoots(a, b, c);

		// if discrimintant < 0, we would get no solution
		while( solutions === null) {
			solutions = GraphLayoutBasic.findRandomCoordsAtGivenDistance(originX, originY, distance);
		}

		// if discriminant == 0 we only get one solution
		if ( solutions.length === 1 ) {
			return [randX, solutions[0]];
		}

		// else, discriminant > 0 and we have two solutions for the point Y
        // and we will choose one at random
		const useFirstOrSecondSolution: number = MathUtil.getRandomArbitrary(0, 1);
		return [randX, solutions[useFirstOrSecondSolution]];

	}
}

export abstract class Renderable {

	abstract render(renderer: AbstractGraphRenderer);
}

export class VertexRenderable extends Renderable {
	vertex: Vertex;

	constructor(v: Vertex) {
		super();
		this.vertex = v;
	}

	render(renderer: AbstractGraphRenderer) {
		const ctx = renderer.canvas.getContext();

		Draw2D.circle(ctx, { x: this.vertex.position.x, y: this.vertex.position.y }, 3);
	}
}

export class EdgeRenderable extends Renderable {

	edge: Edge;
	start: Vertex;
	end: Vertex;

	constructor(e: Edge, s: Vertex, t: Vertex) {
		super();
		this.edge = e;
		this.start = s;
		this.end = t;
	}

	render(renderer: AbstractGraphRenderer) {
		const ctx = renderer.canvas.getContext();
		const start: Point2D = { x: this.start.position.x, y: this.start.position.y };
		const end: Point2D = { x: this.end.position.x, y: this.end.position.y };

		Draw2D.segment(ctx, start, end);
	}
}


export class GraphRenderer2D extends AbstractGraphRenderer {

	lastGraph: Graph;

	constructor(c: Canvas) {
		super(c);
	}

	render(g: Graph) {
		this.lastGraph = g;

		this.renderVertices(g);
		this.renderEdges(g);
	}

	private renderVertices(g: Graph) {
		const ctx = this.canvas.getContext();
		const verticesWithoutDims = g.vertices.filter(ver => {
			return !ver.position && (!ver.position.x || !ver.position.y);
		});

		GraphLayoutBasic.spaceOutVerticesAtFixedDistanceRandom(verticesWithoutDims, 30);

		g.vertices
			.map(ver => {
				return new VertexRenderable(ver);
			})
			.forEach(renderable => {
				renderable.render(this);
			});
	}

	private renderEdges(g: Graph) {
		g.edges
			.map(edge => {
				const s: Vertex = g.lookupVertex(g.vertices, edge.connects[0]);
				const t: Vertex = g.lookupVertex(g.vertices, edge.connects[1]);
				return new EdgeRenderable(edge, s, t);
			})
			.forEach(edgeRenderable => {
				edgeRenderable.render(this);
			});
	}
}