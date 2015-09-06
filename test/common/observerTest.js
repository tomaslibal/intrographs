// import s.u.t.
import Observer from "../../src/common/Observer";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('Observer', () => {

	let observer = null;

	let mockSource = {
		addEventListener: sinon.stub()
	};

	beforeEach('setup', () => {
		observer = new Observer(mockSource, 'foo42');
	});

	describe('constructor', () => {
		it('throws an exception if the source object does not implement method "addEventListener"', () => {
			let throws = null;
			let fn = () => {
				throws = new Observer({});
			}
			chai.expect(fn).to.throw(Error);
		});
		it('stores the source object reference so that the client code can later subscribe to events', () => {
			chai.assert.deepEqual(observer.source, mockSource);
		});
		it('sets property name to the value from the second argument', () => {
			chai.assert.equal(observer.name, 'foo42');
		});
	});

});