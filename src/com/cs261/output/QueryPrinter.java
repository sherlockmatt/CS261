package com.cs261.output;

import com.cs261.main.Reference;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

public class QueryPrinter {

    public static void main(String[] args) { //Call this like "java -jar class type year month date time" N.B. time should be the hour only i.e. 17
        String type = args[0];
        String year = args[1];
        String month = args[2];
        String date = args[3];
        String time = args[4];
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(date));
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/" + year + month + date + type.toLowerCase() + "store.csv"));
            String[] headers = br.readLine().split(",");
            int[] widths = type.equals("Trades") ? Reference.WIDTHS_TRADES : Reference.WIDTHS_COMMS;
            int i = 0;
            System.out.println("<table id='comms' width=100%>");
            System.out.print("<tr>");
            for (String s : headers) {
                System.out.print("<th width='" + widths[i++] + "%;'>" + s + "</th>");
            }
            i = 0;
            System.out.println("</tr>");
            String line;
            while ((line = br.readLine()) != null) {
                if (line.split(",")[0].substring(11,13).equals(time)) {
                    System.out.print("<tr>");
                    for (String s : line.split(",")) {
                        System.out.print("<td width='" + widths[i++] + "%;'>" + s + "</td>");
                    }
                    i = 0;
                    System.out.println("</tr>");
                }
            }
            System.out.println("</table>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
