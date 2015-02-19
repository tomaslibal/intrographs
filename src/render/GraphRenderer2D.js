/*
 * This renderer extends the BaseRenderer2D and knows how to render graphs in
 * a 2D space.
 *
 */
function GraphRenderer2D(baseRenderer2D) {
    "use strict";

    if("object" !== typeof baseRenderer2D) {
        throw new Error("BaseRenderer2D must be an object");
    }

    this.renderer = baseRenderer2D;
    /*
     * __proto__ is DEPRECATED!
     */
    extendObj(this.__proto__, baseRenderer2D.__proto__);

    this.canvas = null;

    this.vertexCl = '#aa4400';
    this.nodeCl = '#a0a0a0';
}

/*
 * Takes two objects `base` and `extra` and extends `base` with all properties
 * of `extra`.
 *
 * Example
 *
 * var base = { bar: 1, foo: 2 };
 * var extra = { focia: 'foo', baz: 10 };
 * 
 * extendObj(base, extra) => {
 *   bar: 1, foo: 2, focia: 'foo', baz: 10
 * }
 *
 */
function extendObj(base, extra) {
    "use strict";

    var prop;

    for(prop in extra) {
        if (!extra.hasOwnProperty(prop)) {
            continue;
        }

        base[prop] = extra[prop];
    }

    return base;
}

GraphRenderer2D.prototype.setCanvas = function(canvas) {
    "use strict";

    this.canvas = canvas;
};

GraphRenderer2D.prototype.render = function(graph) {
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
        this.renderer.paint.segment(s.x, s.y, e.x, e.y);
    }
};


