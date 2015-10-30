/**
 * Proxy class MouseEvents delegates adding event listeners to selected
 * mouse events on a ${target} element.
 */
export default class MouseEvents {

	static onClick(target, callback) {
		target.addEventListener('click', callback);
	}

	static onMouseDown(target, callback) {
		target.addEventListener('mousedown', callback);
	}

	static onMouseUp(target, callback) {
		target.addEventListener('mouseup', callback);
	}

	static onMouseMove(target, callback) {
		target.addEventListener('mousemove', callback);
	}


}