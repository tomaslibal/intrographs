/*
 * Vertex JavaScript constructor
 *
 * Each vertex of a graph is an object constructed by this function.
 */
export default class Vertex {
    constructor(p={}) {

        /*
         * Arbitrary name/identifier
         *
         */
        this.name = p.name || null;

        /*
         * Optional label
         *
         */
        this.label = p.label || null;

        this.symbol = Symbol(this.name);
    }
}
