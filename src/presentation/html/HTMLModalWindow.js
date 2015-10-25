import ModalWindow from '../model/ModalWindow';

export default class HTMLModalWindow extends ModalWindow {

	constructor(window) {
		super(window);

		this.rendered = false;
	}

	render() {
		if (this.rendered === false) {
			this.containerElement = this.containerElement || this.document.createElement('div');
			this.containerElement.innerHTML = this.content;
			this.document.body.appendChild(this.containerElement);
			this.rendered = true;
		} else {
			this.containerElement.innerHTML = this.content;
		}

		if (this.display) {
			this.show();
		} else {
			this.hide();
		}

		super.render();
	}
}