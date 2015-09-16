// u = vertex to be found
// path = array of visited nodes in order of visit
function dfs(graph, u, path=[]) {
	// mark all vertices as "new"
	graph.vertices.forEach(ver => {
		ver._dfs_label = 0;
	});

	let stack = graph.vertices.map(ver => {
		return ver.name;
	});
	let found = null;

	while(stack.length > 0 && found === null) {
		let v = stack.shift();
		found = search(v, graph, u, path);
	};
}

function search(v, graph, u, path) {
	let vv = graph.lookupVertex(graph.vertices, v);

	vv._dfs_label = 1;

	let adj = graph.adj(v);

	adj.forEach(vName => {
		let vvv = graph.lookupVertex(graph.vertices, vName);

		if (vvv._dfs_label === 0) {

			path.push([v, vName]);
			search(vName, graph, u, path);
		}
	});

	return null;
}

export { dfs };

