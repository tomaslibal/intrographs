/*
 * Import the Software Under Test
 */
 import MathUtil from "../src/common/MathUtil";
 
 let assert = require("assert");
 
 describe('MathUtil', () => {
	"use strict";
	
	 it('returns a random number from the given range', () => {
		 const result = MathUtil.getRandomArbitrary(0,1);
		 assert.equal(true, result >= 0);
		 assert.equal(true, result <= 1);
	 });
 });