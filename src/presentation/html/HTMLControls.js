import IRenderable from "../IRenderable";

export default class HTMLControls extends IRenderable {

	constructor(documentObj) {
		super({ 'posX': 0, 'posY': 0 });

		this.document = documentObj;
	}

	createElementAppend(elementType, parentElement) {
		return parentElement.appendChild(this.document.createElement(elementType));
	}
}