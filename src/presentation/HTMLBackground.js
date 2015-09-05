import IRenderable from "./IRenderable";

export default class HTMLBackground extends IRenderable {

	constructor(documentObj) {
		super({ 'posX': 0, 'posY': 0 });

		this.height = 480;
		this.width = 640;
		this.document = documentObj;

		this.bg = this.document.createElement('div');
	}

	render() {

	}
}