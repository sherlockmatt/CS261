package com.cs261.analysis;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private int id;
    private String name;
    private String content;
    private int x;
    private int y;
    private List<Node> edges;

    public Node(int id, String name, String content, int x, int y) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.x = x;
        this.y = y;
        edges = new ArrayList<Node>();
    }

    public void addEdge(Node node) {
        edges.add(node);
    }

    public List<Node> getConnected() {
        return edges;
    }

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public String getName() { return this.name; }
    public String getContent() { return  this.content; }
}
