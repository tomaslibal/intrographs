/**
 * INTROGRAPHS 2.0.0-pre
 */

import { HTMLScene } from 'presentation/Scene';
import { Graph } from 'graphs/Graph';

let K3 = new Graph();
K3.addVertex({ id: "B" });
K3.addVertex({ id: "C" });
K3.addEdge(["A", "B"]);
K3.addEdge(["A", "C"]);
K3.addEdge(["B", "C"]);

function init() {
	let scene = new HTMLScene(K3, window);
	scene.render();
}