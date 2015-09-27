export default class IGraphFactory {
	constructor(graphType='') {
		throw new Error(`Use the static method 'create' to create ${graphType} Graphs`);
	}

	static create() {

	}
}