package com.cs261.input;

public class AnalysisThread implements Runnable {
    public void run() { // Threads running code.

        System.out.println("In the analysis thread");

        final Object event = new Object();

        Runtime.getRuntime().addShutdownHook(new Thread("Shutdown thread") {
            // Code run when program is stopped via Ctrl+c
            public void run() {
                System.out.println("Ctrl-c caught");
                //Tidy up if we need to
                synchronized (event) {
                    event.notifyAll();
                }
            }

        });

        synchronized (event) {
            while (true) {
                //Scan relevant directory for new queries

                //if (new queries)
                //Digest first query

                //Delete query file

                //Send query to analysis

                try {
                    event.wait(5000); //5sec, although not thought about this value much
                } catch (InterruptedException e) {
                    System.out.println("Analysis thread stopping...");
                    break;
                }
            }
        }
    }
}
