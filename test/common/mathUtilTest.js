/*
 * Import the Software Under Test
 */
 import MathUtil from "../../src/common/MathUtil";

 let assert = require("assert");
 let chai = require("chai");

 describe('MathUtil', () => {
	"use strict";

	 it('returns a random number from the given range', () => {
		 const result = MathUtil.getRandomArbitrary(0,1);
		 assert.equal(result >= 0, true);
		 assert.equal(result <= 1, true);
	 });

	 describe('quadRoots', () => {
                 it('returns null when discriminant < 0', () => {
		 	const result = MathUtil.quadRoots(1, 5, 100);
			chai.assert.equal(result, null);
		 });
		 it('returns one root x=[2] for x^2-4x+4 (discriminant = 0)', () => {
			 const result = MathUtil.quadRoots(1, -4, 4);
			 chai.assert.deepEqual([2], result);
		 });

		 it('returns two roots x=[1, -2] for x^2+x-2 (discriminant > 0)', () => {
			 const result = MathUtil.quadRoots(1, 1, -2);
			 chai.assert.deepEqual([1, -2], result);
		 });
	 });
 });
