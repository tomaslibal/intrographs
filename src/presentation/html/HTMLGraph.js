import GraphRenderer2D from "../GraphRenderer2D";
import { ObservableRenderable } from "../../presentation/ObservableRenderable";
import Observable from "../../common/Observable";
import HTMLList from "./HTMLList";

export default class HTMLGraph extends ObservableRenderable {

	constructor(graph, document) {
		const x = 0;
		const y = 0;

		super({ 'posX': x, 'posY': y });

		this.graph = graph;
		this.x = x;
		this.y = y;
		this.document = document;

		let vertexListParentElement = document.createElement('div');
		vertexListParentElement.style.position = 'absolute';
		vertexListParentElement.style.left = '10px';
		vertexListParentElement.style.bottom = '40px';
		vertexListParentElement.innerHTML = '<span>Vertices: </span>';
		document.body.appendChild(vertexListParentElement);
		this.vertexList = new HTMLList(document, vertexListParentElement);

		let edgeListParentElement = document.createElement('div');
		edgeListParentElement.style.position = 'absolute';
		edgeListParentElement.style.left = '10px';
		edgeListParentElement.style.bottom = '20px';
		edgeListParentElement.innerHTML = '<span>Edges: </span>';
		document.body.appendChild(edgeListParentElement);
		this.edgeList = new HTMLList(document, edgeListParentElement);

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

}