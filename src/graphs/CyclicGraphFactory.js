import IGraphFactory from './IGraphFactory';
import { Graph } from './Graph';
import { assert } from '../common/assert';

export default class CyclicGraphFactory extends IGraphFactory {

	constructor() {
		super('Cyclic');
	}

	static create(v=3) {
		assert(v>=3);

		let c = new Graph();

		// add vertices
		for(let i = 0; i < v; i++) {
			c.addVertex({'name': `v${i}`});
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