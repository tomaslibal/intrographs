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
	});
	
});
