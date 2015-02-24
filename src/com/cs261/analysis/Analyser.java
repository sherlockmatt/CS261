package com.cs261.analysis;

import com.cs261.main.Query;

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
     * The method which runs analysis, using the supplied query
     *
     * @param query The query to run
     * @return true if success, false if failure
     */
    public boolean analyse(Query query) {
        if (query != null) {
            //Set stuff based on query

            //Add everything to the graph

        }

        //Perform DFS to find all connected components
        List<Node> nodes = this.graph.getAllNodes();
        while (nodes.size() > 0){
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
            //Do stuff with connectedComponent (i.e. output it)
            
        }

        if (query != null) {
            //output result
        } else {
            //output differently?
        }

        //Figure out what to return

        return false;
    }

    public void addNode(String name, String content, int x, int y) {
        this.graph.addNode(name, content, x, y, this.radius); //sdfsf
    }

    public void addNode(String name, String content, int x, int y, int radius) {
        this.graph.addNode(name, content, x, y, radius);
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