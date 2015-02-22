package com.cs261.main;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.lang.Runnable;
import sun.misc.SignalHandler;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
// This code takes the input stream and then outputs it to a csv file. However now does it within a thread so other code may be used. Also it will now run until an interrupt is used (Ctrl-C).


public class Main {
// Implements runnable to allow the change of threads run code.

    

    public static void main(String[] args) throws IOException {
    	
  	  DateFormat dateFormatForCheck = new SimpleDateFormat("yyyyMMdd");
	   //get current date time with Date()
	  Date currentdate = new Date();
	  Date cutoffdate = new Date(currentdate.getTime() - 15 * 24 * 3600 * 1000);
	  String dateStr = dateFormatForCheck.format(cutoffdate);
	  System.out.println("Cut off date " + dateStr);
	  
	  File TooOldTrade = new File(dateStr + "tradesstore" + ".csv");
	  System.out.println("Searching for " + dateStr + "tradesstore.csv");
	  File TooOldComm = new File(dateStr + "commsstore" + ".csv");
	  System.out.println("Searching for " + dateStr + "commsstore.csv");
	  boolean DoesExistTrade = TooOldTrade.exists();
	  boolean DoesExistComm = TooOldComm.exists();
	  
	  if (DoesExistTrade)
		{
		  TooOldTrade.delete();
		  System.out.println("Deleting " + dateStr + "tradesstore.csv");
		}
	  if (DoesExistComm)
		{
		  TooOldComm.delete();
		  System.out.println("Deleting " + dateStr + "commsstore.csv");
		}
	  
	  
      Thread Tradethread = new Thread(new TradesThread()); // Creates new thread.
      Thread Commsthread = new Thread(new CommsThread()); // Creates new thread.
      System.out.println("Should start the thread");
      Tradethread.start(); // Runs new thread.
      Commsthread.start(); // Runs new thread.
      System.out.println("Should occur before thread finishes thread");
      
	    
    }
}

