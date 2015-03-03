package com.cs261.output;

import com.cs261.analysis.Analyser;
import com.cs261.analysis.Node;
import com.cs261.main.Query;

import java.util.Calendar;
import java.util.List;

public class QueryPrinter {

    private Query query;
    private Analyser analyser;

    public QueryPrinter(Query query) {
        this.query = query;
        this.analyser = new Analyser(10); //Tweak this value later   <-----------           <-----------               <------------             <------------
    }

    public void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])); //year, month, date
        Query newQuery = new Query(cal.getTime(), Integer.parseInt(args[3]));
        QueryPrinter printer = new QueryPrinter(newQuery);
        printer.print();
    }

    public void print() {
        List<Node> nodes = analyser.analyse(this.query);
        System.out.println("<table><tr>");

        for (Node node : nodes) {
            for (String s : node.getContent()) {
                System.out.println("<td>" + s + "</td>");
            }
            System.out.println("</tr></table>");
        }
    }

}
