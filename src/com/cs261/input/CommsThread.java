package com.cs261.input;

import com.cs261.analysis.Analyser;
import com.cs261.main.Reference;
import com.cs261.output.AlertPrinter;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class CommsThread implements Runnable {

    public void run() { // Threads running code.
        try {
            System.out.println("In the comms thread");

            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            //get current date time with Date()
            Date currentdate = new Date();
            String dateStr = dateFormat.format(currentdate);
            boolean DoesExist = new File("data/" + dateStr + "commsstore.csv").exists();
            final PrintWriter outputTrades = new PrintWriter(new FileWriter("data/" + dateStr + "commsstore.csv", true));
            //Define a writer for outputting trades.

            String hostName = "cs261.dcs.warwick.ac.uk";
            // Declare the host for the socket.
            int portNumber = Integer.parseInt("1720");
            // Declare the port number to listen to.
            final Socket echoSocket = new Socket(hostName, portNumber);
            // Establish a socket using the chosen host and port.

            Analyser analyser = new Analyser("Comms", Reference.RADIUS);
            boolean analyse = true;
            boolean ignoreFirst = true;
            int i = 0;            

            HashMap<String, HashMap<String, List<Integer>>> history = new HashMap<String, HashMap<String, List<Integer>>>();

            try {
                BufferedReader tradesocket = new BufferedReader(new InputStreamReader(echoSocket.getInputStream())); // Begin reading from socket.
                String lineinput; // String to hold each line read in.
                String[] lineseperated; // String array to separate into individual data e.g. date, buyer, seller
                if (DoesExist) {
                    lineinput = tradesocket.readLine(); // ignore first line if appending to file
                }
                while ((lineinput = tradesocket.readLine()) != null) {
                    lineseperated = lineinput.split(","); // Splits the read line into individual data.
                    // Output to csv file, note that to separate columns need to use a "," and to separate rows per println.
                    for (i = 0; i < lineseperated.length; i++) {
                        outputTrades.append(lineseperated[i]);
                        if (i == lineseperated.length - 1) {
                            outputTrades.append("\n");
                        } else {
                            outputTrades.append(",");
                        }
                    }
                    if (!ignoreFirst) {
                        int x = Integer.parseInt(lineseperated[0].substring(11, 13)) * 60 * 60
                                + Integer.parseInt(lineseperated[0].substring(14, 16)) * 60
                                + Integer.parseInt(lineseperated[0].substring(17, 19));
                        int y = 0;
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
                        analyser.addNode(lineseperated, x, y);
                    }
                    ignoreFirst = false;
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    } else if (Calendar.getInstance().get(Calendar.MINUTE) % Reference.TIME_INTERVAL == 0 && analyse) { //Every 5 minutes
                        AlertPrinter printer = new AlertPrinter("Comms", analyser.analyse());
                        printer.print();
                        analyse = false;
                    } else if (Calendar.getInstance().get(Calendar.MINUTE) % Reference.TIME_INTERVAL == 1) { // 1min after that ^
                        analyse = true;
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Ctrl-c caught (trades)");
                for (; i < lineseperated.length; i++) {
                    outputTrades.append(lineseperated[i]);
                    if (i == lineseperated.length - 1) {
                        outputTrades.append("\n");
                    } else {
                        outputTrades.append(",");
                    }
                }
            } finally { // Runs after try whether success or fail.
                outputTrades.close(); // Close link to file output.
                echoSocket.close(); // Close link to socket.
            }
        } catch (IOException IOEx) {
            IOEx.printStackTrace();
        }
    }
}
 
