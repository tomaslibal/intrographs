import ModalWindow from '../model/ModalWindow';

export default class HTMLModalWindow extends ModalWindow {

	constructor(window) {
		super(window);

		this.rendered = false;
	}

	closeWindowClickHandler(event) {
		event.preventDefault();

	}

	getCloseWindowButton() {
		let button = this.document.createElement('a');
		button.innerHTML = 'close window';
		this._boundCloseWindowClickHandler = this.closeWindowClickHandler.bind(this);
		button.addEventListener('click', this._boundCloseWindowClickHandler);
		return button;
	}

	render() {
		if (this.rendered === false) {
			this.containerElement = this.containerElement || this.document.createElement('div');
			this.containerElement.innerHTML = this.content;
			this.document.body.appendChild(this.containerElement);
			this.rendered = true;
			this.closeButton = this.getCloseWindowButton();
			this.containerElement.appendChild(this.closeButton);
		} else {
			this.containerElement.innerHTML = this.content;
			this.containerElement.appendChild(this.closeButton);
		}

		if (this.display) {
			this.show();
		} else {
			this.hide();
		}

		super.render();
	}
}