/*
 * Edge is a set of two vertices that are connected by the edge.
 *
 */
import { Vertex } from "./Vertex";

interface IEdge {
    source: Vertex;
    target: Vertex;
    weight: number;
}

interface IDirectedEdge extends IEdge {
    
}

export class Edge {

    /**
     * This is an array of the two vertices on which the edge is incident.
     *
     * The vertices should be represented by their id: string properties.
     */
    connects: Array<string>;

    constructor([v1, v2]) {
        this.connects = [v1, v2];
    }
}
