// import s.u.t.
import Observer from "../../src/common/Observer";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('Observer', () => {

	let observer = null;

	let mockSource = {

	}

	beforeEach('setup', () => {
		observer = new Observer(mockSource, 'foo42');
	});

	describe('constructor', () => {
		it('sets property name to the value from the second argument', () => {
			chai.assert.equal(observer.name, 'foo42');
		});
	});

});