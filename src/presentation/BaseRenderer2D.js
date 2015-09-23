/*
 * Renderer is a high level interface for drawing into HTML canvas
 */
export default class BaseRenderer2D {

    constructor(canvas) {
        this.canvas = canvas || null;
        this.originX = 0;
        this.originY = 0;
        this.prevOriginX = 0;
        this.prevOriginY = 0;

        this.paint = {
            dot(ctx, x, y, radius=1) {
                ctx.beginPath();
                /*
                 * ellipse(x, y, radiusX, radiusY, rotation, startAngle, endAngle, [anticlockwise])
                 */
                ctx.arc(x, y, radius, 0, 2 * Math.PI);
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

        if (this.canvas) {
            this.ctx = this.canvas.getContext('2d');
        }
    }

    pushTranslate(ctx, x, y) {
        this.prevOriginX = this.tX;
        this.prevOriginY = this.tY;

        this.tX = x;
        this.tY = y;

        ctx.translate(x, y);
    }

    popTranslate(ctx) {
        this.tX = this.prevOriginX;
        this.tY = this.prevOriginY;

        ctx.translate(this.tX, this.tY);
    }

    clearCanvas(ctx=null) {
        if (null === ctx) {
            ctx = this.ctx;
        }

        this.pushTranslate(ctx, 0, 0);
        ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.popTranslate(ctx);
    }

    setCanvas(canvas) {
        this.canvas = canvas;

        this.ctx = this.canvas.getContext(canvas);
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
