let not_implemented = (fncName) => {
    throw new Error(`${fncName}: Not yet implemented!`);
}

/*
 * SetUtil uses Array<any> representation for a set.
 */
export default class SetUtil {
    /*
     * Definition 1: a set is a collection of distinct objects none of which is
     * the set itself.
     *
     */
    static isSet(s=[]) {
        let selfRef = false;
        let seen = [];
        let result = true;

        if (s.length === 0) {
            return false;
        }

        s.forEach((obj, idx, arr) => {
            if (obj === s) {
                result = false;
                return;
            }
            if (seen.indexOf(obj) > -1) {
                result = false;
                return;
            }
            seen.push(obj);
        });

        return result;
    }

    /*
     * Definition 2: A set containing no elements is called a null set.
     *
     * Theorem 1: There is only one empty set
     */
    static isNullSet(s=[]) {
        if (s.length === 0) {
            return true;
        }
        return false;
    }

    /*
     * Definition 3: set A is said to be a subset of B if every element of A
     * is also an element of B.
     */
    static isSubset(a=[], b=[]) {
        if (a.length > b.length) {
            return false;
        }

        let result = true;

        a.forEach(val => {
           if (b.indexOf(val) === -1) {
               result = false;
           }
        });

        return result;
    }

    /*
     * Definition 4: sets A and B are said to be equal when A is a subset of B
     * and B is a subset of A.
     */
    static equals(a, b) {
        not_implemented("setUtil.equals");
    }

}
