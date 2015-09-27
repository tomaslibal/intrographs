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
        this.edgeCl = '#a0a0a0';
    }

    /*
     * takes a vertex set and assigns each vertex a pair of random x, y coordinates
     *
     * @param Vertex[] graphVertices
     * @return Vertex[]
     */
    spaceOutVerticesRandom(graphVertices) {
        "use strict";

        let i = 0;

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

    spaceOutVerticesAtFixedDistanceRandom(vertexList, distance=15) {
        return vertexList.forEach((vertex, idx, all) => {
            if (idx === 0 && !this.lastVertexX) {
                vertex.x = MathUtil.getRandomArbitrary(100, 500);
                vertex.y = MathUtil.getRandomArbitrary(0, 500);
            } else if(idx === 0 && this.lastVertexX) {
                const coords = this.findRandomCoordsAtGivenDistance(this.lastVertexX, this.lastVertexY, distance);
                vertex.x = coords[0];
                vertex.y = coords[1];
            } else {
                const prev = all[idx-1];
                const coords = this.findRandomCoordsAtGivenDistance(prev.x, prev.y, distance);
                vertex.x = coords[0];
                vertex.y = coords[1];
            }
            this.lastVertexX = vertex.x;
            this.lastVertexY = vertex.y;
        });
    }

    /*
     * Finds a random point at a given distance from the origin point.
     * In other words, the origin is a center of a circle of radius=distance and
     * the result is a random point (x, y) on that circle.
     *
     */
    findRandomCoordsAtGivenDistance(originX, originY, distance=15) {
        const randX = MathUtil.getRandomArbitrary(originX-distance, originX+distance);

        const c = (distance*distance - originX*originX - originY*originY + 2*originX*randX - randX*randX);
        const b = +2*originY;
        const a = -1;

        let res = MathUtil.quadRoots(a, b, c);

        // if discrimintant < 0, we would get no solution
        while(res === null) {
            res = this.findRandomCoordsAtGivenDistance(originX, originY, distance);
        }

        // if discriminant == 0 we only get one solution
        if (res.length === 1) {
            return [randX, res[0]];
        }

        // else, discriminant > 0 and we have two solutions for the point Y
        // and we will choose one at random
        const useFirstOrSecondSolution = MathUtil.getRandomArbitrary(0, 1);
        return [randX, res[useFirstOrSecondSolution]];
    }

    checkVertexCollision(v1, v2) {
        const LENIENCY = 5; // vertices must be at least this much apart

        const aDifX = Math.abs(v1.x - v2.x);
        const aDifY = Math.abs(v1.y - v2.y);

        if (aDifX < LENIENCY && aDifY < LENIENCY) {
            return true;
        } else {
            return false;
        }
    }

    render(graph) {
        "use strict";

        /*
         * Get 2D context of the canvas
         */
        let ctx = this.getContext(this.canvas);

        /*
         * Space out vertices so that they won't overlap on the same x, y
         * coordinates.
         *
         * Do this only to vertices that have no {x, y} coordinates yet
         */
        let verticesNeedingDimensions = graph.vertices.filter(ver => {
           return 'undefined' === typeof ver.x || 'undefined' === typeof ver.y;
        });

        this.spaceOutVerticesAtFixedDistanceRandom(verticesNeedingDimensions, 30);

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
        this.setStrokeColor(ctx, this.edgeCl);

        /*
         * Render edges
         */
        let edges = graph.edges;
        let i = 0;
        let k = edges.length;
        /*
         * Start point of the edge (which is a line segment)
         */
        let s;
        /*
         * End point of the edge
         */
        let e;
        for(; i < k; i++) {
            s = graph.lookupVertex(vertices, edges[i].connects[0]);
            e = graph.lookupVertex(vertices, edges[i].connects[1]);
            this.paint.segment(ctx, s.x, s.y, e.x, e.y);
        }
    }
}
