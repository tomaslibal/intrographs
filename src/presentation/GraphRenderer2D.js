/*
 * This renderer extends the BaseRenderer2D and knows how to render graphs in
 * a 2D space.
 *
 */
import BaseRenderer2D from "./BaseRenderer2D";
import VertexRenderable from "../presentation/VertexRenderable.js";
import MathUtil from "../common/MathUtil";

export default class GraphRenderer2D extends BaseRenderer2D {

    constructor() {
        super();

        this.canvas = null;
        this.vertexCl = '#aa4400';
        this.nodeCl = '#a0a0a0';
    }

    /*
     * takes a vertex set and assigns each vertex a pair of random x, y coordinates
     *
     * @param Vertex[] graphVertices
     * @return Vertex[]
     */
    spaceOutVerticesRandom(graphVertices) {
        "use strict";

        var i = 0;

        for(; i < graphVertices.length; i++) {
            /*
             * NB: the range depends on the size of the canvas. This object does not
             * know about the canvas's dimensions so these values should be
             * injected.
             */
            graphVertices[i].x = MathUtil.getRandomArbitrary(0, 500);
            graphVertices[i].y = MathUtil.getRandomArbitrary(0, 500);
        }

        return graphVertices;
    };

    render(graph) {
        "use strict";

        /*
         * Get 2D context of the canvas
         */
        var ctx = this.getContext(this.canvas);

        /*
         * Space out vertices so that they won't overlap on the same x, y
         * coordinates.
         */
        this.spaceOutVerticesRandom(graph.vertices);

        /*
         *
         */
        this.setStrokeColor(ctx, this.vertexCl);

        /*
         * Render vertices
         */
        let vertices = graph.vertices;
        graph.vertices.map(vertex => {
            return new VertexRenderable({ 'posX': vertex.x, 'posY': vertex.y });
        }).forEach(renderable => {
            renderable.render(this);
        });

        /*
         *
         */
        this.setStrokeColor(ctx, this.nodeCl);

        /*
         * Render edges
         */
        var edges = graph.edges;
        let i = 0;
        let k = edges.length;
        /*
         * Start point of the edge (which is a line segment)
         */
        var s;
        /*
         * End point of the edge
         */
        var e;
        for(; i < k; i++) {
            s = graph.lookupVertex(vertices, edges[i][0]);
            e = graph.lookupVertex(vertices, edges[i][1]);
            this.paint.segment(ctx, s.x, s.y, e.x, e.y);
        }
    }
}
