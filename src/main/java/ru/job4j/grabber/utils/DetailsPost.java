package ru.job4j.grabber.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailsPost {

    public void getDetals(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements descriptionJob = doc.select(".msgBody");
        System.out.println(descriptionJob.get(1).text());
        Elements selectAllFooter = doc.select(".msgFooter");
        String descriptionTime = selectAllFooter.get(0).text();
        System.out.println(descriptionTime.substring(0, 16));
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        DetailsPost dp = new DetailsPost();
        dp.getDetals(url);
    }
}
