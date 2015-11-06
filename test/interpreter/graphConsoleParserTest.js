// import s.u.t
import GraphConsoleParser from '../../src/interpreter/GraphConsoleParser';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('GraphConsoleParser', () => {

	let parser;

	beforeEach(() => {
		parser = new GraphConsoleParser();

		parser.tokenizer = {
			tok: sinon.stub()
		};
	});

	describe('sentence input is first tokenized, then parsed', () => {
		it('fromSentence invokes the tokenizer', () => {
			parser.fromSentence('a.b.c');

			assert(parser.tokenizer.tok.calledWith('a.b.c'));
		});

		it('after the sentence is tokenized, the result is passed on to the parser', () => {
			const mockTokens = [{}];
			sinon.spy(parser, 'parse');

			parser.tokenizer.tok.returns(mockTokens);

			parser.fromSentence('a.b.c');

			assert(parser.parse.calledWith(mockTokens));
		});
	});

	describe('object keyword is followed by an action', () => {
		it('graph.add is valid', () => {
			parser.tokenizer.tok.returns([
				{ isObject: true },
				{ isObject: false, isAction: true }
			]);


			const wontThrow = () => {
				parser.fromSentence(s);
			};

			chai.expect(wontThrow).to.not.throw;
		});

		it('and if not, it throws an exception', () => {
			parser.tokenizer.tok.returns([
				{ isObject: true },
				{ isObject: true }
			]);

			const throws = () => {
				parser.fromSentence();
			};

			chai.expect(throws).to.throw();
		});
	});
});
