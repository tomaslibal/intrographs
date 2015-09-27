import IGraphFactory from './IGraphFactory';
import { Graph } from './Graph';
import { assert } from '../common/assert';


export default class ConnectedGraphFactory extends IGraphFactory {
	constructor() {
		super('Connected');
	}

	static create(k=1) {
		assert(k>=1);
	}
}