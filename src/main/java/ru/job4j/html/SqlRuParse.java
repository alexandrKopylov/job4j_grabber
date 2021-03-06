package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {

        for (int i = 1; i < 6; i++) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = doc.select(".postslisttopic");
            String jobText = null;
            String jobDate = null;
            for (Element td : row) {
                Element href = td.child(0);
                jobText = href.text();
                if (jobText.toLowerCase().contains("java")) {
                    Element parent = td.parent();
                    jobDate = parent.child(5).text();
                    System.out.println(jobText + " : " + jobDate);
                    System.out.println(href.attr("href"));
                    System.out.println();
                }
            }
        }
    }
}