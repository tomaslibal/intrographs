import IRenderable from "./IRenderable";

class ObservableRenderable extends IRenderable {

	constructor({ 'posX': x, 'posY': y }) {
		super({ 'posX': x, 'posY': y });

		this.callbacks = [];
	}

	addEventListener(eventType, callback) {
		this.callbacks.push({ eventType: eventType, callback: callback });
	}

	removeEventListener() {

	}

	notify(signalEventType, eventObj={}) {
		this.callbacks.forEach(({ eventType: eventType, callback: callback }) => {
			if (signalEventType === eventType) {
				callback.call(undefined, eventObj);
			}
		});
	}

}

export { ObservableRenderable };