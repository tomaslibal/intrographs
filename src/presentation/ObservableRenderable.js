import IRenderable from "./IRenderable";

class ObservableRenderable extends IRenderable {

	constructor({ 'posX': x, 'posY': y }) {
		super({ 'posX': x, 'posY': y });
	}

	addEventListener() {

	}

	removeEventListener() {

	}

}

export { ObservableRenderable };