let not_implemented = (fncName) => {
    throw new Error(`${fncName}: Not yet implemented!`);
}

export default class SetUtil {
    /*
     * Definition 1: a set is a collection of distinc objects none of which is
     * the set itself.
     *
     */
    static isSet(set) {
        not_implemented("setUtil.isSet");
    }

    /*
     * Definition 2: A set containing no elements is called a null set.
     *
     * Theorem 1: There is only one empty set
     */
    static isNullSet(set) {
        not_implemented("setUtil.isNullSet");
    }

    /*
     * Definition 3: set A is said to be a subset of B if every element of A
     * is also an element of B.
     */
    static isSubset(a, b) {
        not_implemented("setUtil.isSubset");
    }

    /*
     * Definition 4: sets A and B are said to be equal when A is a subset of B
     * and B is a subset of A.
     */
    static equals(a, b) {
        not_implemented("setUtil.equals");
    }

}
