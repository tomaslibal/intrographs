import IconButtonFactory from '../IconButtonFactory';
import HTMLIconButton from './HTMLIconButton';
import CSSStyles from '../css/CSSStyles';

import { ObservableRenderable } from "../../presentation/ObservableRenderable";

export default class HTMLMenuBar extends ObservableRenderable {

	constructor(menuBar, parentElement=null) {
		super({'posX': menuBar.posLeft, 'posY': menuBar.posTop});

		this.menuBar = menuBar;

		this.addToggleConsoleButton();

		this.parentElement = parentElement;
		this.containerElement = null;
		this.buttons = null;
	}

	toggleConsoleClickHandler() {
		console.log(42);
	}

	addToggleConsoleButton() {
		let b = IconButtonFactory.create('toggleConsole', 'CONSOLE');

		this.boundToggleConsoleClickHandler = this.toggleConsoleClickHandler.bind(this);
		b.addClickHandler(this.boundToggleConsoleClickHandler);

		//this.menuBar.iconButtonList.push(b);
	}

	render() {
		if (this.containerElement === null && this.parentElement !== null) {
			const document = this.parentElement.ownerDocument;
			const window = document.defaultView;
			this.containerElement = document.createElement('div');

			this.styleUtil = new CSSStyles(window);

			this.styleUtil.setStyle(this.containerElement, 'position', 'absolute');
			this.styleUtil.setStyle(this.containerElement, 'bottom', '20px');
			this.styleUtil.setStyle(this.containerElement, 'right', '200px');

			this.parentElement.appendChild(this.containerElement);

			this.buttons = this.menuBar.iconButtonList
				.map(iconButtonModel => {
					return new HTMLIconButton(iconButtonModel, this.containerElement);
				})
		}

		if (this.containerElement) {
			this.buttons
				.forEach(icon => {
					icon.render();
				});
		}
	}
}