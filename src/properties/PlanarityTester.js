/*
 * Planar graph is a graph that can be drawn geometrically on the plane without edge crossings.
 *
 * Chiba and Nishizeke (2008, p. 11) give a corollary which says that a planar graph has no vertex of degree > 5. One
 * could use the Kuratowski's Theorem from which we know that a graph is planar if it does not contain subgraphs of K5
 * or UG (utility graph, also known as K3,3). Although, this algorithm's running time will grow exponentially to the
 * number of edges, because it needs to check every subgraph of whose there are 2^m (m=num. of edges) in every graph
 * (Chiba and Nishizeke, 2008, p. 23).
 *
 * This PlanarityTester will employ a vertex addition algorithm described in Chiba and Nishizeke (2008) which uses 
 * a st-numbering algorithm and a data structure called PQ-tree (Chiba and Nishizeke, 2008, p. 33). St-numbering or
 * source-sink numbering assigns numbers to vertices such that number 1 (source) and N (sink) (N being the total number of vertices)
 * are next to each other and also every node has two adjacent nodes such that one adj. node has lower number and 
 * the other one has a higher number (Moehring, H. R., 1997, p. 357).
 */
 
export default class PlanarityTester {

    static test(graph) {
    
    }

}
