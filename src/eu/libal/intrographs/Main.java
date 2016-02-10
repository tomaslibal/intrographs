package eu.libal.intrographs;

import eu.libal.intrographs.edge.Edge;

public class Main {
    public static void main(String[] args) {
        System.out.println("INTROGRAPHS2");

        Graph<Integer, Edge<Integer>> g = new Graph<>();
        g.addVertex(1);
        g.addVertex(2);
    }
}
