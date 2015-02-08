/*
 * This renderer extends the BaseRenderer2D and knows how to render graphs in
 * a 2D space.
 *
 */
function GraphRenderer2D(baseRenderer2D) {
    "use strict";

    if("object" !== typeof baseRenderer2D) {
        throw new Error("BaseRenderer2D must be an object");
        return;
    }

    this.renderer = baseRenderer2D;
    extendObj(this.__proto__, baseRenderer2D.__proto__);

    this.canvas = null;
}

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
    this.canvas = canvas;
};

GraphRenderer2D.prototype.render = function(graph) {
    "use strict";

    /*
     * Get 2D context of the canvas
     */
    var ctx = this.getContext(this.canvas);

    /*
     * Render vertices
     */
    var vertices = graph.vertices;
    var k = vertices.length;
    var i = 0;
    for(; i < k; i++) {
        this.renderer.paint.dot(ctx, vertices[i].x, vertices[i].y);
    }

};


