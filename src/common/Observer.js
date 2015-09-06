export default class Observer {

    // observer is created from an object which acts as a source of events
    constructor(source={}, name='untitled observer') {
        if (!source.addEventListener) {
            throw new Error('The source object does not seem to implement the Observable interface');
        }

        this.source = source;
        this.name = name;

        this._subscribedEvents = [];
        this._forEachCallbacks = [];

        this._boundHandleNewEvent = this._handleNewEvent.bind(this);
    }

    _handleNewEvent(event) {
        this._forEachCallbacks.forEach(callback => {
            callback.call(undefined, event);
        })
    }

    // subscribe, dispose to certain events on the underlying object
    subscribe(eventType) {
        this.source.addEventListener(eventType, this._boundHandleNewEvent);
        this._subscribedEvents.push(eventType);
    }
    //notify() {} // notify happens on the underlying object
    dispose(eventType) {
        this.source.removeEventListener(eventType, this._boundHandleNewEvent);
    }

    forEach(callback) {
        if (this._subscribedEvents.length === 0) {
            throw new Error('Cannot process forEach because no event has been subscribed');
            return;
        }

        if ('function' !== typeof callback) {
            throw new Error('Callback is not a function');
            return;
        }

        this._forEachCallbacks.push(callback);
    }

}
