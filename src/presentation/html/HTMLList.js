import HTMLModalWindow from './HTMLModalWindow';

export default class HTMLList {

	constructor(documentObj=null, parentElement=null) {
		if (documentObj === null || parentElement === null) {
			throw new Error('Constructor must be supplied with the document object and a parent element');
			return;
		}

		this.document = documentObj;
		this.parent = parentElement;
		this.listElement = this.document.createElement('span');
		this.parent.appendChild(this.listElement);
		this.list = [];
		this.limit = 16;
		this.modal = null;
	}

	showAllClickHandler(event) {
		event.preventDefault();

		this.modal = this.modal || new HTMLModalWindow(this.document.defaultView);
		this.modal.display = true;
		this.modal.content = this.list.length === 0 ? '' : this.list.reduce((prev, curr, idx) => {
			return prev + ` <p>${curr}</p>`;
		});

		this.modal.render();
		this.modal.show();
	}

	render() {
		let listOfElements = this.list;
		let limitExceeded = false;

		if (this.list.length > this.limit) {
			listOfElements = this.list.slice(0, this.limit);
			limitExceeded = true;
		}

		let str = listOfElements.reduce((prev, curr, idx) => {
			return prev + ', ' + curr;
		});

		this.listElement.innerHTML = `${str}`;

		if (limitExceeded) {
			let showAllLink = this.document.createElement('a');
			showAllLink.href = '#';
			showAllLink.innerHTML = '(show all)';
			this._boundShowAllClickHandler = this.showAllClickHandler.bind(this);
			showAllLink.addEventListener('click', this._boundShowAllClickHandler);
			this.listElement.appendChild(showAllLink);
		}
	}
}