// import s.u.t
import PolymerBridge from '../../../src/presentation/html/PolymerBridge';

import { mockHTMLElement, mockDocument } from '../../mocks/htmlMocks';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('PolymerBridge', () => {

	let bridge = null;
	
	beforeEach(() => {
		bridge = new PolymerBridge(mockDocument);
	});
	
	afterEach(() => {
		mockDocument.addEventListener.reset();
	})

	describe('constructor', () => {
		it('inits polymerReady to false', () => {
			chai.assert.equal(bridge.polymerReady, false);
		});
	});
	
	describe('createImportElement', () => {
		it('creates a new <link rel="import" href="..."> element', () => {
			mockDocument.createElement.returns(mockHTMLElement);
			
			const el = bridge.createImportElement('foo');
			
			assert(mockDocument.createElement.calledWith('link'));
			chai.assert.equal(el.rel, 'import');
			chai.assert.equal(el.href, 'foo');
		});
	});
	
	describe('waitUntilPolymerReady', () => {
		it('listens to WebComponentsReady event on document', () => {
			bridge.waitUntilPolymerReady();
			
			assert(mockDocument.addEventListener.calledWith('WebComponentsReady', sinon.match.func));
		});
		
		it('executes the supplied callback when WebComponentsReady emitted', () => {
			const mock = sinon.stub();
			bridge.waitUntilPolymerReady(mock);
			
			// getCall(1) is the mock call, getCall(0) is done by the constructor
			const fn = bridge.document.addEventListener.getCall(1).args[1];
			fn.call(bridge);
			
			assert(mock.calledOnce);
		});
		
		it('sets polymerReady=true when WebComponentsReady emitted', () => {
			bridge.waitUntilPolymerReady(sinon.stub());
			
			// getCall(1) is the mock call, getCall(0) is done by the constructor
			const fn = bridge.document.addEventListener.getCall(1).args[1];
			fn.call(bridge);
			
			chai.assert.equal(bridge.polymerReady, true);
		});
	});
	
});
