package ru.job4j.grabber.store;

import ru.job4j.grabber.model.Post;
import ru.job4j.grabber.parse.SqlRuParse;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.quartz.AlertRabbit;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        try {
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void save(Post post) {
        try (PreparedStatement statement =
                     cnn.prepareStatement("insert into post(name, text, link, created) values (?,?,?,?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> postList = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement("select * from post")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    postList.add(new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    @Override
    public Post findById(int id) {
        Post result = null;
        try (PreparedStatement statement = cnn.prepareStatement("select * from post where id =  ? ;")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Post(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("text"),
                            resultSet.getString("link"),
                            resultSet.getTimestamp("created").toLocalDateTime()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static Properties getProperty() {
        try (InputStream in = AlertRabbit.class.getClassLoader().getResourceAsStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            return config;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Properties prop = getProperty();
        PsqlStore psqlStore = new PsqlStore(prop);
        SqlRuParse parse = new SqlRuParse(new SqlRuDateTimeParser());
        Post post1 = parse.detail("https://www.sql.ru/forum/1339483/vakansiya-oracle-razrabotchik-ot-170-k-udalenka");
        post1.setCreated(LocalDateTime.now());
        Post post2 = parse.detail("https://www.sql.ru/forum/1339524/senior-middle-dba-postgresql-ot-dataegret-udalyonnaya-rabota");
        post2.setCreated(LocalDateTime.now());
        psqlStore.save(post1);
        psqlStore.save(post2);
        psqlStore.getAll().forEach(System.out::println);
        System.out.println("-----------------------------------------------");
        System.out.println(psqlStore.findById(1));
    }
}