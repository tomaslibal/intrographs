/*
 *
 * Abstract math operations that are used throughout Graph class reside here.
 *
 * In the future it should be possible to pass a math library to the Graph class
 * so that the class would use funtions from that injected library.
 */
export class MathUtil {
    /*
     * Function returns an integer number from within the given range [min,
     * max].
     *
     * The random generator is the JavaScript random generator. This one does
     * not seem to include seed function.
     *
     */
    static getRandomArbitrary(min=0, max=65536) {
        return Math.ceil((Math.random() * (max - min))) + min;
    }

    /*
     * Gives real valued roots for a function
     *     y = f(x) = ax^2 + bx + c
     *
     */
    static quadRoots(a=0, b=0, c=0) {
        const discr = b*b - 4*a*c;

        if (discr < 0) {
            return null;
        }

        if (discr == 0) {
            return [(-b)/2*a];
        }

        const sqrt = Math.sqrt(discr);

        return [(-b + sqrt)/2*a, (-b - sqrt)/2*a];
    }
}
