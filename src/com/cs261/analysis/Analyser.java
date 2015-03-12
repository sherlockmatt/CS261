package com.cs261.analysis;

import com.cs261.main.Main;
import com.cs261.main.Query;
import com.cs261.main.Reference;
import com.cs261.output.AlertPrinter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Analyser {

    private Graph graph;
    private int radius;
    private String type;

    public HashMap<Integer, HashMap<String, Double>> tradesAverages;

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
        HashMap<String, HashMap<String, List<Integer>>> history = new HashMap<String, HashMap<String, List<Integer>>>();
        this.setType(query.getType());

        calcAverages();

        //Add everything to the graph
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            String[] lineseperated;
            line = br.readLine();
            while ((line = br.readLine()) != null) {
                lineseperated = line.split(",");
                if (isLineInTime(lineseperated, query.getTime())) {
                    int x = Integer.parseInt(lineseperated[0].substring(11, 13)) * 60 * 60
                            + Integer.parseInt(lineseperated[0].substring(14, 16)) * 60
                            + Integer.parseInt(lineseperated[0].substring(17, 19));

                    int y = 0;
                    if (this.getType().equals("Trades")) {
                        for (int i = 1; i < 14; i++) {
                            int date = Integer.parseInt(lineseperated[0].substring(8, 10)) - i;
                            if (tradesAverages.containsKey(date)) {
                                HashMap<String, Double> dateMap = tradesAverages.get(date);
                                Double avg = dateMap.get(lineseperated[6]);
                                y += Math.abs(Double.parseDouble(lineseperated[9]) - avg); //I think :3
                            }
                        }
                    } else {
                        String sender = lineseperated[1];
                        String[] receivers = lineseperated[2].split(";");
                        String trader1;
                        String trader2;
                        for (String recv : receivers) {
                            if (sender.compareTo(recv) < 0) {
                                trader1 = sender;
                                trader2 = recv;
                            } else {
                                trader1 = recv;
                                trader2 = sender;
                            }
                            if (history.containsKey(trader1)) {
                                if (history.get(trader1).containsKey(trader2)) {
                                    List<Integer> list = new ArrayList<Integer>();
                                    list.addAll(history.get(trader1).get(trader2));
                                    for (Integer i : list) {
                                        if (((i - Integer.parseInt(lineseperated[0].substring(17, 19))) + 60) % 60 > 5) {
                                            history.get(trader1).get(trader2).remove(i);
                                        } else {
                                            y++;
                                        }
                                    }
                                } else {
                                    history.get(trader1).put(trader2, new ArrayList<Integer>());
                                }
                            } else {
                                history.put(trader1, new HashMap<String, List<Integer>>());
                                history.get(trader1).put(trader2, new ArrayList<Integer>());
                            }
                            history.get(trader1).get(trader2).add(Integer.parseInt(lineseperated[0].substring(17, 19)));
                        }
                    }

                    this.addNode(lineseperated, x, y);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            List<Node> output = new ArrayList<Node>(1);
            output.add(new Node(0, (this.getType().equals("Trades")) ? Reference.FAILED_TRADES : Reference.FAILED_COMMS, 0, 0));
            return output;
        }

        return analyse();
    }

    public List<Node> analyse() {
        //Perform DFS to find all connected components
        List<Node> nodes = this.graph.getAllNodes();
        List<Node> output = new ArrayList<Node>();
        List<Node> visited = new ArrayList<Node>();

        while (nodes.size() > 0) {
            List<Node> connectedComponent = new ArrayList<Node>();
            Stack<Node> stack = new Stack<Node>();
            stack.push(nodes.get(0)); //Add a new start node
            while (!stack.empty()) {
                Node n1 = stack.pop();
                connectedComponent.add(n1);
                visited.add(n1);
                for (Node n2 : n1.getConnected()) {
                    if (!visited.contains(n2)) {
                        stack.push(n2);
                        visited.add(n2);
                    }
                }
            }
            nodes.removeAll(connectedComponent);
            if (connectedComponent.size() > Reference.CLUSTER_LOWER_BOUND && connectedComponent.size() < Reference.CLUSTER_UPPER_BOUND) {
                connectedComponent.add(new Node(-1, this.getType().equals("Trades") ? Reference.SEPERATOR_TRADES : Reference.SEPERATOR_COMMS, 0, 0));
                output.addAll(connectedComponent);
            }
        }

        return output;
    }

    public void addNode(String[] content, int x, int y) {
        this.addNode(content, x, y, this.radius);
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

    private void calcAverages() {
        File[] trades = new File("data").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.matches(".+tradesstore\\.csv$");
            }
        });
        HashMap<Integer, HashMap<String, Double>> tradesMap = new HashMap<Integer, HashMap<String, Double>>();

        for (File f : trades) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                String[] lineseperated;
                String stock;
                Double cost;
                HashMap<String, Integer> cntr = new HashMap<String, Integer>();
                int date = Integer.parseInt(f.getName().substring(6, 8));
                tradesMap.put(date, new HashMap<String, Double>());

                line = br.readLine(); //Skips the first line
                while ((line = br.readLine()) != null) {
                    lineseperated = line.split(",");
                    stock = lineseperated[6];
                    cost = Double.parseDouble(lineseperated[9]);
                    int oldCntr = (cntr.containsKey(stock)) ? cntr.get(stock) : 0;
                    int newCntr = oldCntr + 1;
                    Double oldAvg = (tradesMap.get(date).containsKey(stock)) ? tradesMap.get(date).get(stock) : 0;
                    Double newAvg = (oldCntr * oldAvg + cost) / newCntr;

                    if (cntr.containsKey(stock)) cntr.remove(stock);
                    cntr.put(stock, newCntr);
                    if (tradesMap.get(date).containsKey(stock)) tradesMap.get(date).remove(stock);
                    tradesMap.get(date).put(stock, newAvg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        tradesAverages = tradesMap;
    }
}