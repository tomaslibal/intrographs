export default class Observer {

    // observer is created from an object which acts as a source of events
    constructor(source={}, name='untitled observer') {
        if (!source.addEventListener) {
            throw new Error("The source object does not seem to implement the Observable interface");
        }

        this.source = source;
        this.name = name;
    }

    // subscribe, dispose to certain events on the underlying object
    subscribe() {}
    //notify() {} // notify happens on the underlying object
    dispose() {}

}
