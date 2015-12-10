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