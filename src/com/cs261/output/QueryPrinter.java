package com.cs261.output;

import com.cs261.analysis.Analyser;
import com.cs261.analysis.Node;
import com.cs261.main.Query;
import com.cs261.main.Reference;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class QueryPrinter {

    private Query query;
    private Analyser analyser;
    private static String[] headers;

    public QueryPrinter(Query query) {
        this.query = query;
        this.analyser = new Analyser(query.getType(), Reference.RADIUS);
    }

    public static void main(String[] args) { //Call this like "java -jar class type year month date time" N.B. time should be the hour only i.e. 17
        int year = Integer.parseInt(args[1]);
        int month = Integer.parseInt(args[2]);
        int date = Integer.parseInt(args[3]);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, date);
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/" + args[1] + args[2] + args[3] + args[0].toLowerCase() + "store.csv"));
            headers = br.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Query newQuery = new Query(args[0], headers, cal.getTime(), Integer.parseInt(args[4]));
        QueryPrinter printer = new QueryPrinter(newQuery);
        printer.print();
    }

    public void print() {
        int[] widths = query.getType().equals("Trades") ? Reference.WIDTHS_TRADES : Reference.WIDTHS_COMMS;

        int i = 0;

        List<Node> nodes = analyser.analyse(this.query);
        System.out.println("<table id='comms' width=100%>");

        System.out.print("<tr>");
        for (String s : query.getHeaders()) {
            System.out.print("<th width='" + widths[i++] + "%;'>" + s + "</th>");
        }
        i = 0;
        System.out.println("</tr>");

        for (Node node : nodes) {
            System.out.print("<tr>");
            for (String s : node.getContent()) {
                System.out.print("<td width='" + widths[i++] + "%;'>" + s + "</td>");
            }
            i = 0;
            System.out.println("</tr>");
        }
        System.out.println("</table>");
    }

}
