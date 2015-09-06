import IRenderable from "../IRenderable";

export default class HTMLControls extends IRenderable {

	constructor(documentObj) {
		super({ 'posX': 0, 'posY': 0 });

		this.document = documentObj;
	}

	createElementAppend(elementType, parentElement) {
		return parentElement.appendChild(this.document.createElement(elementType));
	}

	renderAddVertexForm() {}
	renderAddEdgeForm() {}

	render() {
		this.renderAddVertexForm();
		this.renderAddEdgeForm();
	}

	// createButtonAppend() {}
	// createInputAppend() {}

	createLabelAppend(label, parent) {
		let labelElement = this.createElementAppend('p', parent);
		labelElement.innerHTML = label;
		return labelElement;
	}
}