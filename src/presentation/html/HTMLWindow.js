export default class HTMLWindow {

	constructor(winObj=null) {
		if (winObj === null) {
			throw new Error('A window object must be passed to the constructor');
			return;
		}
		this.window = winObj;
	}

	dims() {
		return [this.window.innerWidth, this.window.innerHeight];
	}
}