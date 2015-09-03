/*
 * Edge is a set of two vertices that are connected by the edge.
 *
 */
export default class Edge {
    constructor([v1, v2]) {
        if ("undefined" === typeof v1 ||
            "undefined" === typeof v2) {
            throw new Error("An edge needs two vertices");
        }
        this.connects = [v1, v2];
    }
}
