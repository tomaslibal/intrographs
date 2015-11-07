// import s.u.t
import GraphConsoleTokenizer from '../../src/interpreter/GraphConsoleTokenizer';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('GraphConsoleTokenizer', () => {

	let tokenizer;

	beforeEach(() => {
		tokenizer = new GraphConsoleTokenizer();
	});

	describe('delimiters', () => {
		it('contains ".", "(", ")", "," as delimiters', () => {
			['.','(',')',','].forEach(del => {
				chai.assert.isAbove(tokenizer.delimiters.indexOf(del), -1);
			})
		});
	});

	describe('creating tokens', () => {
		it('sentence graph.add.vertex("A") produces 4 tokens', () => {
			const s = 'graph.add.vertex("A")';
			const r = tokenizer.tok(s);

			chai.assert.equal(r.length, 4);
		});

		it('a list of arguments g.a.v(A,B,C) is parsed into individual tokens A,B, and C', () => {
			const s = 'graph.add.vertex("A","B","C")';
			const r = tokenizer.tok(s);

			chai.assert.equal(r.length, 6);
		});
	});

	describe('invalid input', () => {
		it('will return an empty array of tokens', () => {
			const s = 'garbage';
			const r = tokenizer.tok(s);

			chai.assert.deepEqual(r, []);
		});


	});
});
