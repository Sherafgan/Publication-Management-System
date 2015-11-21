package DBMS;

import Main.Answer;
import Main.Model.Article;
import Main.Model.Author;
import Main.Model.Publication;
import Main.Model.Tuple;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

public class DBMS {
    private static final DBMS instance = new DBMS();
    private static List<Table> tables;

    // конструктор private, чтобы не было возможности создать экземпляр класса извне.
    private DBMS() {

        Kryo kryo = new Kryo();
        JavaSerializer serializer = new JavaSerializer();
        kryo.register(TreeMultimap.class, serializer);
        try {
            Input input = new Input(new FileInputStream("db.txt"));
            tables = new ArrayList<>();
            DBMS.tables = kryo.readObject(input, tables.getClass());
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Answer search(String entity, String atr, String search) {

        switch (entity) {
            case "1":
                TreeMap<String, Tuple> authorsIndex = tables.get(1).indexMap;
                Multimap<String, String> authorsNames = tables.get(1).otherMaps.get(0);
                Collection<String> authorsID = authorsNames.get(search);
                List<Author> resultAuthors = new ArrayList<>();
                if (!authorsID.isEmpty()) {
                    Author authorHeading = new Author("Name","Homepage");
                    resultAuthors.add(authorHeading);
                    Tuple tuple;
                    for (String s : authorsID) {
                        tuple = authorsIndex.get(s);
                        resultAuthors.add((Author) tuple);
                    }
                }
                return Answer.ok(dataToJson(resultAuthors));
            case "2":
                List<Publication> resultPublications = new ArrayList<>();
                List<Article> resultArticles = new ArrayList<>();
                Collection<String> publicationsID = new ArrayList<>();
                TreeMap<String, Tuple> publicationsIndex = tables.get(3).indexMap;

                if (atr.equals("Title") || atr.equals("Year")) {
                    if (atr.equals("Title")) {
                        Multimap<String, String> publicationsTitles = tables.get(3).otherMaps.get(0);
                        publicationsID = publicationsTitles.get(search);
                    }
                    if (atr.equals("Year")) {
                        Multimap<String, String> publicationsYears = tables.get(3).otherMaps.get(1);
                        publicationsID = publicationsYears.get(search);
                    }
                    if (!publicationsID.isEmpty()) {
                        Publication publicationHeading = new Publication("Title","Year","Homepage");
                        resultPublications.add(publicationHeading);
                        Tuple tuple;
                        for (String s : publicationsID) {
                            tuple = publicationsIndex.get(s);
                            resultPublications.add((Publication) tuple);
                        }
                        return Answer.ok(dataToJson(resultPublications));
                    }
                }
                if (atr.equals("Journal") || atr.equals("Month")) {
                    TreeMap<String, Tuple> articlesIndex = tables.get(0).indexMap;
                    Multimap<String, String> articlesJournals = tables.get(0).otherMaps.get(0);
                    Article articleHeading = new Article("Title","Year","URL","Journal","Month","Volume","Number");
                    resultArticles.add(articleHeading);
                    Collection<String> articlesID = articlesJournals.get(search);
                    for (String ss : articlesID) {
                        Article article = (Article) articlesIndex.get(ss);
                        Publication matchedPublication = (Publication) publicationsIndex.get(ss);
                        if (matchedPublication != null) {
                            Article joinedArticle = new Article();
                            joinedArticle.title = matchedPublication.title;
                            joinedArticle.year = matchedPublication.year;
                            joinedArticle.url = matchedPublication.url;
                            joinedArticle.journal = article.journal;
                            joinedArticle.month = article.month;
                            joinedArticle.volume = article.volume;
                            joinedArticle.number = article.number;
                            resultArticles.add(joinedArticle);
                        }
                    }
                    return Answer.ok(dataToJson(resultArticles));
                }
                switch (atr) {
                    case "Journal":
                        break;
                    case "Month":
                        break;
                    case "Publisher":
                        break;
                    case "ISBN":
                        break;
                }
        }

        return null;
    }

    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }
}
