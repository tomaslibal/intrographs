/*
 * Renderer is a high level interface for drawing into HTML canvas
 */
export default class BaseRenderer2D {

    constructor(canvas) {
        this.canvas = canvas || null;

        this.paint = {
            dot(ctx, x, y) {
                ctx.beginPath();
                /*
                 * ellipse(x, y, radiusX, radiusY, rotation, startAngle, endAngle, [anticlockwise])
                 */
                ctx.arc(x, y, 1, 1, 2 * Math.PI);
                //ctx.ellipse(x, y, 1, 1, 0, 0, 2*Math.PI); // not supported elsewhere than in Chrome

                ctx.stroke();
            },
            segment(ctx, x0, y0, x1, y1) {
                ctx.beginPath();
                ctx.moveTo(x0, y0);
                ctx.lineTo(x1, y1);
                ctx.stroke();
            }
        }
    }

    clearCanvas(ctx) {
        ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
    }

    setCanvas(canvas) {
        this.canvas = canvas;
    }
    setStrokeColor(ctx, color) {
        ctx.strokeStyle = color;
    }
    getContext(canvas) {
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
    }
}
