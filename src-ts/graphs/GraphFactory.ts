import { Graph } from './Graph';
import { assert } from '../common/assert';

export interface InterfaceGraphFactory {
}

export class BaseGraphFactory implements InterfaceGraphFactory {
	constructor(gtype: string='n/a') {
		throw new Error(`Use the static method 'create' to create ${gtype} Graphs`);
	}

	static create() {}
}

/*
 * Returns an instance of the Graph class.
 *
 */
export class NamedGraphFactory extends BaseGraphFactory {

	constructor(gtype: string='NamedGraph') {
		super(gtype);
	}

	static fromName() {

	}

	static withVertexNum() {

	}

	static fromAdjacencyMatrix() {

	}

}

export class CyclicGraphFactory extends BaseGraphFactory {

	constructor() {
		super('Cyclic');
	}

	static create(v=3) {
		assert(v>=3);

		let c = new Graph();

		// add vertices
		for(let i = 0; i < v; i++) {
			c.addVertex({'id': `v${i}`});
		}

		// add cyclical edges
		c.vertices.forEach((val, idx, arr) => {
			if (idx < (arr.length-1)) {
				// connect the current and the next vertices
				c.addEdge([`v${idx}`, `v${idx+1}`]);
			} else {
				// connect the last and the first vertices
				c.addEdge([`v0`, `v${idx}`]);
			}
		});

		return c;
	}
}

export class ConnectedGraphFactory extends BaseGraphFactory {
	constructor() {
		super('Connected');
	}

	static create(k=1) {
		assert(k>=1);
	}
}
