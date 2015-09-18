import { dfs } from '../search/depthFirstSearch';

function addIfNotFound(array, member) {
    if (array.indexOf(member) === -1) {
        return array.push(member);
    } else {
        return array;
    }
}

function uniqueValues(array) {
    let uniques = [];

    array.forEach(member => {
        if (member.constructor.name === 'Array') {
            let mems = uniqueValues(member);
            mems.forEach(m => {
                addIfNotFound(uniques, m);
            });
        } else {
            addIfNotFound(uniques, member);
        }
    });

    return uniques;
}

export default class ConnectednessTester {

    static isConnected(graph) {
        let path = [];
        dfs(graph, 'non-existent', path);

        const uniq = uniqueValues(path);

        if (uniq.length === graph.vertices.length) {
            return true;
        } else {
            return false;
        }
    }

}
