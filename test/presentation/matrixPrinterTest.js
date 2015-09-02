import MatrixPrinter from "../../src/presentation/MatrixPrinter";

// mock matrix
let mockMatrix = {
	fromGraph(v, e) {
		return [[0, 1, 1], [1, 0, 0], [1, 0, 0]];
	}
}

 let assert = require("assert");
 let chai = require("chai");

 describe('MatrixPrinter', () => {
	 it('.ascii prints out the matrix as ascii diagram', () => {
		 const mat = mockMatrix.fromGraph([], []);

		 let out = MatrixPrinter.ascii(mat);

		 chai.assert.equal(out, '[ 0  1  1 ]\n[ 1  0  0 ]\n[ 1  0  0 ]\n');
	 });
 });