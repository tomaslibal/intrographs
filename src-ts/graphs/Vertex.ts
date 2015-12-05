/*
 * Vertex JavaScript constructor
 *
 * Each vertex of a graph is an object constructed by this function.
 */
export interface VertexProperties {
    id: string;
    name?: string;
}

export interface VertexPosition2D {
    x: number;
    y: number;
}

export class Vertex {

    id:     string;
    name:   string;
    symbol: Symbol;

    position: VertexPosition2D;

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

    setPosition(p: VertexPosition2D) {
        this.position = p;
    }

    getPosition(): VertexPosition2D {
        return this.position;
    }
}
