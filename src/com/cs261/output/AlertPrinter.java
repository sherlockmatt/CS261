package com.cs261.output;

import com.cs261.analysis.Node;

import java.io.*;
import java.util.List;

public class AlertPrinter {

    private List<Node> nodes;
    private int id;

    public AlertPrinter(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void print() {
        File[] alerts = new File("alerts").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.matches("Alert [0-9]+\\.csv$");
            }
        });
        if (alerts.length > 0) {
            id = Integer.parseInt(alerts[alerts.length].getName().split(" ")[1]) + 1;
        }
        try {
            PrintWriter outputFile = new PrintWriter(new FileWriter("alerts/Alert " + id + ".csv", true));
            for (Node node : nodes) {
                outputFile.append(node + "\n"); //Should not be node, change soon to be content or whatever         <══════                <═══════
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
