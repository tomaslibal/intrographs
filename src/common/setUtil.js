var setUtil = {
    /*
     * Definition 1: a set is a collection of distinc objects none of which is
     * the set itself.
     *
     */
    isSet: function(set) {
        "use strict";

        not_implemented("setUtil.isSet");
    },
    /*
     * Definition 2: A set containing no elements is called a null set.
     *
     * Theorem 1: There is only one empty set
     */
    isNullSet: function(set) {
        "use strict";
        not_implemented("setUtil.isNullSet");
    },
    /*
     * Definition 3: set A is said to be a subset of B if every element of A
     * is also an element of B.
     */
    isSubset: function(a, b) {
        "use strict";
        not_implemented("setUtil.isSubset");
    },
    /*
     * Definition 4: sets A and B are said to be equal when A is a subset of B
     * and B is a subset of A.
     */
    equals: function(a, b) {
        "use strict";
        not_implemented("setUtil.equals");
    }

};

function not_implemented() {
    "use strict";
        
    var str = "Not yet implemented!";
          
    if (fcn) {
        str = fcn + ": " + str;
    }
             
    if (msg) {
        str += " " + msg;
    }
                  
    throw new Error(str);
}
