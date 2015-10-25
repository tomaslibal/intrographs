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
		this.content = null;
	}

	updateContent(newContent) {
		this.content = newContent;
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

	}
}