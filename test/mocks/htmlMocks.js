import { extendObj as extend } from '../../src/common/eloquent';

let sinon = require("sinon");

let NodeTypeValues = {
    'ELEMENT_NODE': 1,
    'TEXT_NODE': 3,
    'PROCESSING_INSTRUCTION_NODE': 7,
    'COMMENT_NODE': 8,
    'DOCUMENT_NODE': 9,
    'DOCUMENT_TYPE_NODE': 10,
    'DOCUMENT_FRAGMENT_NODE': 11
};

let mockNode = {
    nodeType: NodeTypeValues.ELEMENT_NODE,
    ownerDocument: null
};

let mockDOMClientRectangle = {
	bottom: 0,
	height: 0,
	left: 0,
	right: 0,
	top: 0,
	width: 0
};

let CSS2Properties = {
	display: ''
};

let mockHTMLElement = extend(mockNode, {
	innerHTML: '',
	id: '',
	'type': '',
	className: '',
	style: CSS2Properties,
	value: '',
	width: 0,
	height: 0,
	appendChild(child) { return child; },
	addEventListener: sinon.stub(),
	querySelector: sinon.stub(),
	getBoundingClientRect: sinon.stub().returns(mockDOMClientRectangle)
});

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
	getComputedStyle: sinon.stub(),
	innerWidth: 640,
	innerHeight: 480
};

// CanvasRenderingContext2d
let mockCtx2d = {
	clearRect: sinon.stub(),
	translate: sinon.stub(),
    save: sinon.stub(),
    restore: sinon.stub()
};

let mockCanvas = Object.create(mockHTMLElement);
mockCanvas.getContext = sinon.stub().returns(mockCtx2d);

export { mockDocument, mockHTMLElement, mockDocumentBody, mockWindow, mockCanvas, mockCtx2d };
