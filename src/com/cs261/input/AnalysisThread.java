package com.cs261.input;

import com.cs261.analysis.Analyser;
import com.cs261.main.Query;

import java.io.*;
import java.util.Calendar;

public class AnalysisThread implements Runnable {
    public void run() { // Threads running code.

        System.out.println("In the analysis thread");

        final Object event = new Object();
        final Analyser analyser = new Analyser(5); //Tweak this value later   <-----------           <-----------               <------------             <------------

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
                try {
                    File[] queries = new File("queries").listFiles(new FilenameFilter() {
                        @Override
                        public boolean accept(File file, String s) {
                            return s.matches(".+-input\\.csv$");
                        }
                    });
                    if (queries.length > 0) {
                        Query query;
                        String uuid = queries[0].getName().split("-input")[0];
                        BufferedReader br = new BufferedReader(new FileReader(queries[0]));
                        try {
                            String[] fields = br.readLine().split(",");
                            Calendar cal = Calendar.getInstance();
                            cal.set(Integer.parseInt(fields[0].substring(0, 4)), Integer.parseInt(fields[0].substring(4, 6)) - 1, Integer.parseInt(fields[0].substring(6, 8)));
                            query = new Query(uuid, cal.getTime(), Integer.parseInt(fields[1]));
                            if (queries[0].delete()) {
                                analyser.analyse(query);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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
