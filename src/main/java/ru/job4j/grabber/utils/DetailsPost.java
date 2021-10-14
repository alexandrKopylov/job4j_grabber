package ru.job4j.grabber.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailsPost {

    public void getDetals(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements descriptionJob = doc.select("#content-wrapper-forum > table:nth-child(3) > tbody > tr:nth-child(2) > td:nth-child(2)");
        System.out.println(descriptionJob.text());
        Elements timeCreatedDescription = doc.select("#content-wrapper-forum > table:nth-child(3) > tbody > tr:nth-child(3) > td");
        String str = timeCreatedDescription.text();
        str = str.substring(0, 16);
        System.out.println(str);
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        DetailsPost dp = new DetailsPost();
        dp.getDetals(url);

    }
}
