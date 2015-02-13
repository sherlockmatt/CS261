package com.cs261.analysis;

import java.util.List;

public class Analyser {

    private Graph graph;
    private int radius;

    public Analyser(int radius) {
        this.graph = new Graph();
        this.radius = radius;
    }

    /**
     * The method which runs analysis on the current graph, using the supplied query
     *
     * @param query The query to run
     * @return true if success, false if failure
     */
    public boolean analyse(String query) {
        //Do stuff
        return false;
    }

    public void addNode(String name, String content, int x, int y) {
        graph.addNode(name, content, x, y, this.radius);
    }

    public List<Node> getNodesInRadius(Node node, int radius) {
        return graph.getNodesInRadius(node, radius);
    }

    public void updateRadius(int radius) {
        this.radius = radius;
    }
}