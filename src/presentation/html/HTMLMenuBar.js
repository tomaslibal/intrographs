import IconButtonFactory from '../IconButtonFactory';

export default class HTMLMenuBar {

	constructor(menuBar) {
		this.menuBar = menuBar;

		this.addToggleConsoleButton();
	}

	toggleConsoleClickHandler() {
		console.log(42);
	}

	addToggleConsoleButton() {
		let b = IconButtonFactory.create('toggleConsole', 'CONSOLE');

		this.boundToggleConsoleClickHandler = this.toggleConsoleClickHandler.bind(this);
		b.addClickHandler(this.boundToggleConsoleClickHandler);

		this.menuBar.iconButtonList.push(b);
	}

	render() {
		
	}
}