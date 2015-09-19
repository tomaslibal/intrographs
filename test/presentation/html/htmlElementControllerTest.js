// import s.u.t.
import HTMLElementController from '../../../src/presentation/html/HTMLElementController';

import { mockDocument } from '../../mocks/htmlMocks';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('HTMLElementController', () => {

	let elementCtrl;

	beforeEach(() => {
		elementCtrl = new HTMLElementController(mockDocument);
	});

	it('stores the reference to the document', () => {
		chai.assert.deepEqual(elementCtrl.document, mockDocument);
	});
});