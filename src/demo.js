// interim "main" entry point

import { Graph, getGraph } from "./graphs/Graph";
import BaseRenderer2D from "./render/BaseRenderer2D";
import GraphRenderer2D from "./render/GraphRenderer2D";

var K3 = getGraph();
K3.addVertex({ name: "B" });
K3.addVertex({ name: "C" });

K3.addEdge(["A", "B"]);
K3.addEdge(["A", "C"]);
K3.addEdge(["B", "C"]);

var baseRenderer = new BaseRenderer2D();
var graphRenderer = new GraphRenderer2D(baseRenderer);

var canvas = document.querySelector("#canvas");

var ctx = baseRenderer.getContext(canvas);

graphRenderer.setCanvas(canvas);
graphRenderer.render(K3);
