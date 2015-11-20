package Main.sql2omodel;

import Main.Model.Article;
import Main.Model.Author;
import Main.Model.Model;
import Main.Model.Publication;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

public class Sql2oModel implements Model {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public void addition(String entity, ArrayList<String> values) {
        String sql;
        String name = values.get(0);
        if (entity.equals("1")) {
            sql = "INSERT INTO author (name) VALUES (':name');";
            try (Connection conn = sql2o.open()) {
                conn.createQuery(sql)
                        .addParameter("name", name)
                        .executeUpdate();
            }
        } else {
            sql = "INSERT INTO publication VALUES (':title',':year',':journal',':month',':publisher',':ISBN')";
            try (Connection conn = sql2o.open()) {
                conn.createQuery(sql)
                        .addParameter("title", values.get(0))
                        .addParameter("year", values.get(0))
                        .addParameter("journal", values.get(0))
                        .addParameter("month", values.get(0))
                        .addParameter("publisher", values.get(0))
                        .addParameter("ISBN", values.get(0))
                        .executeUpdate();
            }
        }
    }

    public List<Publication> getBooksOn(String attribute, String value) {
        String sql;
        if (attribute.equals("Publisher")) {
            sql = "SELECT pub_id, title, year, book.url, publisher, isbn " +
                    "FROM book JOIN publication ON book.key = publication.pub_key " +
                    "WHERE publisher = :target " +
                    "limit 100";
        } else {
            sql = "SELECT pub_id, title, year, book.url, publisher, isbn " +
                    "FROM book JOIN publication ON book.key = publication.pub_key " +
                    "WHERE month = :target " +
                    "limit 100";
        }
        try (Connection conn = sql2o.open()) {
            List<Main.Model.Book> books = conn.createQuery(sql)
                    .addParameter("target", value)
                    .executeAndFetch(Main.Model.Book.class);
            return new ArrayList<>(books);
        }
    }

    public List<Publication> getArticlesOn(String attribute, String value) {
        String sql;
        if (attribute.equals("Journal")) {
            sql = "SELECT pub_id, title, year, article.url, journal, month, volume, number " +
                    "FROM article JOIN publication ON article.key = publication.pub_key " +
                    "WHERE journal = :target " +
                    "limit 100";
        } else {
            sql = "SELECT pub_id, title, year, article.url, journal, month, volume, number " +
                    "FROM article JOIN publication ON article.key = publication.pub_key " +
                    "WHERE month = :target " +
                    "limit 100";
        }
        try (Connection conn = sql2o.open()) {
            List<Article> articles = conn.createQuery(sql)
                    .addParameter("target", value)
                    .executeAndFetch(Article.class);
            return new ArrayList<>(articles);
        }
    }

    @Override
    public List<Publication> getPublicationsOn(String attribute, String value) {
        String sql;
        switch (attribute) {
            case "Title":
                sql = "SELECT pub_id, title, year, url " +
                        "FROM publication " +
                        "WHERE title = :target " +
                        "limit 100";

                break;
            case "Year":
                sql = "SELECT pub_id, title, year, url " +
                        "FROM  publication " +
                        "WHERE year = :target " +
                        "limit 100";
                break;
            case "Journal":
                return getArticlesOn(attribute, value);
            case "Month":
                return getArticlesOn(attribute, value);
            case "Publisher":
                return getBooksOn(attribute, value);
            case "ISBN":
                return getBooksOn(attribute, value);
            default:
                sql = "null";
                break;
        }
        try (Connection conn = sql2o.open()) {
            List<Publication> publications = conn.createQuery(sql)
                    .addParameter("target", value)
                    .executeAndFetch(Publication.class);
            return publications;
        }
    }

    @Override
    public List<Author> getParticipantsOn(String attribute, String value) {
        String sql =
                "SELECT name, homepage " +
                        "FROM participant " +
                        "WHERE name = :targetName " +
                        "limit 100";
        try (Connection conn = sql2o.open()) {
            if (attribute.equals("1")) {
                List<Author> authors = conn.createQuery(sql)
                        .addParameter("targetName", value)
                        .executeAndFetch(Author.class);
                return authors;
            } else
                return null;
        }
    }
}