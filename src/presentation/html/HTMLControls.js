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
			return true;
		}
		return false;
	}

	_addVertexButtonHandler() {
		const id = 'testId';
		const label = 'testLabel';
		this.notify('controls.add.vertex', { 'id': id, 'label': label });
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

		if (this.appendElementIfNotPresent(this.addVertexForm, this.document.body)) {
			this._boundAddVertexButtonHandler = this._addVertexButtonHandler.bind(this);
			buttonAdd.addEventListener('click', this._boundAddVertexButtonHandler);
		}
	}

	renderAddEdgeForm() {
		let label = null;
		let spanVertex1 = null;
		let input1 = null;
		let spanVertex2 = null;
		let input2 = null;
		let buttonAdd = null;

		if('undefined' === typeof this.addEdgeForm) {
			this.addEdgeForm = this.document.createElement('div');
			this.addEdgeForm.className = 'addEdgeForm';


			label = this.createLabelAppend('Add Edge', this.addEdgeForm);
			spanVertex1 = this.createSpanAppend({ innerHTML: 'Vertex 1'}, this.addEdgeForm);
			input1 = this.createInputAppend({ id: 'vertex1', 'type': 'text' }, this.addEdgeForm);
			spanVertex2 = this.createSpanAppend({ innerHTML: 'Vertex 2' }, this.addEdgeForm);
			input2 = this.createInputAppend({ id: 'vertex2', 'type': 'text' }, this.addEdgeForm);
			buttonAdd = this.createButtonAppend('Add Edge', this.addEdgeForm);
		}

		if (this.appendElementIfNotPresent(this.addEdgeForm, this.document.body)) {
			
		}
	}

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

	createSpanAppend({ innerHTML: innerHTML }, parent) {
		let spanElement = this.createElementAppend('span', parent);
		spanElement.innerHTML = innerHTML;
		return spanElement;
	}
}