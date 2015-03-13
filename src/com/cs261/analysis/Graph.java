package com.cs261.analysis;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Node> nodes;
    private int cntr = 0;

    public Graph() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(String[] content, int x, int y, int radius) {
        Node n1 = new Node(content, x, y);
        nodes.add(n1);
        cntr++;
        if (n1.getEdgeCount() > 0) {
            for (Node n2 : getNodesInRadius(n1, radius)) {
                n1.addEdge(n2);
                n2.addEdge(n1);
            }
        }
    }

    public List<Node> getAllNodes() {
        return nodes;
    }

    public List<Node> getNodesInRadius(Node node, int radius) {
        List<Node> tempNodes = new ArrayList<Node>();
        for (int i = 0; i < cntr; i++) {
            Node tempNode = nodes.get(i);
            if (Math.sqrt((node.getX() - tempNode.getX()) ^ 2 + (node.getY() - tempNode.getY()) ^ 2) <= radius) {
                if (node != tempNode) tempNodes.add(tempNode);
            }
        }
        return tempNodes;
    }
}