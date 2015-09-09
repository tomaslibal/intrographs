export default class HTMLList {

	constructor(parentElement=null) {
		if (parentElement === null) {
			throw new Error('Constructor must be supplied with a parent element');
			return;
		}

		this.parent = parentElement;
		this.list = [];
	}

	render() {
		let str = this.list.reduce((prev, curr, idx) => {
			return prev + ', ' + curr;
		});

		this.parent.innerHTML = str;
	}
}