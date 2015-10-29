export default class EventBus {

    constructor(name='untitled_event_bus') {
        this.name = name;
        this.eventCallbacks = {};
    }

    dispatch(event={}) {
        if ("undefined" === typeof event.type) {
            throw new Error("event must specify a type");
        } 
        
        const listeners = this.eventCallbacks[event.type] || [];

        listeners.forEach(fn => {
            fn.call(undefined, event);
        });
    }

    on(eventType, callback) {
        let q = this.eventCallbacks[eventType] || (this.eventCallbacks[eventType] = []);
    
        q.push(callback);
    }
}
