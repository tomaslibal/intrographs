import { ObservableRenderable } from "../../presentation/ObservableRenderable";

export default class HTMLControls extends ObservableRenderable {

	constructor(documentObj) {
		super({ 'posX': 0, 'posY': 0 });

		this.document = documentObj;
	}

	createElementAppend(elementType, parentElement) {
		return parentElement.appendChild(this.document.createElement(elementType));
	}

	appendElementIfNotPresent(element, parent) {
		if (parent.querySelector(`.${element.className}`) === null) {
			parent.appendChild(element);
		}
	}

	renderAddVertexForm() {
		let label = null;
		let inputId = null;
		let inputLabel = null;
		let buttonAdd = null;

		if ('undefined' === typeof this.addVertexForm) {
			this.addVertexForm = this.document.createElement('div');
			this.addVertexForm.className = 'addVertexForm';

			label = this.createLabelAppend('Add Vertex', this.addVertexForm);
			inputId = this.createInputAppend({ id: 'vertexId', 'type': 'text' }, this.addVertexForm);
			inputLabel = this.createInputAppend({ id: 'vertexLabel', 'type': 'text' }, this.addVertexForm);
			buttonAdd = this.createButtonAppend('Add', this.addVertexForm);
		}

		this.appendElementIfNotPresent(this.addVertexForm, this.document.body);
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