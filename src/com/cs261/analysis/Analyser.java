package com.cs261.analysis;

public class Analyser {

    private Graph graph;

    public Analyser() {
        graph = new Graph();
    }

    /**
     * The method which runs analysis on the current graph, using the supplied query
     * @param query The query to run
     * @return true if success, false if failure
     */
    public boolean analyse(String query) {
        return false;
    }

    public void addNode(String name, int x, int y) {
        Node tempNode = new Node(name, x, y);
        graph.addNode(tempNode);
    }

    public void addEdge(Node n1, Node n2) {
        Edge tempEdge = new Edge(n1, n2);
        graph.addEdge(tempEdge);
    }

}
