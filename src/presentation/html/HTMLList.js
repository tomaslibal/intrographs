import HTMLModalWindow from './HTMLModalWindow';

export default class HTMLList {

	constructor(documentObj=null, parentElement=null, polymerElement=null, inner=null, polymer=null) {
		if (documentObj === null || parentElement === null) {
			throw new Error('Constructor must be supplied with the document object and a parent element');
			return;
		}

        this.polymer = polymer;

        this.polymerElement = polymerElement;
        this.inner = inner;
		this.document = documentObj;
		this.parent = parentElement;
		this.list = [];
		this.limit = 16;
		this.modal = null;
        this.polymerElementReady = false;

        this.polymerElement.addEventListener('ready', () => {
            this.polymerElementReady = true;
            this.polymer = typeof Polymer !== "undefined" ? Polymer : this.polymer;
            this.render();
        });
        this.clickRegistered = false;
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
        if (!this.polymerElementReady) {
            return;
        }

		let listOfElements = this.list;
		let limitExceeded = false;

		if (this.list.length > this.limit) {
			listOfElements = this.list.slice(0, this.limit);
			limitExceeded = true;
		}

		let str = listOfElements.reduce((prev, curr, idx) => {
			return prev + ', ' + curr;
		});
        
        const findPolymer = this.polymer.dom(this.polymerElement);

        if (!findPolymer) {
            return;
        }
    
        findPolymer.node.querySelector(this.inner).innerHTML = str;

        let showAllBt = this.polymer.dom(this.polymerElement).node.querySelector(this.inner).parentElement.querySelector('.all');

		if (limitExceeded && !this.clickRegistered) {
        	this._boundShowAllClickHandler = this.showAllClickHandler.bind(this);
			showAllBt.addEventListener('click', this._boundShowAllClickHandler);
            this.clickRegistered = true;
		}
        
        if (limitExceeded) {
            showAllBt.style.display = 'inline';   
        } else {
            showAllBt.style.display = 'none';
        }
	}
}
