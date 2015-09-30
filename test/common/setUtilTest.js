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


});
