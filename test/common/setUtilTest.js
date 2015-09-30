// import s.u.t.
import SetUtil from '../../src/common/SetUtil';

let chai = require('chai');
let sinon = require('sinon');

describe('SetUtil', () => {

	describe('isSet', () => {
		it('returns true if the array contains 1 object which is not the set itself', () => {
			const inv = [1];
			const result = SetUtil.isSet(inv);

			chai.assert.equal(result, true);
		});

		it('returns true if the array contains multiple object neither of which is not the set itself', () => {
			const inv = [1, 2, 3, 4, 5];
			const result = SetUtil.isSet(inv);

			chai.assert.equal(result, true);
		});

		it('returns false if the array contains an object which is the set itself', () => {
			let inv = [];
			inv.push(inv);
			const result = SetUtil.isSet(inv);

			chai.assert.equal(result, false);
		});

		it('returns false if the array is empty', () => {
			let inv = [];
			const result = SetUtil.isSet(inv);

			chai.assert.equal(result, false);
		});

		it('returns false if there are any duplicates', () => {
			let inv = [1, 2, 3, 3];
			const result = SetUtil.isSet(inv);

			chai.assert.equal(result, false);
		});
	});

	describe('isNullSet', () => {
		it('returns true if the array is empty', () => {
			let inv = [];
			const result = SetUtil.isNullSet(inv);

			chai.assert.equal(result, true);
		});

		it('returns false if the array is non empty', () => {
			let inv = [Infinity, -Infinity];
			const result = SetUtil.isNullSet(inv);

			chai.assert.equal(result, false);
		});
	});

	describe('isSubset', () => {
		it('returns true if a is subset of b', () => {
			const a = [99, 100, 101];
			const b = [98, 99, 100, 101, 102, 103];

			chai.assert.equal(SetUtil.isSubset(a, b), true);
		});

		it('returns false if a is not a subset of b', () => {
			const a = [70, 100, 101];
			const b = [200, 100, 101, 102, 103];

			chai.assert.equal(SetUtil.isSubset(a, b), false);
		});
	});

});
