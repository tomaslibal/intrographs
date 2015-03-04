/*
 * Euler walk is a sequence of moves from vertices A_n, A_n+1, A_n+2, ... A_k
 * where each edge is visited exactly once.
 *
 */
function EulerWalkUtil()
{
    "use strict";

    this.CONSTS = {
        NOT_FOUND: -1
    };

    var CONSTS = this.CONSTS;

    function hasEulerWalk(graph) {
        return CONSTS.NOT_FOUND;
    }

    return {
        hasEulerWalk: hasEulerWalk
    };
}

var eulerWalk = EulerWalkUtil();

