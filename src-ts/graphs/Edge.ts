/*
 * Edge is a set of two vertices that are connected by the edge.
 *
 */
import { Vertex } from "./Vertex";

interface IEdge {
    source: Vertex;
    target: Vertex;

    isIncidentOn(v: Vertex): boolean;
}

interface IWeightedEdge extends IEdge {
    weight: number;
}

export class DefaultEdge implements IEdge {
    source: Vertex;
    target: Vertex;

    constructor(source: Vertex, target: Vertex) {
        this.source = source;
        this.target = target;
    }

    isIncidentOn(v: Vertex): boolean
    {
        if (this.source === v || this.target === v) {
            return true;
        } else {
            return false;
        }
    }
}

export class WeightedEdge implements IWeightedEdge {
    source: Vertex;
    target: Vertex;
    weight: number;

    constructor(source: Vertex, target: Vertex, w: number) {
        this.source = source;
        this.target = target;
        this.weight = w;
    }

    isIncidentOn(v: Vertex): boolean
    {
        if (this.source === v || this.target === v) {
            return true;
        } else {
            return false;
        }
    }

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
