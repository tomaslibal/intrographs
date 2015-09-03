/*
 * Edge is a set of two vertices that are connected by the edge.
 *
 */
export default class Edge {
    constructor([v1, v2]) {
        this.connects = [v1, v2];
    }
}
