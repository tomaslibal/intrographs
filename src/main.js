// intrographs "main" entry point

import { getGraph } from "./graphs/Graph";
import HTMLScene from "./presentation/html/HTMLScene";
import PolymerBridge from "./presentation/html/PolymerBridge";

let K3 = getGraph();
K3.addVertex({ name: "B" });
K3.addVertex({ name: "C" });
K3.addEdge(["A", "B"]);
K3.addEdge(["A", "C"]);
K3.addEdge(["B", "C"]);

function init() {
    let pb = new PolymerBridge(document);
    let scene = new HTMLScene(K3, window);

    scene.renderAll();
}

init();
