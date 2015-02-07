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
    },
    getContext: function(canvas) {
        "use strict";

        var ctx = null;

        if (!canvas || "object" !== typeof canvas) {
            return 1;
        }

        if (canvas.toString && canvas.toString() !== "[object HTMLCanvasElement]") {
            return 2;
        }

        try {
            ctx = canvas.getContext2d();
        } catch (e) {
            return 3;
        }

        return ctx;
    }
};

BaseRenderer2D.prototype = BaseRenderer2DPrototype;
