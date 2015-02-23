package com.cs261.input;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class TradesThread implements Runnable {
    public void run() { // Threads running code.
        try {
            System.out.println("In the trades thread");

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

            Runtime.getRuntime().addShutdownHook(new Thread() {
                // Code run when program is stopped via Ctrl+c
                public void run() {
                    try {
                        System.out.println("Ctrl-c caught");
                        outputTrades.close(); // Close link to file output.
                        echoSocket.close(); // Close link to socket.
                    } catch (IOException IOEx) {
                        IOEx.printStackTrace();
                    }
                }

            });

            try {


                BufferedReader tradesocket = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                // Begin reading from socket.
                String lineinput; // String to hold each line read in.
                String[] lineseperated; // String array to separate into individual data e.g. date, buyer, seller
                if (DoesExist) {
                    lineinput = tradesocket.readLine(); // ignore first line if appending to file
                }
                while ((lineinput = tradesocket.readLine()) != null) {
                    lineseperated = lineinput.split(","); // Splits the read line into individual data.
                    // Output to csv file, note that to separate columns need to use a "," and to separate rows per println.
                    for (int i = 0; i < lineseperated.length; i++) {
                        outputTrades.append(lineseperated[i]);
                        if (i == lineseperated.length - 1) {
                            outputTrades.append("\n");
                        } else {
                            outputTrades.append(",");
                        }
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
