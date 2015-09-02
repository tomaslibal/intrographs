/*
 *  * Import the Software Under Test
 *   */
import Matrix from "../src/common/Matrix";
 
let assert = require("assert");
let chai = require("chai");
 
describe('Matrix', () => {
	it('.init returns an m*n array of zero\'s', () => {
		let arr = Matrix.init(1, 1);
		chai.assert.deepEqual(arr, [[0]]);
	});

	it('.init returns an arbitrary 4*3 matrix of zero\'s', () => {
		let arr = Matrix.init(4, 3);
		chai.assert.equal(arr.length, 4);
		chai.assert.equal(arr[0].length, 3);
	});
});
