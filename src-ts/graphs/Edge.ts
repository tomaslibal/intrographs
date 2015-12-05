/*
 * Edge is a set of two vertices that are connected by the edge.
 *
 */
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
