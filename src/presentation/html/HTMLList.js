export default class HTMLList {

	constructor(parentElement=null) {
		if (parentElement === null) {
			throw new Error('Constructor must be supplied with a parent element');
			return;
		}

		this.parent = parentElement;
		this.list = [];
	}
}