// import s.u.t.
import Observer from "../../src/common/Observer";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('Observer', () => {

	let observer = null;

	let mockSource = {
		addEventListener: sinon.stub(),
		removeEventListener: sinon.stub()
	};

	beforeEach('setup', () => {
		observer = new Observer(mockSource, 'foo42');
	});

	describe('constructor', () => {
		it('throws an exception if the source object does not implement method "addEventListener"', () => {
			let throws = null;
			let fn = () => {
				throws = new Observer({});
			}
			chai.expect(fn).to.throw(Error);
		});
		it('stores the source object reference so that the client code can later subscribe to events', () => {
			chai.assert.deepEqual(observer.source, mockSource);
		});
		it('sets property "name" to the value from the second argument', () => {
			chai.assert.equal(observer.name, 'foo42');
		});
		it('sets property "_subscribedEvents" to an empty array', () => {
			chai.assert.deepEqual(observer._subscribedEvents, []);
		});
		it('sets property "_forEachCallbacks" to an empty array', () => {
			chai.assert.deepEqual(observer._forEachCallbacks, []);
		});
	});

	describe('subscribe', () => {
		it('registers a listener on the source object for the given event type', () => {
			observer.subscribe('customEvent');

			assert(mockSource.addEventListener.calledWith('customEvent', sinon.match.func));
		});
		it('stores the eventType in property _subscribedEvents', () => {
			observer.subscribe('minky');

			chai.assert.property(observer, '_subscribedEvents');
			chai.assert.deepEqual(observer._subscribedEvents, ['minky']);

			observer.subscribe('binky');
			chai.assert.deepEqual(observer._subscribedEvents, ['minky', 'binky']);
		});
	});

	describe('dispose', () => {
		it('unregisters a listener on the source object for the given event type', () => {
			observer.dispose('customEvent2');

			assert(mockSource.removeEventListener.calledWith('customEvent2', sinon.match.func));
		});
	});

	describe('forEach', () => {
		it('throws an Error if no event has been subscribed to and will not add the callback to the _forEachCallbacks array', () => {
			let fn = () => {
				observer.forEach(() => {});
			};

			chai.expect(fn).to.throw(Error);
			chai.assert.deepEqual(observer._forEachCallbacks, []);
		});
		it('stores the callback in _forEachCallbacks array', () => {
			let callback = sinon.stub();

			observer.subscribe('fusionStart');

			observer.forEach(callback);

			chai.assert.deepEqual(observer._forEachCallbacks, [callback]);
		});
		it('throws an Error if callback is not a function and will not add the argument to the _forEachCallbacks array', () => {
			observer.subscribe('fusionStart');

			let fn = () => {
				observer.forEach('bar');
			};

			chai.expect(fn).to.throw(Error);
			chai.assert.deepEqual(observer._forEachCallbacks, []);
		});
	})

});