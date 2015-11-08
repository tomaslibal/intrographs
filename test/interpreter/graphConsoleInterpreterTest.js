// import s.u.t
import GraphConsoleInterpreter from '../../src/interpreter/GraphConsoleInterpreter';

import mockEventBus from '../mocks/eventBusMock';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('GraphConsoleInterpreter', () => {

	let interpreter;

	beforeEach(() => {
		interpreter = new GraphConsoleInterpreter(mockEventBus);
	});

	describe('constructor', () => {
		it('registers listener on the eventBus for interpreter.input', () => {
			assert(mockEventBus.on.calledWith('interpreter.input', interpreter._boundOnInputHandler));
		});
	});

	describe('on interpreter.input event', () => {
		it('it tokenizes and parses the input', () => {
			const payload = 'graph.add.vertex("A")';
			const mockTokens = [{ test: true }];
			interpreter.tokenizer.tok = sinon.stub().withArgs(payload).returns(mockTokens);
			sinon.spy(interpreter.parser, 'parse');

			interpreter.onInputHandler({
				'payload': payload
			});

			assert(interpreter.tokenizer.tok.calledWith(payload));
			assert(interpreter.parser.parse.calledWith(mockTokens));
		});

	});

});
