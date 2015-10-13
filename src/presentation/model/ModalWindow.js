import { ObservableRenderable } from '../ObservableRenderable';
import CSSStyles from '../css/CSSStyles';

export default class ModalWindow extends ObservableRenderable {
	constructor(window) {
		super({'posX': 0, 'posY': 0});

		this.x = 0;
		this.y = 0;
		this.width = 100;
		this.height = 100;
		this.innerHTML = '';
		this.display = false;
		this.containerElement = null;
		this.document = window.document;
		this.cssUtil = new CSSStyles(window);
	}

	show() {

		if (this.containerElement) {
			this.cssUtil.setStyle(this.containerElement, 'display', 'block');
		}

		if (this.display != true) {
			this.notify('visibilityChange', {'display': true});
		}

		this.display = true;
	}
	hide() {
		
		if (this.containerElement) {
			this.cssUtil.setStyle(this.containerElement, 'display', 'none');
		}

		if (this.display != false) {
			this.notify('visibilityChange', {'display':false});
		}

		this.display = false;
	}

	render() {
		if (this.containerElement === null) {
			this.containerElement = this.document.createElement('div');
			this.containerElement.innerHTML = this.innerHTML;
			if (this.display) {
				this.show();
			} else {
				this.hide();
			}
			this.document.body.appendChild(this.containerElement);
		}
	}
}