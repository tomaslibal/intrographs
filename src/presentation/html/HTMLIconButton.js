import { ObservableRenderable } from "../../presentation/ObservableRenderable";

export default class HTMLImageButton extends ObservableRenderable {
	constructor(iconModel, parentElement) {
		super();

		this.iconModel = iconModel;
		this.parentElement = parentElement;
		this.element = null;
	}

	render() {
		if (this.element === null) {
			let document = this.parentElement.ownerDocument;
			let button = document.createElement('button');
			button.innerHTML = this.iconModel.id;
			this.parentElement.appendChild(button);
			console.log('rendergin!!!' + this.iconModel.id);
		}

	}
}