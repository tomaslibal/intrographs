/*
 * Renderer is a high level interface for drawing into HTML canvas
 */
function BaseRenderer2D(args) {
    "use strict";

    args = args || {};

    this.canvas = args.canvas || null;
}

BaseRenderer2DPrototype = {
    setCanvas: function(canvas) {
        "use strict";

        this.canvas = canvas;
    }
};

BaseRenderer2D.prototype = BaseRenderer2DPrototype;
