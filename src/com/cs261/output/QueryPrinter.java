package com.cs261.output;

import com.cs261.analysis.Node;
import com.cs261.main.Query;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class QueryPrinter {

    private Query query;
    private List<Node> nodes;

    public QueryPrinter(Query query, List<Node> nodes) {
        this.query = query;
        this.nodes = nodes;
    }

    public void print() {
        try {
            PrintWriter outputFile = new PrintWriter(new FileWriter("queries/" + query.getUUID() + "-output.csv", true));
            for (Node node : nodes) {
                outputFile.append(node + "\n"); //Should not be node, change soon to be content or whatever         <══════                <═══════
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
