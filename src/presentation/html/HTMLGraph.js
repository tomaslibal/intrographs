import GraphRenderer2D from "../GraphRenderer2D";
import { ObservableRenderable } from "../../presentation/ObservableRenderable";
import Observable from "../../common/Observable";
import HTMLList from "./HTMLList";

export default class HTMLGraph extends ObservableRenderable {

	constructor(graph, document, polymer={}) {
		const x = 0;
		const y = 0;

		super({ 'posX': x, 'posY': y });

        this.polymer = typeof Polymer !== "undefined" ? Polymer : polymer;

		this.graph = graph;
		this.x = x;
		this.y = y;
		this.document = document;

		let vertexListParentElement = document.createElement('div');

        const el = this.document.querySelector("vertex-list");

		this.vertexList = new HTMLList(document, vertexListParentElement, el, '.vertices', this.polymer);

		let edgeListParentElement = document.createElement('div');
		this.edgeList = new HTMLList(document, edgeListParentElement, el, '.edges', this.polymer);

		let graphRenderer = new GraphRenderer2D();
		let canvas = this.document.querySelector("#canvas");
		let ctx = graphRenderer.getContext(canvas);
		graphRenderer.setCanvas(canvas);

		this.graphRenderer = graphRenderer;
		this.canvas = canvas;
		this.ctx = ctx;
	}

	setUp() {
		if (this.controls) {
			this.ctrlObservable = new Observable(this.controls);
			this.ctrlObservable.subscribe('controls.add.vertex');
			this._boundHandleNewVertexEvent = this._handleNewVertexEvent.bind(this);
			this.ctrlObservable.forEach(this._boundHandleNewVertexEvent);

			this.ctrlObservableNewEdge = new Observable(this.controls);
			this.ctrlObservableNewEdge.subscribe('controls.add.edge');
			this._boundHandleNewEdgeEvent = this._handleNewEdgeEvent.bind(this);
			this.ctrlObservableNewEdge.forEach(this._boundHandleNewEdgeEvent);
		}

		if (this.eventBus) {
			this.eventBus.on('interpreter.add.vertex', (ev) => {
				this._handleNewVertexEvent(ev);
			})

			this.eventBus.on('interpreter.add.edge', (ev) => {
				this._handleNewEdgeEvent(ev);
			});
		}

		this.graph.vertices.forEach(vertex => {
			this.vertexList.list.push(vertex.name);
		});

		this.graph.edges.forEach(edge => {
			this.edgeList.list.push(edge.connects);
		});
	}

	_handleNewVertexEvent({ 'id': id, 'label': label }) {
		this.graph.addVertex({ 'name': id, 'label': label });
		this.render();

		this.vertexList.list.push(id);
		this.vertexList.render();
	}

	_handleNewEdgeEvent({ 'vertex1': v1, 'vertex2': v2 }) {
		this.graph.addEdge([v1, v2]);
		this.render();

		this.edgeList.list.push([v1, v2]);
		this.edgeList.render();
	}

	render() {
		this.graphRenderer.clearCanvas(this.ctx);
		this.graphRenderer.render(this.graph);

		this.vertexList.render();
		this.edgeList.render();
	}

	renderVertexChanges({'translatedX': translatedX, 'translatedY': translatedY}) {
		this.ctx.save();
		this.ctx.translate(-translatedX, -translatedY);
		this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
		this.ctx.restore();

		this.graphRenderer.render(this.graph);
	}

	getVertexByCoords({ 'x': x, 'y': y }) {
		const LENIENCY = 5;

		return this.graph.vertices.filter(vertex => {
			const dx = Math.abs(vertex.x - x);
			const dy = Math.abs(vertex.y - y);

			return dx <= LENIENCY && dy <= LENIENCY;
		});
	}

}
