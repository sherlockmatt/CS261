package com.cs261.main;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.lang.Runnable;
import sun.misc.SignalHandler;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class Main {
// Implements runnable to allow the chamge of threads run code.

    

    public static void main(String[] args) throws IOException {
      Thread Tradethread = new Thread(new TradesThread()); // Creates new thread.
      Thread Commsthread = new Thread(new CommsThread()); // Creates new thread.
      System.out.println("Should start the thread");
      Tradethread.start(); // Runs new thread.
      Commsthread.start(); // Runs new thread.
      System.out.println("Should occur before thread finishes thread");
      
	    
    }
}
