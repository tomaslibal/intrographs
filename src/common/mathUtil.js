/*
 *
 * Abstract math operations that are used throughout Graph class reside here.
 *
 * In the future it should be possible to pass a math library to the Graph class
 * so that the class would use funtions from that injected library.
 */
var mathUtil = {
    /*
     * Function returns an integer number from within the given range [min, 
     * max]. 
     *
     * The random generator is the JavaScript random generator. This one does
     * not seem to include seed function.
     *
     */
    getRandomArbitrary: function(min, max) {
        "use strict";

        return Math.ceil((Math.random() * (max - min))) + min;
    }
};

if ("undefined" !== typeof process && process.env) {
    module.exports = math;
}
