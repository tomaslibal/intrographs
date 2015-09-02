/*
 * Import the Software Under Test
 */
 import MathUtil from "../src/common/MathUtil";
 
 let assert = require("assert");
 let chai = require("chai");
 
 describe('MathUtil', () => {
	"use strict";
	
	 it('returns a random number from the given range', () => {
		 const result = MathUtil.getRandomArbitrary(0,1);
		 assert.equal(true, result >= 0);
		 assert.equal(true, result <= 1);
	 });
	 
	 describe('quadRoots', () => {
                 it('returns null when discriminant < 0', () => {
		 	const result = MathUtil.quadRoots(1, 5, 100);
			chai.assert.equal(null, result);
		 });
		 it('returns x=[2] for x^2-4x+4', () => {
			 const result = MathUtil.quadRoots(1, -4, 4);
			 chai.assert.deepEqual(result, [2]);
		 });

		 it('returns x=[1, -2] for x^2+x-2', () => {
			 const result = MathUtil.quadRoots(1, 1, -2);
			 chai.assert.deepEqual(result, [1, -2]); 
		 });
	 });
 });
