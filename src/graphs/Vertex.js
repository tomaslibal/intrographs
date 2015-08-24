/*
 * Vertex JavaScript constructor
 *
 * Each vertex of a graph is an object constructed by this function.
 */
export default class Vertex {
    constructor() {
        /*
         * Ensure that props is not null but rather is an object;
         */
        props = props || {};

        /*
         * For graphical purposes, a vertex has [x, y] coordinates in the pixel
         * space associated with it.
         */
        this.x = props.x || 0;
        this.y = props.y || 0;

        /*
         * Arbitrary name
         *
         */
        this.name = props.name || null;
    }
}
