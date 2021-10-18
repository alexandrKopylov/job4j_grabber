package ru.job4j.grabber.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }


    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> postList = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        String jobText = null;
        String jobDate = null;
        String jobHref = null;
        for (Element td : row) {
            Element href = td.child(0);
            jobText = href.text();
            Element parent = td.parent();
            jobDate = parent.child(5).text();
            jobHref = href.attr("href");
        }
        Post post = detail(jobHref);
        post.setTitle(jobText);
        post.setCreated(dateTimeParser.parse(jobDate));
        post.setLink(jobHref);
        postList.add(post);
        return postList;
    }

    @Override
    public Post detail(String link) throws IOException {
        Post post = new Post();
        Document doc = Jsoup.connect(link).get();
        Elements descriptionJob = doc.select(".msgBody");
        // post.setDescription(descriptionJob.get(1).text());
        Elements selectAllFooter = doc.select(".msgFooter");
        String descriptionTime = selectAllFooter.get(0).text();
        post.setUpdate(dateTimeParser.parse(descriptionTime.substring(0, 16)));
        return post;
    }

    public List<Post> parseHTML(String sait, int countPages) throws IOException {
        List<Post> postList = new ArrayList<>();
        for (int i = 1; i <= countPages; i++) {
            postList.addAll(list(sait + "/" + i));
        }
        return postList;
    }

    public static void main(String[] args) throws IOException {
        SqlRuDateTimeParser sdtp = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(sdtp);
        List<Post> postList = sqlRuParse.parseHTML("https://www.sql.ru/forum/job-offers", 1);
        for (Post post : postList) {
            System.out.println(post);
        }
    }
}
