// import s.u.t.
import HTMLList from '../../../src/presentation/html/HTMLList';

import { mockHTMLElement as mockNewElement, mockHTMLElement as mockParentElement, mockDocument } from "../../mocks/htmlMocks";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLList', () => {

	let htmlList = null;

	beforeEach(() => {
		htmlList = new HTMLList(mockDocument, mockParentElement);

		sinon.spy(htmlList.list, 'reduce');
	});

	describe('constructor', () => {
		it('takes a document object and a parent element', () => {
			let throws = () => {
				const list = new HTMLList();
			};

			let wontThrow = () => {
				const list = new HTMLList(mockDocument, mockParentElement);
			};

			chai.expect(throws).to.throw('Constructor must be supplied with the document object and a parent element');
			chai.expect(wontThrow).not.to.throw();
		});
		it('assigns property .parent to the supplied parent element', () => {
			chai.assert.property(htmlList, 'parent');
			chai.assert.deepEqual(htmlList.parent, mockParentElement);
		});
		it('assigns property .document to the supplied document object', () => {
			chai.assert.property(htmlList, 'document');
			chai.assert.deepEqual(htmlList.document, mockDocument);
		});
		it('assigns property .list to an empty array', () => {
			chai.assert.property(htmlList, 'list');
			chai.assert.deepEqual(htmlList.list, []);
		});
		it('creates a <span> element that will be appended to the parent', () => {
			assert(htmlList.document.createElement.calledWith('span'));
			assert(htmlList.parent.appendChild.calledWith(mockNewElement));
		});
	});

	describe('render', () => {
		it('flattens the .list array to string "el1, el2, ..., elN" and assigns this strings as innerHTML of the listElement', () => {
			htmlList.list.push('a', 'b', 'c', 'd');
			htmlList.render();

			assert(htmlList.list.reduce.calledWith(sinon.match.func));
			chai.assert.equal(htmlList.listElement.innerHTML, 'a, b, c, d');
		});
	});

});