package com.cs261.analysis;

import com.cs261.main.Query;
import com.cs261.output.AlertPrinter;
import com.cs261.output.QueryPrinter;

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

    public List<Node> analyse(Query query) {
        //Set stuff based on query

        //Add everything to the graph

        //Perform DFS to find all connected components
        List<Node> nodes = this.graph.getAllNodes();
        List<Node> output = new ArrayList<Node>();

        while (nodes.size() > 0) {
            List<Node> connectedComponent = new ArrayList<Node>();
            Stack<Node> stack = new Stack<Node>();
            stack.push(nodes.get(0)); //Add a new start node
            while (!stack.empty()) {
                Node n1 = stack.pop();
                connectedComponent.add(n1);
                for (Node n2 : n1.getConnected()) {
                    stack.push(n2);
                }
            }
            if (connectedComponent.size() > 3 && connectedComponent.size() < 1000) {//Sensitivity values, will likely need tweaking        <════════        <═════════
                output.addAll(connectedComponent);
            }
        }

        return output;
    }

    public void analyse() {
        //Perform DFS to find all connected components
        List<Node> nodes = this.graph.getAllNodes();
        List<Node> output = new ArrayList<Node>();

        while (nodes.size() > 0) {
            List<Node> connectedComponent = new ArrayList<Node>();
            Stack<Node> stack = new Stack<Node>();
            stack.push(nodes.get(0)); //Add a new start node
            while (!stack.empty()) {
                Node n1 = stack.pop();
                connectedComponent.add(n1);
                for (Node n2 : n1.getConnected()) {
                    stack.push(n2);
                }
            }
            if (connectedComponent.size() > 3 && connectedComponent.size() < 1000) {//Sensitivity values, will likely need tweaking        <════════        <═════════
                output.addAll(connectedComponent);
            }
        }

        AlertPrinter printer = new AlertPrinter(output);
        printer.print();
    }

    public void addNode(String[] content, int x, int y) {
        this.graph.addNode(content, x, y, this.radius);
    }

    public void addNode(String[] content, int x, int y, int radius) {
        this.graph.addNode(content, x, y, radius);
    }

    public List<Node> getNodesInRadius(Node node, int radius) {
        return this.graph.getNodesInRadius(node, radius);
    }

    public List<Node> getConnected(Node node) {
        return this.graph.getConnected(node);
    }

    public void updateRadius(int radius) {
        this.radius = radius;
    }
}