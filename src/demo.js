// interim "main" entry point

import { Graph, getGraph } from "./graphs/Graph";
import HTMLScene from "./presentation/html/HTMLScene";

let K3 = getGraph();
K3.addVertex({ name: "B" });
K3.addVertex({ name: "C" });
K3.addEdge(["A", "B"]);
K3.addEdge(["A", "C"]);
K3.addEdge(["B", "C"]);

let scene = new HTMLScene(K3, window);
scene.renderAll();

// Extra, Prints the graph as an adjacency matrix to the console
// import MatrixPrinter from "./presentation/MatrixPrinter";
// import Matrix from "./common/Matrix";
// let mat = Matrix.fromGraph(K3.getVerticesList(), K3.edges);
// console.log(MatrixPrinter.ascii(mat));