package com.cs261.main;

import com.cs261.input.CommsThread;
import com.cs261.input.TradesThread;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class Main {
// Implements runnable to allow the change of threads run code.

    public static HashMap<Integer, HashMap<String, Double>> tradesAverages;

    public static void main(String[] args) throws IOException {

        final Object event = new Object();

        DateFormat dateFormatForCheck = new SimpleDateFormat("yyyyMMdd");
        //get current date time with Date()
        Date currentdate = new Date();
        Date cutoffdate = new Date(currentdate.getTime() - 14 * 24 * 3600 * 1000);
        String dateStr = dateFormatForCheck.format(cutoffdate);
        System.out.println("Cut off date " + dateStr);

        File[] trades = new File("data").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.matches(".+tradesstore\\.csv$");
            }
        });
        File[] comms = new File("data").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.matches(".+commsstore\\.csv$");
            }
        });

        if (trades.length > 0) {
            Calendar cal = Calendar.getInstance();
            for (File file : trades) {
                String name = file.getName();
                cal.set(Integer.parseInt(name.substring(0, 4)), Integer.parseInt(name.substring(4, 6)) - 1, Integer.parseInt(name.substring(6, 8)));
                Date fileDate = cal.getTime();
                System.out.println(dateFormatForCheck.format(fileDate) + " " + fileDate.before(cutoffdate));
                if (fileDate.before(cutoffdate)) {
                    if (!file.delete()) {
                        throw new IOException("Could not delete file " + file.getName());
                    }
                }
            }
        }
        if (comms.length > 0) {
            Calendar cal = Calendar.getInstance();
            for (File file : comms) {
                String name = file.getName();
                cal.set(Integer.parseInt(name.substring(0, 4)), Integer.parseInt(name.substring(4, 6)) - 1, Integer.parseInt(name.substring(6, 8)));
                Date fileDate = cal.getTime();
                System.out.println(dateFormatForCheck.format(fileDate) + " " + fileDate.before(cutoffdate));
                if (fileDate.before(cutoffdate)) {
                    if (!file.delete()) {
                        throw new IOException("Could not delete file " + file.getName());
                    }
                }
            }
        }

        Thread Tradethread = new Thread(new TradesThread()); // Creates new thread.
        Thread Commsthread = new Thread(new CommsThread()); // Creates new thread.
        System.out.println("Should start the thread");
        Tradethread.start(); // Runs new thread.
        Commsthread.start(); // Runs new thread.
        System.out.println("Should occur before thread finishes thread");

        synchronized (event) {
            while (true) { //This loop waits until after 00:30, so that it doesn't start-stop-start-stop etc
                try {
                    event.wait(5 * 60 * 1000);
                    Calendar cal = Calendar.getInstance();
                    if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) > 30) {
                        calcAverages(trades);
                        break;
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            while (true) {
                try {
                    event.wait(1 * 60 * 1000); //One minute timeout
                    Calendar cal = Calendar.getInstance();
                    if (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) < 30) {
                        //Shut down at some point between 00:00 and 00:30
                        Tradethread.interrupt();
                        Tradethread.join();
                        Commsthread.interrupt();
                        Commsthread.join();
                        //Start a new process
                        ProcessBuilder pb = new ProcessBuilder("java", "Main");
                        pb.directory(new File("/src/com/cs261/main/"));
                        File log = new File("log");
                        pb.redirectErrorStream(true);
                        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(log));
                        Process p = pb.start();
                        assert pb.redirectInput() == ProcessBuilder.Redirect.PIPE;
                        assert pb.redirectOutput().file() == log;
                        assert p.getInputStream().read() == -1;
                        //Analysisthread.interrupt();
                        throw new InterruptedException();
                    }
                } catch (InterruptedException e) {
                    System.out.println("Main thread interrupted, exiting...");
                    break;
                }
            }
        }
    }

    private static void calcAverages(File[] trades) {
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

