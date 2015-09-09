// import s.u.t.
import HTMLList from '../../../src/presentation/html/HTMLList';

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('HTMLList', () => {

	let htmlList = null;

	let mockParentElement = {
		innerHTML: ''
	};

	beforeEach(() => {
		htmlList = new HTMLList(mockParentElement);

		sinon.spy(htmlList.list, 'reduce');
	});

	describe('constructor', () => {
		it('takes a parent element', () => {
			let throws = () => {
				const list = new HTMLList();
			};

			let wontThrow = () => {
				const list = new HTMLList(mockParentElement);
			};

			chai.expect(throws).to.throw('Constructor must be supplied with a parent element');
			chai.expect(wontThrow).not.to.throw();
		});
		it('assigns property .parent to the supplied parent element', () => {
			chai.assert.property(htmlList, 'parent');
			chai.assert.deepEqual(htmlList.parent, mockParentElement);
		});
		it('assigns property .list to an empty array', () => {
			chai.assert.property(htmlList, 'list');
			chai.assert.deepEqual(htmlList.list, []);
		});
	});

	describe('render', () => {
		it('flattens the .list array to string "el1, el2, ..., elN" and assigns this strings as innerHTML of the parent element', () => {
			htmlList.list.push('a', 'b', 'c', 'd');
			htmlList.render();

			assert(htmlList.list.reduce.calledWith(sinon.match.func));
			chai.assert.equal(htmlList.parent.innerHTML, 'a, b, c, d');
		});
	});

});