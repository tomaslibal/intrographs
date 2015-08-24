/*
 * A Hamilton walk is a sequence of vertices A_n, A_n+1, ..., A_k in which every
 * vertex of the graph is visited exactly once.
 *
 */
function HamiltonWalkUtil()
{
    "use strict";

    var CONSTS = {
        NOT_FOUND: -1
    };

    function hasHamiltonWalk(graph)
    {
        return CONSTS.NOT_FOUND;
    }

    return {
        hasHamiltonWalk: hasHamiltonWalk
    };
}

var hamiltonWalk = HamiltonWalkUtil();
