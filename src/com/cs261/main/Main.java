package com.cs261.main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        JFrame f = new JFrame("GraphPanel");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        f.setPreferredSize(new Dimension(1366, 768));
        GraphPanel gp = new GraphPanel(Color.red);
        f.add(new JScrollPane(gp), BorderLayout.CENTER);
        f.pack();
        f.setLocationByPlatform(true);
        f.setVisible(true);

        int i = 0;
        while (i < (args.length - (args.length % 3)) / 3) {
            String file = args[3*i];
            gp.setColour(new Color(Integer.parseInt(args[3*i + 2], 16)));
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                String[] lineSplit;
                int done = 0;
                int prevMinute = 0;
                int cntr = 0;
                Node prevNode = null;

                line = br.readLine(); //Yes, that's two.
                while (line != null) {
                    lineSplit = line.split(",");
                    int hours = Integer.parseInt(lineSplit[0].substring(11, 13));
                    int minutes = Integer.parseInt(lineSplit[0].substring(14, 16));
                    int seconds = Integer.parseInt(lineSplit[0].substring(17, 19));

                    int curMinute = hours * 60 + minutes;

                    if (curMinute == prevMinute) {
                        cntr++;
                    } else {
                        int x = (int) (gp.getWidth() * (curMinute / 1440.0));
                        int y = gp.getHeight() - (cntr * Integer.parseInt(args[3*i + 1]));
                        if (x > gp.getWidth()) {
                            x = gp.getWidth();
                        }
                        if (y < 0) {
                            y = 0;
                        }
                        Node curNode = gp.newNode(x, y);
                        System.out.println("Added node " + done++ + " at [" + x + "," + y + "]");
                        if (prevNode != null) {
                            gp.newEdge(prevNode, curNode);
                        }
                        prevNode = curNode;
                        cntr = 0;
                        prevMinute = curMinute;
                    }

                    line = br.readLine();
                }
            } catch (IOException e) {
                System.err.println(e.toString());
            } finally {
                gp.repaint();
            }
            i++;
        }
    }
}

class GraphPanel extends JComponent {

    private static final int RADIUS = 2;
    private List<Node> nodes = new ArrayList<Node>();
    private List<Edge> edges = new ArrayList<Edge>();
    private Color colour;

    public GraphPanel(Color colour) {
        this.setOpaque(true);
        this.colour = colour;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0xF0F0F0));
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Edge e : edges) {
            e.draw(g);
        }
        for (Node n : nodes) {
            n.draw(g);
        }
    }

    public Node newNode(int x, int y) {
        Point p = new Point(x, y);
        Node node = new Node(p, RADIUS, this.colour);
        nodes.add(node);
        return node;
    }

    public void newEdge(Node n1, Node n2) {
        edges.add(new Edge(n1, n2, this.colour));
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }
}

class Node {

    private Point p;
    private int r;
    private Color colour;
    private Rectangle bb = new Rectangle();

    public Node(Point p, int r, Color colour) {
        this.p = p;
        this.r = r;
        this.colour = colour;
        setBoundary(bb);
    }

    private void setBoundary(Rectangle bb) {
        bb.setBounds(p.x - r, p.y - r, 2 * r, 2 * r);
    }

    public void draw(Graphics g) {
        g.setColor(this.colour);
        g.fillOval(bb.x, bb.y, bb.width, bb.height);
    }

    public Point getLocation() {
        return p;
    }
}

class Edge {

    private Node n1;
    private Node n2;
    private Color colour;

    public Edge(Node n1, Node n2, Color colour) {
        this.n1 = n1;
        this.n2 = n2;
        this.colour = colour;
    }

    public void draw(Graphics g) {
        Point p1 = n1.getLocation();
        Point p2 = n2.getLocation();
        g.setColor(this.colour);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
}