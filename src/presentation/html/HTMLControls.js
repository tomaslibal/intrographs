import { ObservableRenderable } from "../../presentation/ObservableRenderable";
import CSSStyles from "../../presentation/css/CSSStyles";
import HTMLElementController from './HTMLElementController';
import { extendPlainObj } from '../../common/eloquent';

export default class HTMLControls extends ObservableRenderable {

	constructor(windowObj) {
		super({ 'posX': 0, 'posY': 0 });

		let documentObj = windowObj.document;

		this.document = documentObj;
		this.cssStyles = new CSSStyles(windowObj);
		this.elementCtrl = new HTMLElementController(documentObj);

        this.commonBoxStyles = {
            'position': 'absolute',
            'background': '#fff',
            'padding': '10px',
            'box-shadow': 'rgb(210, 210, 210) 1px 1px 5px',
            'width': '170px'
        };
	}

	createElementAppend(elementType, parentElement) {
		return this.elementCtrl.createElementAppend(elementType, parentElement);
	}

	appendElementIfNotPresent(element, parent) {
		return this.elementCtrl.appendElementIfNotPresent(element, parent);
	}

	_addVertexButtonHandler() {
		let vertexId = this.addVertexForm.querySelector('#vertexId');
		let vertexLabel = this.addVertexForm.querySelector('#vertexLabel');

		const id = vertexId.value;
		const label = vertexLabel.value;

		this.notify('controls.add.vertex', { 'id': id, 'label': label });

		vertexId.value = '';
		vertexLabel.value = '';
	}

	_addEdgeButtonHandler() {
		let vertex1 = this.addEdgeForm.querySelector('#vertex1');
		let vertex2 = this.addEdgeForm.querySelector('#vertex2');

		const v1 = vertex1.value;
		const v2 = vertex2.value;

		this.notify('controls.add.edge', { 'vertex1': v1, 'vertex2': v2 });

		vertex1.value = '';
		vertex2.value = '';
	}

	renderAddVertexForm() {
		let label = null;
		let spanId = null;
		let inputId = null;
		let spanLabel = null;
		let inputLabel = null;
		let buttonAdd = null;

		if ('undefined' === typeof this.addVertexForm) {
			this.addVertexForm = this.document.createElement('div');
			this.addVertexForm.className = 'addVertexForm';

			label = this.createLabelAppend('Add Vertex', this.addVertexForm);
			spanId = this.createSpanAppend({ innerHTML: 'ID' }, this.addVertexForm);
			inputId = this.createInputAppend({ id: 'vertexId', 'type': 'text' }, this.addVertexForm);
			spanLabel = this.createSpanAppend({ innerHTML: 'Label' }, this.addVertexForm);
			inputLabel = this.createInputAppend({ id: 'vertexLabel', 'type': 'text' }, this.addVertexForm);
			buttonAdd = this.createButtonAppend('Add', this.addVertexForm);

            const vertexFormStyles = extendPlainObj(this.commonBoxStyles, {
                'top': '10px',
                'right': '10px'
            });
            
            this.cssStyles.setStyles(this.addVertexForm, vertexFormStyles);
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

            const edgeFormStyles = extendPlainObj(this.commonBoxStyles, {
                'top': '120px',
                'right': '10px'
            });

            this.cssStyles.setStyles(this.addEdgeForm, edgeFormStyles);
		}

		if (this.appendElementIfNotPresent(this.addEdgeForm, this.document.body)) {
			this._boundAddEdgeButtonHandler = this._addEdgeButtonHandler.bind(this);
			buttonAdd.addEventListener('click', this._boundAddEdgeButtonHandler);
		}
	}

	render() {
		this.renderAddVertexForm();
		this.renderAddEdgeForm();
	}

	createButtonAppend(label, parent) {
		return this.elementCtrl.createButtonAppend(label, parent);
	}

	createInputAppend({ 'id': id, 'type': typeAttr }, parent) {
		return this.elementCtrl.createInputAppend({ 'id': id, 'type': typeAttr }, parent);
	}

	createLabelAppend(label, parent) {
		return this.elementCtrl.createLabelAppend(label, parent);
	}

	createSpanAppend({ innerHTML: innerHTML }, parent) {
		return this.elementCtrl.createSpanAppend({ innerHTML: innerHTML }, parent);
	}
}
