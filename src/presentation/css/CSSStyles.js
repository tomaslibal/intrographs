export default class CSSStyles {

	constructor(windowObj=null) {
		if (windowObj === null) {
			throw new Error('You must supply the window object to the constructor');
			return;
		}

		this.window = windowObj;
	}

	getAllStyles(element=null) {
		return this.window.getComputedStyle(element);
	}

	getStyle(element, styleName) {
		const styles = this.getAllStyles(element);
		return styles[styleName];
	}
}