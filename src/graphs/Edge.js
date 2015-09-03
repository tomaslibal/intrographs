/*
 * Edge is a set of two vertices that are connected by the edge.
 *
 */
export default class Edge {
    constructor([e1, e2]) {
        this.connects = [e1, e2];
    }
}
