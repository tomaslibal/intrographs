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

	setStyle(element, styleName, value) {
		element.style[styleName] = value;
	}

    setStyles(element, styles={}) {
        Object.keys(styles).forEach(styleName => {
            const val = styles[styleName];
            this.setStyle(element, styleName, val);
        });
    }
}
