package com.cs261.main;

import com.cs261.input.CommsThread;
import com.cs261.input.TradesThread;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class Main {
// Implements runnable to allow the change of threads run code.


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
        //Thread Analysisthread = new Thread(new AnalysisThread());
        System.out.println("Should start the thread");
        Tradethread.start(); // Runs new thread.
        Commsthread.start(); // Runs new thread.
        //Analysisthread.start();
        System.out.println("Should occur before thread finishes thread");

        synchronized (event) {
            while (true) {
                try {
                    event.wait(1 * 60 * 1000); //One minute timeout
                    if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 0) {
                        //System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                        Tradethread.interrupt();
                        Tradethread.join();
                        Commsthread.interrupt();
                        Commsthread.join();
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
}

