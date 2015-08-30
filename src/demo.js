// interim "main" entry point

import { Graph, getGraph } from "./graphs/Graph";
import GraphRenderer2D from "./presentation/GraphRenderer2D";
//import CyclicGraph from "./graphs/CyclicGraph";
//let C5 = new CyclicGraph(5);

let graphRenderer = new GraphRenderer2D();
let canvas = document.querySelector("#canvas");
let ctx = graphRenderer.getContext(canvas);
let K3 = getGraph();

K3.addVertex({ name: "B" });
K3.addVertex({ name: "C" });

K3.addEdge(["A", "B"]);
K3.addEdge(["A", "C"]);
K3.addEdge(["B", "C"]);

graphRenderer.setCanvas(canvas);
graphRenderer.render(K3);
