import { ObservableRenderable } from '../ObservableRenderable';

export default class ModalWindow extends ObservableRenderable {
	constructor(document) {
		super({'posX': 0, 'posY': 0});

		this.x = 0;
		this.y = 0;
		this.width = 100;
		this.height = 100;
		this.innerHTML = '';
		this.display = false;
		this.containerElement = null;
		this.document = document;
	}

	show() {
		this.display = true;
		this.notify('visibilityChange', {'display': true});
	}
	hide() {
		this.display = false;
		this.notify('visibilityChange', {'display':false});
	}

	render() {
		if (this.containerElement === null) {
			this.containerElement = this.document.createElement('div');
			this.containerElement.innerHTML = this.innerHTML;
			this.document.body.appendChild(this.containerElement);
		}
	}
}