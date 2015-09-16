/*
 * Depth-first search.
 *
 * Algorithm inspired by Chiba and Nishizeki (2008, p. 30).
 */
 
// u = vertex to be found
// path = array of visited nodes in order of visit
function dfs(graph, u, path=[]) {
	// mark all vertices as "new"
	graph.vertices.forEach(ver => {
		ver._dfs_label = 0;
	});

	// copy the vertex set into 'stack'
	let stack = graph.vertices.map(ver => {
		return ver.name;
	});
	let found = null;

	// while nodes to be explored > 0 and still have not found the node do:
	while(stack.length > 0 && found === null) {
		let v = stack.shift();
		// run a search of all adjacent nodes recursively
		found = search(v, graph, u, path);
	};

	return found;
}

function search(v, graph, u, path) {
	let vv = graph.lookupVertex(graph.vertices, v);

	// mark node as "old"
	vv._dfs_label = 1;

	let adj = graph.adj(v);

	let ret = null;

	// for all adjacent nodes check if one of them is the node to be looked
	// up. If not, continue searching down the depth of the tree
	adj.forEach(vName => {
		let vvv = graph.lookupVertex(graph.vertices, vName);

		// only if marked as "new"
		if (vvv._dfs_label === 0) {
			path.push([v, vName]);

			// check if found the node
			if (vName === u) {
				ret = vvv;
			} else {
				// keep searching
				search(vName, graph, u, path);
			}
		}
	});

	return ret;
}

export { dfs };

