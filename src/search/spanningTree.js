import MathUtil from '../common/MathUtil';

function spanTree(graph, v=null) {
	// mark all vertices as "new"
	graph.vertices.forEach(ver => {
		ver._span_label = 0;
	});

	if (v === null) {
		const len = graph.vertices.length;
		const rand = MathUtil.getRandomArbitrary(0, len-1);
		v = graph.vertices[rand].name;
		graph.vertices[rand]._span_label = 1;
	}

	let tree = [v];

	branchFromVertex(graph, v, tree);

	return tree;
}

function branchFromVertex(graph, v, tree) {
	let adj = graph.adj(v);

	// for all adjacent nodes check if one of them is the node to be looked
	// up. If not, continue searching down the depth of the tree
	adj.forEach(vName => {
		let vvv = graph.lookupVertex(graph.vertices, vName);

		// only if marked as "new"
		if (vvv._span_label === 0) {
			tree.push(vName);

			vvv._span_label = 1;

			branchFromVertex(graph, vName, tree);
		}
	});
}

export { spanTree };