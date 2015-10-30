export default class HTMLElementController {

	constructor(documentObj) {
		this.document = documentObj;
	}

	createElementAppend(elementType, parentElement) {
		return parentElement.appendChild(this.document.createElement(elementType));
	}

	/**
	 * Appends ${element} to ${parent} if parent's child nodes contain no element
	 * of same className as the ${element}.
	 * 
	 * @return bool
	 */
	appendElementIfNotPresent(element, parent) {
		if (parent.querySelector(`.${element.className}`) === null) {
			parent.appendChild(element);
			return true;
		}
		return false;
	}

	createButtonAppend(label, parent) {
		let buttonElement = this.createElementAppend('button', parent);
		buttonElement.innerHTML = label;
		return buttonElement;
	}

	createInputAppend({ 'id': id, 'type': typeAttr }, parent) {
		let inputElement = this.createElementAppend('input', parent);
		inputElement.id = id;
		inputElement.type = typeAttr;
		return inputElement;
	}

	createLabelAppend(label, parent) {
		let labelElement = this.createElementAppend('p', parent);
		labelElement.innerHTML = label;
		return labelElement;
	}

	createSpanAppend({ innerHTML: innerHTML }, parent) {
		let spanElement = this.createElementAppend('span', parent);
		spanElement.innerHTML = innerHTML;
		return spanElement;
	}
}
