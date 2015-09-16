let sinon = require("sinon");

let mockHTMLElement = {
	innerHTML: '',
	id: '',
	'type': '',
	className: '',
	style: {},
	value: '',
	appendChild(child) { return child; },
	addEventListener: sinon.stub(),
	querySelector: sinon.stub()
};
sinon.spy(mockHTMLElement, 'appendChild');

let mockDocumentBody = {
	querySelector: sinon.stub().returns(null),
	appendChild: sinon.stub()
}

let mockDocument = {
	createElement: sinon.stub().returns(mockHTMLElement),
	querySelector: sinon.stub(),
	body: mockDocumentBody,
	appendChild: sinon.stub()
};

let mockWindow = {
	document: mockDocument,
	getComputedStyle: sinon.stub()
};

export { mockDocument, mockHTMLElement, mockDocumentBody, mockWindow };