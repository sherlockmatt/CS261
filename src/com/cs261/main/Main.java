package com.cs261.main;

import com.cs261.input.CommsThread;
import com.cs261.input.TradesThread;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class Main {
// Implements runnable to allow the change of threads run code.

    public static void main(String[] args) throws IOException {

        final Object event = new Object();

        System.out.println("Welcome to the DBA server!");
        System.out.println("No more output will be generated here, unless an error occurs.");
        System.out.println("The program will continue until stopped with ^C.");

        while (true) {
            //get current date time with Date()
            Date currentdate = new Date();
            Date cutoffdate = new Date(currentdate.getTime() - 14 * 24 * 3600 * 1000);

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
                    if (fileDate.before(cutoffdate)) {
                        if (!file.delete()) {
                            throw new IOException("Could not delete file " + file.getName());
                        }
                    }
                }
            }

            Thread Tradethread = new Thread(new TradesThread()); // Creates new thread.
            Thread Commsthread = new Thread(new CommsThread()); // Creates new thread.
            Tradethread.start(); // Runs new thread.
            Commsthread.start(); // Runs new thread.

            synchronized (event) {
                Calendar cal = Calendar.getInstance();
                while (cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) <= 30) { //This loop waits until after 00:30, so that it doesn't start-stop-start-stop etc
                    try {
                        event.wait(5 * 60 * 1000); //Five minute timeout
                        cal = Calendar.getInstance();
                    } catch (InterruptedException e) {
                        returnThreads(Tradethread, Commsthread);
                        return;
                    }
                }
                while (cal.get(Calendar.HOUR_OF_DAY) > 0 || cal.get(Calendar.MINUTE) > 30) { //Shut down just after 00:00
                    try {
                        event.wait(1 * 60 * 1000); //One minute timeout
                        cal = Calendar.getInstance();
                    } catch (InterruptedException e) {
                        returnThreads(Tradethread, Commsthread);
                        return;
                    }
                }
            }
            if (!returnThreads(Tradethread, Commsthread)) return;
        }
    }

    private static boolean returnThreads(Thread t, Thread c) {
        try {
            t.join(10000); //10s timeout
            c.join(10000); //10s timeout
            if (t.isAlive()) {
                t.interrupt();
                t.join();
            }
            if (c.isAlive()) {
                c.interrupt();
                c.join();
            }
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }
}

