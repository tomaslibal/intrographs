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
            ctx = canvas.getContext("2d");
        } catch (e) {
            return 3;
        }

        return ctx;
    },
    paint: {
        dot: function(ctx, x, y) {
            "use strict";

            ctx.beginPath();
            /*
             * ellipse(x, y, radiusX, radiusY, rotation, startAngle, endAngle, [anticlockwise])
             */
            ctx.ellipse(x, y, 1, 1, 0, 0, 2*Math.PI);

            ctx.stroke();
        }

    }
};

BaseRenderer2D.prototype = BaseRenderer2DPrototype;
