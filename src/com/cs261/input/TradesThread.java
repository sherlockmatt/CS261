package com.cs261.input;

import com.cs261.analysis.Analyser;
import com.cs261.main.Reference;
import com.cs261.output.AlertPrinter;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class TradesThread implements Runnable {
    public HashMap<Integer, HashMap<String, Double>> tradesAverages;

    public void run() { // Threads running code.
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            //get current date time with Date()
            Date currentdate = new Date();
            String dateStr = dateFormat.format(currentdate);
            boolean DoesExist = new File("data/" + dateStr + "tradesstore.csv").exists();
            final PrintWriter outputTrades = new PrintWriter(new FileWriter("data/" + dateStr + "tradesstore.csv", true));
            //Define a writer for outputting trades.

            String hostName = "cs261.dcs.warwick.ac.uk";
            // Declare the host for the socket.
            int portNumber = Integer.parseInt("80");
            // Declare the port number to listen to.
            final Socket echoSocket = new Socket(hostName, portNumber);
            // Establish a socket using the chosen host and port.

            Analyser analyser = new Analyser("Trades", Reference.RADIUS);
            boolean analyse = false;
            boolean ignoreFirst = true;
            calcAverages();

            String lineinput; // String to hold each line read in.
            String[] lineseperated = new String[10]; // String array to separate into individual data e.g. date, buyer, seller
            try {
                BufferedReader tradesocket = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                // Begin reading from socket.
                if (DoesExist) {
                    lineinput = tradesocket.readLine(); // ignore first line if appending to file
                }
                while ((lineinput = tradesocket.readLine()) != null) {
                    lineseperated = lineinput.split(","); // Splits the read line into individual data.
                    // Output to csv file, note that to separate columns need to use a "," and to separate rows per println.
                    for (int num = 0; num < lineseperated.length; num++) {
                        outputTrades.append(lineseperated[num]);
                        if (num == lineseperated.length - 1) {
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
                        for (int j = 1; j < 14; j++) {
                            int date = Integer.parseInt(lineseperated[0].substring(8, 10)) - j;
                            if (tradesAverages.containsKey(date)) {
                                HashMap<String, Double> dateMap = tradesAverages.get(date);
                                Double avg = dateMap.get(lineseperated[6]);
                                y += Math.abs(Double.parseDouble(lineseperated[9]) - avg); //I think :3
                            }
                        }
                        analyser.addNode(lineseperated, x, y);
                    }
                    ignoreFirst = false;
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    } else if (Calendar.getInstance().get(Calendar.MINUTE) % Reference.TIME_INTERVAL == 0 && analyse) { //Every 5 minutes
                        AlertPrinter printer = new AlertPrinter("Trades", analyser.analyse());
                        printer.print();
                        analyse = false;
                    } else if (Calendar.getInstance().get(Calendar.MINUTE) % Reference.TIME_INTERVAL == 1) { // 1min after that ^
                        analyse = true;
                    }
                }
            } catch (InterruptedException e) {
                outputTrades.close(); // Close link to file output.
                echoSocket.close(); // Close link to socket.
            } finally { // Runs after try whether success or fail.
                outputTrades.close(); // Close link to file output.
                echoSocket.close(); // Close link to socket.
            }
        } catch (IOException IOEx) {
            IOEx.printStackTrace();
        }
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
