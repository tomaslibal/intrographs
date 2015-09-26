export default class IsolatedSetFinder {

	/*
	 * This method uses a "randomly" shuffled array of vertices in Graph.findLowestDegreeVertex
	 * so the initial vertex may differ every time the method is run on the same graph.
	 *
	 * This algorithm has been based on "the simplest reasonable heurstics" to find an independent
	 * set in a graph by Skiena (2009, p. 529).
	 */
	static findRandomIsolatedSet(g) {
		let vCopy = g.vertices.slice(0);
		let eCopy = g.edges.slice(0);
		let indepSet = [];

		while(vCopy.length > 0) {
			let l = g.findLowestDegreeVertex(vCopy, eCopy);
			if (l !== null) {
				indepSet.push(l);
				let toRemove = g.adj(l.name, eCopy);
				toRemove.push(l.name);
				vCopy = vCopy.filter(vertex => {
					if (toRemove.indexOf(vertex.name) === -1) return true;
				});
				eCopy = eCopy.filter(edge => {
					if (toRemove.indexOf(edge.connects[0]) === -1 && toRemove.indexOf(edge.connects[1]) === -1) return true;
				});

			} else { break; }
		}
		return indepSet;
	}
}