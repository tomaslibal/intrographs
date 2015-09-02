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
         * row-col: m is the number of rows and n is the number of columns
         *
         */
	static init(m=1, n=1) {
		assert(m>0);
		assert(n>0);

		return this.defarray(m).map(val => {
			return this.defarray(n).map(() => 0);
		});
	}

}
