import ModalWindow from '../model/ModalWindow';
import CSSStyles from '../css/CSSStyles';

export default class HTMLModalWindow extends ModalWindow {

	constructor(window) {
		super(window);

		this.cssUtil = new CSSStyles(window);

		this.rendered = false;
	}

	closeWindowClickHandler(event) {
		event.preventDefault();

		if (this.containerElement) {
			this.cssUtil.setStyle(this.containerElement, 'display', 'none');
		}

		super.hide();
	}

	show() {

		if (this.containerElement) {
			this.cssUtil.setStyle(this.containerElement, 'display', 'block');
		}

		super.show();
	}

	hide() {
		if (this.containerElement) {
			this.cssUtil.setStyle(this.containerElement, 'display', 'none');
		}

		super.hide();
	}

	getCloseWindowButton() {
		let button = this.document.createElement('a');
		button.innerHTML = 'close window';
		this._boundCloseWindowClickHandler = this.closeWindowClickHandler.bind(this);
		button.addEventListener('click', this._boundCloseWindowClickHandler);
		return button;
	}

	setStyles() {
		let styles = {
			'position': 'absolute',
			'top': '10px',
			'left': '100px',
			'width': '500px',
			'height': '500px',
			'overflow-y': 'scroll',
			'padding': '5px',
			'background': '#f0f0f0'
		};

		if (this.containerElement) {
			Object.keys(styles).forEach(key => {
				const value = styles[key];
				console.log(`setting ${key}=${value}`);
				this.cssUtil.setStyle(this.containerElement, key, value);
			});
		}
	}

	render() {
		if (this.rendered === false) {
			this.containerElement = this.containerElement || this.document.createElement('div');
			this.containerElement.innerHTML = this.content;
			this.document.body.appendChild(this.containerElement);
			this.setStyles();
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