import { dfs } from '../search/depthFirstSearch';
import { spanTree } from '../search/spanningTree';

export default class ConnectednessTester {

    static isConnected(graph) {

        let tree = spanTree(graph);

        if (tree.length === graph.vertices.length) {
            return true;
        } else {
            return false;
        }
    }

}
