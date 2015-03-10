package com.cs261.analysis;

import com.cs261.main.Query;
import com.cs261.main.Reference;
import com.cs261.output.AlertPrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public List<Node> analyse(Query query) {
        //Set stuff based on query
        DateFormat dateFormatForCheck = new SimpleDateFormat("yyyyMMdd");
        String dateStr = dateFormatForCheck.format(query.getDate());
        String fileName = "data/" + dateStr + query.getType().toLowerCase() + "store.csv";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));

            //Add everything to the graph
            String line;
            String[] lineseperated;
            while ((line = br.readLine()) != null) {
                lineseperated = line.split(",");
                if (isLineInTime(lineseperated, query.getTime())) {
                    this.addNode(lineseperated, 0, 0); //Copy the x,y from trades/comms later            <------------               <--------------                 <----------------
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            List<Node> output = new ArrayList<Node>(1);
            output.add(new Node(0, (this.getType().equals("Trades")) ? Reference.FAILED_TRADES : Reference.FAILED_COMMS, 0, 0));
            return output;
        }

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
            if (connectedComponent.size() > Reference.CLUSTER_LOWER_BOUND && connectedComponent.size() < Reference.CLUSTER_UPPER_BOUND) {
                connectedComponent.add(new Node(-1, query.getType().equals("Trades") ? Reference.SEPERATOR_TRADES : Reference.SEPERATOR_COMMS, 0,0));
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
            if (connectedComponent.size() > Reference.CLUSTER_LOWER_BOUND && connectedComponent.size() < Reference.CLUSTER_UPPER_BOUND) {
                connectedComponent.add(new Node(-1, this.getType().equals("Trades") ? Reference.SEPERATOR_TRADES : Reference.SEPERATOR_COMMS, 0,0));
                output.addAll(connectedComponent);
            }
        }

        AlertPrinter printer = new AlertPrinter(this.getType(), output);
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

    public boolean isLineInTime(String[] line, int time) {
        int lineTime = Integer.parseInt(line[0].substring(11, 13));
        return lineTime == time;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}