package com.cs261.output;

import com.cs261.analysis.Node;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlertPrinter {

    private List<Node> nodes;
    private int id;
    private String type;
    private String[] headers;

    public AlertPrinter(String type, List<Node> nodes) {
        this.type = type;
        this.nodes = nodes;

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date currentdate = new Date();
        String dateStr = dateFormat.format(currentdate);
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/" + dateStr + type.toLowerCase() + "store.csv"));
            headers = br.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        File[] alerts = new File("alerts").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.matches("^" + type + " [0-9]+\\.csv$");
            }
        });
        if (alerts.length > 0) {
            String oldFileName = alerts[alerts.length - 1].getName();
            String oldFileName2 = oldFileName.split(" ")[1];
            String oldId = oldFileName2.substring(0,oldFileName2.length() - 4);
            System.out.println(oldFileName + " | " + oldFileName2 + " | " + oldId);
            this.id = Integer.parseInt(oldId) + 1;
        }
        try {
            PrintWriter outputFile = new PrintWriter(new FileWriter("alerts/" + type + " " + id + ".csv", true));

            for (int i = 0; i < headers.length; i++) {
                outputFile.append(headers[i]);
                if (i == headers.length - 1) {
                    outputFile.append("\n");
                } else {
                    outputFile.append(",");
                }
            }

            for (Node node : nodes) {
                for (int i = 0; i < node.getContent().length; i++) {
                    outputFile.append(node.getContent()[i]);
                    if (i == node.getContent().length - 1) {
                        outputFile.append("\n");
                    } else {
                        outputFile.append(",");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
