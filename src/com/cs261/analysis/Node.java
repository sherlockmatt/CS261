package com.cs261.analysis;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int id;
    private String[] content;
    private int x;
    private int y;
    private List<Node> edges;
    private int edgeCount;

    public Node(int id, String[] content, int x, int y) {
        this.id = id;
        this.content = content;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<Node>();
        this.edgeCount = 0;
    }

    public void addEdge(Node node) {
        edgeCount++;
        this.edges.add(node);
    }

    public List<Node> getConnected() {
        return this.edges;
    }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getId() { return this.id; }
    public String[] getContent() { return this.content; }
    public int getEdgeCount() {
        return this.edgeCount;
    }
}
