// import s.u.t.
import HTMLList from '../../../src/presentation/html/HTMLList';

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLList', () => {

	let htmlList = null;

	let mockDocument = {

	};

	beforeEach(() => {
		htmlList = new HTMLList(mockDocument);
	});

	describe('constructor', () => {
		it('takes a Document object', () => {
			let throws = () => {
				const list = new HTMLList();
			};

			let wontThrow = () => {
				const list = new HTMLList(mockDocument);
			};

			chai.expect(throws).to.throw('Constructor must be supplied with the Document object');
			chai.expect(wontThrow).not.to.throw();
		});
		it('assigns property .document to the supplied document object', () => {
			chai.assert.property(htmlList, 'document');
			chai.assert.deepEqual(htmlList.document, mockDocument);
		});
		it('assigns property .list to an empty array', () => {
			chai.assert.property(htmlList, 'list');
			chai.assert.deepEqual(htmlList.list, []);
		});
	});

});