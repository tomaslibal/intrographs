import { IGraph } from "./Graph";
import { Vertex } from "./Vertex";
import { Edge } from "./Edge";

export class DirectedGraph implements IGraph {
	vertices: Array<Vertex>;
    edges: Array<Edge>;

	constructor()
	{
		this.vertices = [];
		this.edges = [];
	}

    vertexSet(): Array<Vertex>
	{
		return this.vertices;
	}

    edgeSet():   Array<Edge>
	{
		return this.edges;
	}
}