// import s.u.t.
import Observable from "../../src/common/Observable";

let assert = require("assert");
let chai = require("chai");
let sinon = require("sinon");

describe('Observable', () => {

	let observable = null;

	let mockEvent = {};

	let mockSource = {
		addEventListener(eventType, callback) {
			this.callbacks = this.callbacks || [];
			this.callbacks.push({ eventType: eventType, callback: callback });
		},
		removeEventListener: sinon.stub(),
		notify(signalEventType) {
			this.callbacks.forEach(({ eventType: eventType, callback: callback }) => {
				if (signalEventType === eventType) {
					callback.call(undefined, mockEvent);
				}
			});
		}
	};

	beforeEach('setup', () => {
		observable = new Observable(mockSource, 'foo42');


	});

	describe('constructor', () => {
		it('throws an exception if the source object does not implement method "addEventListener"', () => {
			let throws = null;
			let fn = () => {
				throws = new Observable({});
			}
			chai.expect(fn).to.throw(Error);
		});
		it('stores the source object reference so that the client code can later subscribe to events', () => {
			chai.assert.deepEqual(observable.source, mockSource);
		});
		it('sets property "name" to the value from the second argument', () => {
			chai.assert.equal(observable.name, 'foo42');
		});
		it('sets property "_subscribedEvents" to an empty array', () => {
			chai.assert.deepEqual(observable._subscribedEvents, []);
		});
		it('sets property "_forEachCallbacks" to an empty array', () => {
			chai.assert.deepEqual(observable._forEachCallbacks, []);
		});
	});

	describe('subscribe', () => {
		it('registers a listener on the source object for the given event type', () => {
			sinon.spy(mockSource, 'addEventListener');

			observable.subscribe('customEvent');

			assert(mockSource.addEventListener.calledWith('customEvent', sinon.match.func));
		});
		it('stores the eventType in property _subscribedEvents', () => {
			observable.subscribe('minky');

			chai.assert.property(observable, '_subscribedEvents');
			chai.assert.deepEqual(observable._subscribedEvents, ['minky']);

			observable.subscribe('binky');
			chai.assert.deepEqual(observable._subscribedEvents, ['minky', 'binky']);
		});
	});

	describe('dispose', () => {
		it('unregisters a listener on the source object for the given event type', () => {
			observable.dispose('customEvent2');

			assert(mockSource.removeEventListener.calledWith('customEvent2', sinon.match.func));
		});
	});

	describe('forEach', () => {
		it('throws an Error if no event has been subscribed to and will not add the callback to the _forEachCallbacks array', () => {
			let fn = () => {
				observable.forEach(() => {});
			};

			chai.expect(fn).to.throw(Error);
			chai.assert.deepEqual(observable._forEachCallbacks, []);
		});
		it('stores the callback in _forEachCallbacks array', () => {
			let callback = sinon.stub();

			observable.subscribe('fusionStart');

			observable.forEach(callback);

			chai.assert.deepEqual(observable._forEachCallbacks, [callback]);
		});
		it('throws an Error if callback is not a function and will not add the argument to the _forEachCallbacks array', () => {
			observable.subscribe('fusionStart');

			let fn = () => {
				observable.forEach('bar');
			};

			chai.expect(fn).to.throw(Error);
			chai.assert.deepEqual(observable._forEachCallbacks, []);
		});
		it('invokes all stored callbacks when the source object notifies the observable about a new event', () => {
			let callback = sinon.stub();
			let callback2 = sinon.stub();

			observable.subscribe('fusionStart');
			observable.forEach(callback);
			observable.forEach(callback2);

			mockSource.notify('fusionStart');

			assert(callback.calledOnce);
			assert(callback2.calledOnce);
		});
	})

});