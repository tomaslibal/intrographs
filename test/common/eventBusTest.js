// import s.u.t
import EventBus from '../../src/common/EventBus';

let chai = require('chai');
let sinon = require('sinon');
let assert = require('assert');

describe('EventBus', () => {

    let q = null;

    beforeEach(() => {
        q = new EventBus();
    });

    describe('constructor', () => {
        it('assigns the supplied name as the name of the event bus', () => {
            const temp = new EventBus('foo');

            chai.assert.equal(temp.name, 'foo');
        });

        it('inits an empty object for the event callbacks', () => {
            chai.assert.deepEqual(q.eventCallbacks, {});
        });
    });

    describe('dispatch', () => {
        it('distributes the event to all listeners registered for the given event.type', () => {
            const listener = sinon.stub();
            const listener2 = sinon.stub();
            const listener3 = sinon.stub();

            q.on('event1', listener);
            q.on('event1', listener2);
            q.on('event2', listener3);
            const event = { 'type': 'event1' }
            q.dispatch(event);

            assert(listener.calledWith(event));
            assert(listener2.calledWith(event));
            assert(listener3.notCalled);
        });

        it('throws an error if event does not specify a type', () => {
            const throws = () => {
                q.dispatch({});
            };

            chai.expect(throws).to.throw("event must specify a type");
        });

    });

    describe('on (add listeners)', () => {
        it('adds a listener for the specified event type', () => {
            const listener = sinon.stub();
            q.on('testEvent', listener);

            chai.assert.deepEqual(q.eventCallbacks, { 'testEvent': [listener] });
        });
    });

});
