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

	describe('graph.add production', () => {
		it('.vertex("A") invokes the method to add new vertex', () => {
			sinon.spy(interpreter, 'addNewVertex');

			const s = 'graph.add.vertex("A")';

			interpreter.onInputHandler({ 'payload': s });

			assert(interpreter.addNewVertex.calledOnce);
		});

		it('.vertex("A") invoking addNewVertex method will dispatch that one vertex A has been added', () => {
			const stmt = [
				[
					{ isObject: true, value: 'graph' },
					{ isAction: true, value: 'add' },
					{ isValue: true, value: 'vertex' },
					{ isValue: true, value: 'A' }
				]
			];

			interpreter.addNewVertex(stmt[0]);

			assert(mockEventBus.dispatch.calledWith(
				{
					'type': 'interpreter.add.vertex',
					'id': stmt[0][3].value,
					'label': 'n/a'
				}
			));
		});

		it('.vertex("A","B","C") dispatches 3 new vertex addition events', () => {
			const stmt = [
				[
					{ isObject: true, value: 'graph' },
					{ isAction: true, value: 'add' },
					{ isValue: true, value: 'vertex' },
					{ isValue: true, value: 'A' },
					{ isValue: true, value: 'B' },
					{ isValue: true, value: 'C' }
				]
			];

			interpreter.addNewVertex(stmt[0]);

			assert(mockEventBus.dispatch.calledWith(
				{
					'type': 'interpreter.add.vertex',
					'id': stmt[0][3].value,
					'label': 'n/a'
				}
			));
		});

		it('.edge("A", "B") invokes the method to add new edge', () => {
			sinon.spy(interpreter, 'addNewEdge');

			const s = 'graph.add.edge("A", "B")';

			interpreter.onInputHandler({ 'payload': s });

			assert(interpreter.addNewEdge.calledOnce);
		});

		it('.edge("A","B") dispatches a new edge from A to B addition event', () => {
				const stmt = [
				[
					{ isObject: true, value: 'graph' },
					{ isAction: true, value: 'add' },
					{ isValue: true, value: 'edge' },
					{ isValue: true, value: 'A' },
					{ isValue: true, value: 'B' }
				]
			];

			interpreter.addNewEdge(stmt[0]);

			assert(mockEventBus.dispatch.calledWith(
				{
					'type': 'interpreter.add.edge',
					'vertex1': stmt[0][3].value,
					'vertex2': stmt[0][4].value
				}
			));
		});
	});
});
