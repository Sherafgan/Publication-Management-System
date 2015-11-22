package DBMS;

import Main.Model.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class FWD {
    private static final String[] namesOfFiles = new String[]{"article.csv", "author.csv", "book.csv", "publication.csv"};

    private final static int determinatorOfArticleTable = 0;
    private final static int determinatorOfAuthorTable = 1;
    private final static int determinatorOfBookTable = 2;
    private final static int determinatorOfPublicationTable = 3;

    private static long authorMaxId = 0;
    private static long publicationMaxId = 0;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Table> tables = new ArrayList<>();
        for (int i = 0; i < namesOfFiles.length; i++) {

            String csvFile = "/home/sherafgan/Desktop/csv/" + namesOfFiles[i]; //TODO change directory
            BufferedReader br;
            String line;
            String cvsSplitBy = "\\^";

            TreeMap<String, Tuple> indexMap = new TreeMap<>();

            List<Multimap<String, String>> otherMaps = new ArrayList<>();

            Table table;

            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            switch (i) {
                case determinatorOfArticleTable:
                    Multimap<String, String> journalMap = TreeMultimap.create();
                    Multimap<String, String> monthMap = TreeMultimap.create();
                    while ((line = br.readLine()) != null) {
                        String[] parsedLine = line.split(cvsSplitBy);
                        Article article = new Article(parsedLine);
                        indexMap.put(parsedLine[0], article);

                        journalMap.put(parsedLine[2], parsedLine[0]);
                        if (!parsedLine[3].equals("null")) {
                            monthMap.put(parsedLine[3], parsedLine[0]);
                        }
                    }

                    otherMaps.add(journalMap);
                    otherMaps.add(monthMap);

                    table = new Table(indexMap, otherMaps);
                    tables.add(table);
                    break;
                case determinatorOfAuthorTable:
                    Multimap<String, String> nameMap = TreeMultimap.create();

                    while ((line = br.readLine()) != null) {
                        String[] parsedLine = line.split(cvsSplitBy);
                        Author author = new Author(parsedLine);
                        long id = Long.parseLong(parsedLine[0]);
                        if (id > authorMaxId) {
                            authorMaxId = id;
                        }
                        indexMap.put(parsedLine[0], author);

                        nameMap.put(parsedLine[1], parsedLine[0]);
                    }
                    otherMaps.add(nameMap);

                    table = new Table(indexMap, otherMaps);
                    tables.add(table);
                    break;
                case determinatorOfBookTable:
                    Multimap<String, String> publisherMap = TreeMultimap.create();
                    Multimap<String, String> isbnMap = TreeMultimap.create();

                    while ((line = br.readLine()) != null) {
                        String[] parsedLine = line.split(cvsSplitBy);
                        Book book = new Book(parsedLine);
                        indexMap.put(parsedLine[0], book);

                        publisherMap.put(parsedLine[2], parsedLine[0]);
                        isbnMap.put(parsedLine[3], parsedLine[0]);
                    }
                    otherMaps.add(publisherMap);
                    otherMaps.add(isbnMap);

                    table = new Table(indexMap, otherMaps);
                    tables.add(table);
                    break;
                case determinatorOfPublicationTable:
                    Multimap<String, String> titleMap = TreeMultimap.create();
                    Multimap<String, String> yearMap = TreeMultimap.create();

                    while ((line = br.readLine()) != null) {
                        String[] parsedLine = line.split(cvsSplitBy);
                        Publication publication = new Publication(parsedLine);
                        long id = Long.parseLong(parsedLine[0]);
                        if (id > publicationMaxId) {
                            publicationMaxId = id;
                        }
                        indexMap.put(parsedLine[0], publication);

                        titleMap.put(parsedLine[2], parsedLine[0]);
                        yearMap.put(parsedLine[3], parsedLine[0]);
                    }
                    otherMaps.add(titleMap);
                    otherMaps.add(yearMap);

                    table = new Table(indexMap, otherMaps);
                    tables.add(table);
                    break;
            }
        }

        TreeMap<String, Tuple> empMap = new TreeMap<>();
        Multimap<String, String> idsMap = TreeMultimap.create();
        idsMap.put("authorMaxID", String.valueOf(authorMaxId));
        idsMap.put("publicationMaxID", String.valueOf(publicationMaxId));

        List<Multimap<String, String>> temp = new ArrayList<>();
        temp.add(idsMap);

        Table maxIDS = new Table(empMap, temp);
        tables.add(maxIDS);

        Kryo kryo = new Kryo();
        JavaSerializer serializer = new JavaSerializer();
        kryo.register(TreeMultimap.class, serializer);
        Output output = new Output(new FileOutputStream("db.dat"));
        kryo.writeObject(output, tables);
        output.close();
    }

}
