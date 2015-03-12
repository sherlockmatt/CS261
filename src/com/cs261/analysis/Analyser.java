package com.cs261.analysis;

import com.cs261.main.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Analyser {

    private Graph graph;
    private int radius;
    private String type;

    public Analyser(String type, int radius) {
        this.setType(type);
        this.graph = new Graph();
        this.radius = radius;
    }

    public List<Node> analyse() {
        //Perform DFS to find all connected components
        List<Node> nodes = this.graph.getAllNodes();
        List<Node> output = new ArrayList<Node>();
        List<Node> visited = new ArrayList<Node>();

        while (nodes.size() > 0) {
            List<Node> connectedComponent = new ArrayList<Node>();
            Stack<Node> stack = new Stack<Node>();
            stack.push(nodes.get(0)); //Add a new start node
            while (!stack.empty()) {
                Node n1 = stack.pop();
                connectedComponent.add(n1);
                visited.add(n1);
                for (Node n2 : n1.getConnected()) {
                    if (!visited.contains(n2)) {
                        stack.push(n2);
                        visited.add(n2);
                    }
                }
            }
            nodes.removeAll(connectedComponent);
            if (connectedComponent.size() > Reference.CLUSTER_LOWER_BOUND && connectedComponent.size() < Reference.CLUSTER_UPPER_BOUND) {
                connectedComponent.add(new Node(-1, this.getType().equals("Trades") ? Reference.SEPERATOR_TRADES : Reference.SEPERATOR_COMMS, 0, 0));
                output.addAll(connectedComponent);
            }
        }

        return output;
    }

    public void addNode(String[] content, int x, int y) {
        this.addNode(content, x, y, this.radius);
    }

    public void addNode(String[] content, int x, int y, int radius) {
        this.graph.addNode(content, x, y, radius);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}