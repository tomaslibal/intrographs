/*
 * Enables an interactive layer over a graph that is drawn into a canvas.
 *
 * E.g.: on click, the nearest vertex of the graph is 'grabbed' and for mousemove
 * action the vertex is being dragged on the canvas to a new cursor position.
 *
 * Additionally, this layer should add controls to add new vertices and edges
 * and add means to delete the existing ones.
 */
function InteractiveLayer(graph, canvas)
{
    "use strict";

}

function Observer() {
    "use strict";

    this.listeners = [];
}

/*
 * Suppose we have a canvas `var cnvs` and we want to perform some action 
 * every time a certain event `click` happens. The Observer helps to do this
 * as follows:
 *
 * Observer.observe(cnvs, 'click', callback);
 * Observer.dispatch('click'); 
 *
 * The dispatch method invokes all registered callbacks that listen to the click
 * event.
 */
Observer.prototype.observe = function(obj, eventType, callback) {
    "use strict";

    this.listeners.push({
        eventType: eventType,
        callback: callback
    });
};

Observer.prototype.dispatch = function(eventType) {
    "use strict";

    for(var i = 0; i < this.listeners.length; i++) {
        if (this.listeners[i].eventType === eventType) {
            this.listeners[i].callback.call(undefined);
        }
    }
};

if ("undefined" !== typeof process && process.env) {
    module.exports = InteractiveLayer;
}
