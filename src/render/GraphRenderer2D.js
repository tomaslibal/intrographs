/*
 * This renderer extends the BaseRenderer2D and knows how to render graphs in
 * a 2D space.
 *
 */
import BaseRenderer2D from "BaseRenderer2D";

export default class GraphRenderer2D extends BaseRenderer2D {

    constructor(baseRenderer2D) {
        if("object" !== typeof baseRenderer2D) {
            throw new Error("BaseRenderer2D must be an object");
        }

        super();

        this.renderer = baseRenderer2D;
        this.canvas = null;
        this.vertexCl = '#aa4400';
        this.nodeCl = '#a0a0a0';
    }

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
        graph.spaceOutVerticesRandom(graph.vertices);

        /*
         *
         */
        this.renderer.setStrokeColor(this.vertexCl);

        /*
         * Render vertices
         */
        var vertices = graph.vertices;
        var k = vertices.length;
        var i = 0;
        for(; i < k; i++) {
            this.renderer.paint.dot(ctx, vertices[i].x, vertices[i].y);
        }

        /*
         *
         */
        this.renderer.setStrokeColor(this.nodeCl);

        /*
         * Render edges
         */
        var edges = graph.edges;
        i = 0;
        k = edges.length;
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
            this.renderer.paint.segment(ctx, s.x, s.y, e.x, e.y);
        }
    }
}
