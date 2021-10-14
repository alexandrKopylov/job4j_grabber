package ru.job4j.grabber.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailsPost {

    public  void  GetDetals (String url) throws IOException {

        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".msgTable");
        String jobText = null;
        String jobDate = null;
        for (Element td : row) {
            Element href = td.child(0);
            jobText = href.text();

                Element parent = td.parent();
                jobDate = parent.child(5).text();
                System.out.println(jobText + " : " + jobDate);
                System.out.println(href.attr("href"));
                System.out.println();
        }


    }

    public static void main(String[] args) throws IOException {

        String url = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        DetailsPost dp = new DetailsPost();
        dp.GetDetals(url);
    }
}
