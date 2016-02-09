/**
 * INTROGRAPHS 2.0.0-pre
 */

import { HTMLScene, Scene } from './presentation/Scene';
import { Graph } from './graphs/Graph';

let K3 = new Graph();
K3.addVertex({ id: "A" });
K3.addVertex({ id: "B" });
K3.addVertex({ id: "C" });
K3.addEdge(["A", "B"]);
K3.addEdge(["A", "C"]);
K3.addEdge(["B", "C"]);

export function init(sc: Scene) {
	sc.render();
}

init(new HTMLScene(K3, window));