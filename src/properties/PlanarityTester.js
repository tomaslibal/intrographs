/*
 * Planar graph is a graph that can be drawn geometrically on the plane without edge crossings.
 *
 * Chiba and Nishizeke (2008, p. 11) give a corollary which says that a planar graph has no vertex of degree > 5. One
 * could use the Kuratowski's Theorem from which we know that a graph is planar if it does not contain subgraphs of K5
 * or UG (utility graph, also known as K3,3). Although, this algorithm's running time will grow exponentially to the
 * number of edges, because it needs to check every subgraph of whose there are 2^m (m=num. of edges) in every graph
 * (Chiba and Nishizeke, 2008, p. 23).
 *
 */
import { assert } from '../common/assert';
 
export default class PlanarityTester {

    /*
     * return bool
     */
    static test(graph) {
    
    }

}
