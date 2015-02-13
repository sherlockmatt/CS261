package com.cs261.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
        //Set stuff based on query

        //Perform DFS to find all connected components
        int cntr = 0;
        while (cntr < graph.getCount()){
            //Pick random start node that isn't done
            List<Node> connectedComponent = new ArrayList<Node>();
            Stack<Node> stack = new Stack<Node>();
            while (!stack.empty()) {
                Node n1 = stack.pop();
                connectedComponent.add(n1);
                for (Node n2 : n1.getConnected()) {
                    stack.push(n2);
                }
                cntr++;
            }
            //Do stuff with connectedComponent
        }

        //Figure out what to return
        return false;
    }

    public void addNode(String name, String content, int x, int y) {
        graph.addNode(name, content, x, y, this.radius);
    }

    public List<Node> getNodesInRadius(Node node, int radius) {
        return graph.getNodesInRadius(node, radius);
    }

    public List<Node> getConnected(Node node) {
        return graph.getConnected(node);
    }

    public void updateRadius(int radius) {
        this.radius = radius;
    }
}