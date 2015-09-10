export default class HTMLList {

	constructor(documentObj=null, parentElement=null) {
		if (documentObj === null || parentElement === null) {
			throw new Error('Constructor must be supplied with the document object and a parent element');
			return;
		}

		this.document = documentObj;
		this.parent = parentElement;
		this.listElement = this.document.createElement('div');
		this.parent.appendChild(this.listElement);
		this.list = [];
	}

	render() {
		let str = this.list.reduce((prev, curr, idx) => {
			return prev + ', ' + curr;
		});

		this.listElement.innerHTML = str;
	}
}