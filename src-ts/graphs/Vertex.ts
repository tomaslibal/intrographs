/*
 * Vertex JavaScript constructor
 *
 * Each vertex of a graph is an object constructed by this function.
 */
export interface VertexProperties {
    id: string;
    name?: string;
}

export class Vertex {

    id:     string;
    name:   string;
    symbol: Symbol;

    constructor(p: VertexProperties) {

        /*
         * Vertex identifier
         *
         */
        this.id = p.id;

        /**
         * Optional name/label
         */
        this.name = p.name || null;

        this.symbol = Symbol(this.name);
    }
}
