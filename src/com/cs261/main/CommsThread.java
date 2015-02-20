package mainCode;

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



public class CommsThread implements Runnable {
    public void run() { // Threads running code.
	try {
	  System.out.println("In the comms thread");

	  DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	   //get current date time with Date()
	  Date currentdate = new Date();
	  String dateStr = dateFormat.format(currentdate);
	  boolean DoesExist = new File("commsstore" + dateStr + ".csv").exists();
	  final PrintWriter outputTrades = new PrintWriter(new FileWriter("commsstore" + dateStr + ".csv",true));
	  //Define a writer for outputting trades.

	  String hostName = "cs261.dcs.warwick.ac.uk";
	  // Declare the host for the socket.
	  int portNumber = Integer.parseInt("1720");
	  // Declare the port number to listen to.
	  final Socket echoSocket = new Socket(hostName, portNumber);
	  // Establish a socket using the chosen host and port.

	  Runtime.getRuntime().addShutdownHook(new Thread() {
	      // Code run when program is stopped via Ctrl+c
	      public void run() {
		try {
		  System.out.println("Ctrl-c caught");
		  if (outputTrades != null) {
		      outputTrades.close(); // Close link to file output.
		  }
		  if (echoSocket != null) {
		      echoSocket.close(); // Close link to socket.
		  }
		} catch (IOException IOEx){
		  IOEx.printStackTrace();
		}
	      }

	  });

	  try {
	      
		
		BufferedReader tradesocket = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		// Begin reading from socket.
		String lineinput;
		// String to hold each line read in.
		String[] lineseperated;
		// String array to separate into individual data e.g. date, buyer, seller
		if (DoesExist)
		{
		  lineinput = tradesocket.readLine(); // ignore first line
		}
		while ((lineinput = tradesocket.readLine()) != null) {
		    lineseperated = lineinput.split(",");
		    // Splits the read line into individual data.
		   for (int i=0; i < lineseperated.length; i++)
		    {
		      outputTrades.append(lineseperated[i] + ",");
		    }
		    outputTrades.append("\n");
		    // Output to csv file, note that to separate columns need to use a "," and to separate rows per println.
		}	
	      } finally { // Runs after try whether success or fail.
		if (outputTrades != null) {
		    outputTrades.close(); // Close link to file output.
		}
		if (echoSocket != null) {
		    echoSocket.close(); // Close link to socket.
		}
	      }
	    }
	  catch (IOException IOEx){
	    IOEx.printStackTrace();
	  }
      }
}
 
