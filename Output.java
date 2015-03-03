import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {
    public static void main(String[] args) { 
    	outputUsingPHP();
    	outputToCSV();
    }
    
    public static void outputToCSV(){ // this example just outputs trades to a csv file
    	class Trade{ // dummy class that could be used to make a trade into an object
    		int time;
    		String buyer;
    		String seller;
    		float price;
    		int size;
    		String symbol;
    		String bid;   		
    		public Trade(int time, String buyer, String seller, float price, int size, String symbol, String bid){
    			this.time = time;
    			this.buyer = buyer;
    			this.seller = seller;
    			this.price = price;
    			this.size = size;
    			this.symbol = symbol;
    			this.bid = bid;
    		}
    	}
    	Trade[] allTrades= new Trade[5];
        for (int i =0; i<5; i++)
        	allTrades[i] = new Trade(i,"Buyer"+i, "Seller"+i,(float)i, i, "Symbol"+i, "bid"+i);
        
        try {
        	final PrintWriter output = new PrintWriter(new FileWriter("Output.csv", true));
	        for (int i =0; i<5; i++){
	        	output.append(allTrades[i].time+",");
	        	output.append(allTrades[i].buyer+",");
	        	output.append(allTrades[i].seller+",");
	        	output.append(allTrades[i].price+",");
	        	output.append(allTrades[i].size+",");
	        	output.append(allTrades[i].symbol+",");
	        	output.append(allTrades[i].bid+",");
	        	output.append("\n");
	        }
	        output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void outputUsingPHP(){// example to show how we could run the anlysis from the php
    	// the java can print out directly to the webpage from here on using system.out.print
    	// this means we can use it similar to how u would with using echo within php
    	/* This is the php we'd need to run to call the script 
    		<?php 
			exec('java Output some_value',$output);
			echo $output[0];
		?>
	*/
    	
    	/* do all our anlysis and get the fields we are now interested in*/
    	
    	//not sure how the fields will be handled within java but basically just print them out with formatting
    	System.out.print("<table>");// can write html directly from java
    	
    	
    	class Trade{ // dummy class that could be used to make a trade into an object
    		int time;
    		String buyer;
    		String seller;
    		float price;
    		int size;
    		String symbol;
    		String bid;   		
    		public Trade(int time, String buyer, String seller, float price, int size, String symbol, String bid){
    			this.time = time;
    			this.buyer = buyer;
    			this.seller = seller;
    			this.price = price;
    			this.size = size;
    			this.symbol = symbol;
    			this.bid = bid;
    		}
    	}
    	Trade[] allTrades= new Trade[5];
        for (int i =0; i<5; i++)
        	allTrades[i] = new Trade(i,"Buyer"+i, "Seller"+i,(float)i, i, "Symbol"+i, "bid"+i);
        
        for (int i =0; i<5; i++){
        	System.out.print("<tr>");
        	//we can print out whatever collums we want
        	System.out.print("<td>"+allTrades[i].time+"</td>");
        	System.out.print("<td>"+allTrades[i].buyer+"</td>");
        	System.out.print("<td>"+allTrades[i].seller+"</td>");
        	System.out.print("<td>"+allTrades[i].price+"</td>");
        	System.out.print("<td>"+allTrades[i].size+"</td>");
        	System.out.print("<td>"+allTrades[i].symbol+"</td>");
        	System.out.print("<td>"+allTrades[i].bid+"</td>");
        	System.out.print("</tr>");
        }
        System.out.print("/<table>");
    	
    }
}
