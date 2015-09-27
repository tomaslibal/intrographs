import iconButtonImages from './iconButtonImages';
import { ObservableRenderable } from '../ObservableRenderable';

export default class IconButton extends ObservableRenderable {

	constructor() {
		super();

		this.icon = iconButtonImages.DIVIDER;
		this.width = 64;
		this.height = 64;
		this.disabled = false;
		this.clickHandlers = [];
	}

	addClickHandler(callback, name=null) {
		this.clickHandlers.push(callback);
	}
}