export interface CanvasSettings {
	width: number;
	height: number;
	context?: string;
}

export class Canvas {

	private canvas: HTMLCanvasElement;
	private context: CanvasRenderingContext2D;
	private settings: CanvasSettings;

	constructor(canvas: HTMLCanvasElement, settings?: CanvasSettings) {
		this.canvas = canvas;

		this.context = this.canvas.getContext("2d");

		if (settings) {
			this.config(settings);
		}
	}

	config(settings: CanvasSettings) {
		this.settings = settings;
		this.applySettings();
	}

	private applySettings() {
		this.canvas.width = this.settings.width;
		this.canvas.height = this.settings.height;
	}

	getCanvas(): HTMLCanvasElement {
		return this.canvas;
	}

	getContext(): CanvasRenderingContext2D {
		return this.context;
	}
}

export interface Point2D {
	x: number,
	y: number
}

export class Draw2D {

	static ellipse() {

	}

	static circle(ctx: CanvasRenderingContext2D, center: Point2D, radius: number) {
		ctx.beginPath();
		ctx.arc(center.x, center.y, radius, 0, 2 * Math.PI);
		ctx.stroke();
		ctx.closePath();
	}

	static segment(ctx, start: Point2D, end: Point2D) {
		ctx.beginPath();
		ctx.moveTo(start.x, start.y);
		ctx.lineTo(end.x, end.y);
		ctx.stroke();
		ctx.closePath();
	}

}