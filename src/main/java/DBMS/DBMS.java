package DBMS;

import Main.Answer;
import Main.Model.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.io.*;
import java.util.*;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class DBMS {
    private static final DBMS instance = new DBMS();
    public static long authorMaxId = 0;
    public static long publicationMaxId = 0;
    public static Kryo kryo = new Kryo();
    public static JavaSerializer serializer = new JavaSerializer();
    public static Output output;
    public static Input input;
    private static List<Table> tables;

    private DBMS() {
    }


    public static boolean delete(String entity, String id) {
        if (entity.equals("1")) {
            TreeMap<String, Tuple> authorsIndex = tables.get(1).indexMap;
            Multimap<String, String> authorsNames = tables.get(1).otherMaps.get(0);
            Author deletedTuple = (Author)authorsIndex.remove(id);
            if (deletedTuple != null) {
                Collection authorsToSearch = authorsNames.get(deletedTuple.name); //TODO Is it necessary to use for loop? See publication deletion
                for (Object object : authorsToSearch) {
                    String idToCheck = object.toString();
                    if (idToCheck.equals(deletedTuple.id)) {
                        authorsNames.remove(deletedTuple.name,idToCheck);
                    }
                }
                tables.get(1).setIndexMap(authorsIndex);
                tables.get(1).otherMaps.set(0, authorsNames);
                save();
                return true;
            }
        }
        else {
            TreeMap<String, Tuple> articlesIndex = tables.get(0).indexMap;
            TreeMap<String, Tuple> booksIndex = tables.get(2).indexMap;
            TreeMap<String, Tuple> publicationIndex = tables.get(3).indexMap;
            Multimap<String, String> publicationTitle = tables.get(3).otherMaps.get(0);
            Multimap<String, String> publicationYear = tables.get(3).otherMaps.get(1);
            Tuple deletedTuple = publicationIndex.remove(id);
            if (deletedTuple != null) {
                if (articlesIndex.get(id) != null) { //TODO check deletion
                    Article tempArticle = (Article) deletedTuple;
                    Multimap<String, String> articlesJournal = tables.get(0).otherMaps.get(0);
                    Multimap<String, String> articlesMonth = tables.get(0).otherMaps.get(1);
                    publicationTitle.remove(tempArticle.title,id);
                    publicationYear.remove(tempArticle.year,id);
                    articlesJournal.remove(tempArticle.journal,id);
                    articlesMonth.remove(tempArticle.month,id);
                    tables.get(3).setIndexMap(publicationIndex);
                    tables.get(3).otherMaps.set(0, publicationTitle);
                    tables.get(3).otherMaps.set(1, publicationYear);
                    tables.get(0).otherMaps.set(0, articlesJournal);
                    tables.get(0).otherMaps.set(1, articlesMonth);
                } else if (booksIndex.get(id) != null) {
                    Book tempBook = (Book) deletedTuple;
                    Multimap<String, String> bookPublisher = tables.get(2).otherMaps.get(0);
                    Multimap<String, String> bookISBN = tables.get(2).otherMaps.get(1);
                    publicationTitle.remove(tempBook.title,id);
                    publicationYear.remove(tempBook.year,id);
                    bookPublisher.remove(tempBook.publisher,id);
                    bookISBN.remove(tempBook.ISBN,id);
                    tables.get(3).setIndexMap(publicationIndex);
                    tables.get(3).otherMaps.set(0, publicationTitle);
                    tables.get(3).otherMaps.set(1, publicationYear);
                    tables.get(2).otherMaps.set(0, bookPublisher);
                    tables.get(2).otherMaps.set(1, bookISBN);
                }
                save();
                return true;
            }
        }
        return false;
    }

    public static void insert(Map<String, String> map) {
        if (map.get("entity").equals("1")) {
            String name = map.get("name");
            String homepage = map.get("homepage");

            TreeMap<String, Tuple> authorsIndex = tables.get(1).indexMap;
            Multimap<String, String> maxIdsMap = tables.get(4).otherMaps.get(0);
            Multimap<String, String> authorsNames = tables.get(1).otherMaps.get(0);

            Collection maxAuthorIDraw = maxIdsMap.get("authorMaxID");
            String maxAuthorIDString = String.valueOf(Iterables.get(maxAuthorIDraw, 0));
            Long maxAuthorID = Long.parseLong(maxAuthorIDString);
            maxIdsMap.remove("authorMaxID",String.valueOf(maxAuthorID));
            maxAuthorID++;
            maxIdsMap.put("authorMaxID",String.valueOf(maxAuthorID));
            Author author = new Author(String.valueOf(maxAuthorID),name, homepage);
            authorsIndex.put(String.valueOf(maxAuthorID), author);
            authorsNames.put(name, String.valueOf(maxAuthorID));

            tables.get(1).setIndexMap(authorsIndex);
            tables.get(1).otherMaps.set(0, authorsNames);
            List<Multimap<String, String>> newMaxIdsAuthor = new ArrayList<>();
            newMaxIdsAuthor.add(maxIdsMap);
            tables.get(4).setOtherMaps(newMaxIdsAuthor);
            save();
        }
        else {
            String title = map.get("title");
            String year = map.get("year");
            TreeMap<String, Tuple> publicationIndex = tables.get(3).indexMap;
            Multimap<String, String> titleIndex = tables.get(3).otherMaps.get(0);
            Multimap<String, String> yearIndex = tables.get(3).otherMaps.get(1);
            if (map.containsKey("journal")) {
                String journal = map.get("journal");
                String month = map.get("month");
                TreeMap<String, Tuple> articlesIndex = tables.get(0).indexMap;
                Multimap<String, String> articlesJournal = tables.get(0).otherMaps.get(0);
                Multimap<String, String> articlesMonth = tables.get(0).otherMaps.get(1);
                Multimap<String, String> maxIdsMap = tables.get(4).otherMaps.get(0);
                Collection maxPublicationIDraw = maxIdsMap.get("publicationMaxID");
                String maxAuthorIDString = String.valueOf(Iterables.get(maxPublicationIDraw, 0));
                Long maxPublicationID = Long.parseLong(maxAuthorIDString);
                maxIdsMap.remove("publicationMaxID",String.valueOf(maxPublicationID));
                maxPublicationID++;
                maxIdsMap.put("publicationMaxID",String.valueOf(maxPublicationID));
                Article newArticle = new Article(String.valueOf(maxPublicationID),title,year,"null",journal,month,"null","null"); //TODO fix volume, number, url
                articlesIndex.put(String.valueOf(maxPublicationID),newArticle);
                articlesJournal.put(journal,String.valueOf(maxPublicationID));
                articlesMonth.put(month,String.valueOf(maxPublicationID));
                publicationIndex.put(String.valueOf(maxPublicationID),newArticle);
                titleIndex.put(title,String.valueOf(maxPublicationID));
                yearIndex.put(year,String.valueOf(maxPublicationID));
                tables.get(0).setIndexMap(articlesIndex);
                tables.get(0).otherMaps.set(0,articlesJournal);
                tables.get(0).otherMaps.set(1,articlesMonth);
                tables.get(3).setIndexMap(publicationIndex);
                tables.get(3).otherMaps.set(0,titleIndex);
                tables.get(3).otherMaps.set(1,yearIndex);
                save();
            }
            else {
                String publisher = map.get("publisher");
                String isbn = map.get("isbn");
                TreeMap<String, Tuple> booksIndex = tables.get(2).indexMap;
                Multimap<String, String> bookPublisher = tables.get(2).otherMaps.get(0);
                Multimap<String, String> bookISBN = tables.get(2).otherMaps.get(1);
                Multimap<String, String> maxIdsMap = tables.get(4).otherMaps.get(0);
                Collection maxPublicationIDraw = maxIdsMap.get("publicationMaxID");
                String maxAuthorIDString = String.valueOf(Iterables.get(maxPublicationIDraw, 0));
                Long maxPublicationID = Long.parseLong(maxAuthorIDString);
                maxIdsMap.remove("publicationMaxID",String.valueOf(maxPublicationID));
                maxPublicationID++;
                maxIdsMap.put("publicationMaxID",String.valueOf(maxPublicationID));
                Book newBook = new Book(String.valueOf(maxPublicationID),title,year,"null",publisher,isbn); //TODO fix volume, number, url
                booksIndex.put(String.valueOf(maxPublicationID),newBook);
                bookPublisher.put(publisher,String.valueOf(maxPublicationID));
                bookISBN.put(isbn,String.valueOf(maxPublicationID));
                publicationIndex.put(String.valueOf(maxPublicationID),newBook);
                titleIndex.put(title,String.valueOf(maxPublicationID));
                yearIndex.put(year,String.valueOf(maxPublicationID));
                tables.get(2).setIndexMap(booksIndex);
                tables.get(2).otherMaps.set(0,bookPublisher);
                tables.get(2).otherMaps.set(1,bookISBN);
                tables.get(3).setIndexMap(publicationIndex);
                tables.get(3).otherMaps.set(0,titleIndex);
                tables.get(3).otherMaps.set(1,yearIndex);
                save();
            }
        }
    }

    public static Answer search(String entity, String atr, String search) { //TODO check every attribute

        switch (entity) {
            case "1":
                TreeMap<String, Tuple> authorsIndex = tables.get(1).indexMap;
                Multimap<String, String> authorsNames = tables.get(1).otherMaps.get(0);
                Collection<String> authorsID = authorsNames.get(search);
                List<Author> resultAuthors = new ArrayList<>();
                Author authorHeading = new Author("ID","Name", "Homepage");
                resultAuthors.add(authorHeading);
                if (!authorsID.isEmpty()) {
                    Tuple tuple;
                    for (String s : authorsID) {
                        tuple = authorsIndex.get(s);
                        resultAuthors.add((Author) tuple);
                    }
                }
                return Answer.ok(dataToJson(resultAuthors));
            case "2":
                List<Publication> resultPublications = new ArrayList<>();
                Collection<String> publicationsID = new ArrayList<>();
                TreeMap<String, Tuple> publicationsIndex = tables.get(3).indexMap;
                if (atr.equals("Title") || atr.equals("Year")) {
                    Publication publicationHeading = new Publication("ID","Title", "Year", "Homepage");//TODO fix table layout for article/book displaying
                    resultPublications.add(publicationHeading);
                    if (atr.equals("Title")) {
                        Multimap<String, String> publicationsTitles = tables.get(3).otherMaps.get(0);
                        publicationsID = publicationsTitles.get(search);
                    } else if (atr.equals("Year")) {
                        Multimap<String, String> publicationsYears = tables.get(3).otherMaps.get(1);
                        publicationsID = publicationsYears.get(search);
                    }
                    if (!publicationsID.isEmpty()) {
                        Tuple tuple;
                        for (String s : publicationsID) {
                            tuple = publicationsIndex.get(s);
                            resultPublications.add((Publication) tuple);
                        }
                    }
                    return Answer.ok(dataToJson(resultPublications));
                } else if (atr.equals("Journal") || atr.equals("Month")) {
                    List<Article> resultArticles = new ArrayList<>();
                    TreeMap<String, Tuple> articlesIndex = tables.get(0).indexMap;
                    Article articleHeading = new Article("ID","Title", "Year", "URL", "Journal", "Month", "Volume", "Number");
                    resultArticles.add(articleHeading);
                    Multimap<String, String> articlesAttribute = null;
                    if (atr.equals("Journal")) {
                        articlesAttribute = tables.get(0).otherMaps.get(0);
                    } else if (atr.equals("Month")) {
                        articlesAttribute = tables.get(0).otherMaps.get(1);
                    }
                    Collection<String> articlesID = articlesAttribute.get(search);
                    for (String ss : articlesID) {
                        Article article = (Article) articlesIndex.get(ss);
                        Publication matchedPublication = (Publication) publicationsIndex.get(ss);
                        if (matchedPublication != null) {
                            Article joinedArticle = new Article();
                            joinedArticle.id = matchedPublication.id;
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
                } else if (atr.equals("Publisher") || atr.equals("ISBN")) {
                    List<Book> resultBooks = new ArrayList<>();
                    TreeMap<String, Tuple> booksIndex = tables.get(2).indexMap;
                    Book bookHeading = new Book("ID","Title", "Year", "URL", "Publisher", "ISBN");
                    resultBooks.add(bookHeading);
                    Multimap<String, String> booksAttribute = null;
                    if (atr.equals("Publisher")) {
                        booksAttribute = tables.get(2).otherMaps.get(0);
                    } else if (atr.equals("ISBN")) {
                        booksAttribute = tables.get(2).otherMaps.get(1);
                    }
                    Collection<String> booksID = booksAttribute.get(search);
                    for (String ss : booksID) {
                        Book book = (Book) booksIndex.get(ss);
                        Publication matchedPublication = (Publication) publicationsIndex.get(ss);
                        if (matchedPublication != null) {
                            Book joinedBook = new Book();
                            joinedBook.id = matchedPublication.id;
                            joinedBook.title = matchedPublication.title;
                            joinedBook.year = matchedPublication.year;
                            joinedBook.url = matchedPublication.url;
                            joinedBook.publisher = book.publisher;
                            joinedBook.ISBN = book.ISBN;
                            resultBooks.add(joinedBook);
                        }
                    }
                    return Answer.ok(dataToJson(resultBooks));
                }
        }
        return null;
    }

    public static void save() {
        try {
            output = new Output(new FileOutputStream("db.dat"));
            kryo.writeObject(output, tables);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public static void load() {
        kryo.register(TreeMultimap.class, serializer);
        try {
            input = new Input(new FileInputStream("db.dat"));
            tables = new ArrayList<>();
            tables = kryo.readObject(input, tables.getClass());
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
