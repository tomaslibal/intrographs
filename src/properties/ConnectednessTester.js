import { dfs } from '../search/depthFirstSearch';
import { spanTree } from '../search/spanningTree';

export default class ConnectednessTester {

    /*
     * Tests if the graph is in one piece.
     *
     * return bool
     */
    static isConnected(graph) {

        let tree = spanTree(graph);

        if (tree.length === graph.vertices.length) {
            return true;
        } else {
            return false;
        }
    }
    
    /*
     * Returns array containing subsets of vertices that are connected together. If the graph is
     * in one piece, it returns an array of just one set which is the vertex set.
     *
     * return Array<Array<String>>
     */
    static getPieces() {
        
    }

}
