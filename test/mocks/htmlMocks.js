let sinon = require("sinon");

function extend(parent, child) {
    let obj = Object.create(parent);

    for(let key of Object.keys(child)) {
    	obj[key] = child[key];
    }

    return obj;
}

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

let mockHTMLElement = extend(mockNode, {
	innerHTML: '',
	id: '',
	'type': '',
	className: '',
	style: {},
	value: '',
	width: 0,
	height: 0,
	appendChild(child) { return child; },
	addEventListener: sinon.stub(),
	querySelector: sinon.stub()
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
	translate: sinon.stub()
};

let mockCanvas = Object.create(mockHTMLElement);
mockCanvas.getContext = sinon.stub().returns(mockCtx2d);
mockCanvas.getBoundingClientRect = sinon.stub();

export { mockDocument, mockHTMLElement, mockDocumentBody, mockWindow, mockCanvas, mockCtx2d };
