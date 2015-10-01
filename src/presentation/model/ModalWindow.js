import { ObservableRenderable } from '../ObservableRenderable';

export default class ModalWindow extends ObservableRenderable {
	constructor() {
		super({'posX': 0, 'posY': 0});

		this.x = 0;
		this.y = 0;
		this.width = 100;
		this.height = 100;
		this.innerHTML = '';
		this.display = false;
	}

	show() {
		this.display = true;
		this.notify('show', {'show': true});
	}
	hide() {
		this.display = false;
		this.notify('show', {'show':false});
	}
}