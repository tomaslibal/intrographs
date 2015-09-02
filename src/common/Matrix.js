import assert from "../common/assert";

export default class Matrix {

	static defarray(len=0) {
		assert(len>=0);

		let ret = [];		

		for(let i = 0; i < len; i++) {
			ret.push(0);
		}

		return ret;
	}

	/*
         * Row-col format: m is the number of rows and n is the number of 
         * columns.
         *
         */
	static init(m=1, n=1) {
		assert(m>0);
		assert(n>0);

		return this.defarray(m).map(val => {
			return this.defarray(n).map(() => 0);
		});
	}


	/*
 	 * Takes a string array of named vertices and an array of string arrays
 	 * of edges between given vertices.
 	 *
 	 * Ex: G1 = (['A', 'B', 'C'],
 	 * 	     [['A','B'],['A','C'])
 	 *
 	 * Graph G1 has 3 vertices and two edges between A-B and A-C. 
 	 * The function returns an adjacency matrix where vertices' positions
 	 * from the V array map to the row/column position in the adjacency
 	 * matrix, so G1 would become a 3x3 matrix:
 	 *
 	 *        A   B   C
 	 *   A  [ 0   1   1 ]
 	 *   B  [ 1   0   0 ]
 	 *   C  [ 1   0   0 ]
 	 */ 
	static fromGraph(vertices=[], edges=[]) {
		if (vertices.length == 0) {
			return [];
		}

		let mat = this.init(vertices.length, vertices.length);

		edges.forEach((edge) => {
                        var [e1, e2] = edge;
                        var posE1 = vertices.indexOf(e1);
			var posE2 = vertices.indexOf(e2);
			
			mat[posE1][posE2] = 1;
			mat[posE2][posE1] = 1;
		});

		return mat;
	}
}
