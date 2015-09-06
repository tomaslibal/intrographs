import IRenderable from "../IRenderable";

export default class HTMLControls extends IRenderable {

	constructor(documentObj) {
		super({ 'posX': 0, 'posY': 0 });

		this.document = documentObj;
	}

	createElementAppend(elementType, parentElement) {
		return parentElement.appendChild(this.document.createElement(elementType));
	}

	renderAddVertexForm() {
		if ('undefined' === typeof this.addVertexForm) {
			this.addVertexForm = this.document.createElement('div');
		}
	}

	renderAddEdgeForm() {}

	render() {
		this.renderAddVertexForm();
		this.renderAddEdgeForm();
	}

	createButtonAppend(label, parent) {
		let buttonElement = this.createElementAppend('button', parent);
		buttonElement.innerHTML = label;
		return buttonElement;
	}

	createInputAppend({ 'id': id, 'type': typeAttr }, parent) {
		let inputElement = this.createElementAppend('input', parent);
		inputElement.id = id;
		inputElement.type = typeAttr;
		return inputElement;
	}

	createLabelAppend(label, parent) {
		let labelElement = this.createElementAppend('p', parent);
		labelElement.innerHTML = label;
		return labelElement;
	}
}