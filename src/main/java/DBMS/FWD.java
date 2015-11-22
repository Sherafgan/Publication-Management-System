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
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class FWD {
    private static final String[] namesOfFiles = new String[]{"article.csv", "author.csv", "book.csv", "publication.csv"};

    private final static int determinatorOfArticleTable = 0;
    private final static int determinatorOfAuthorTable = 1;
    private final static int determinatorOfBookTable = 2;
    private final static int determinatorOfPublicationTable = 3;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Table> tables = new ArrayList<>();
        int j;
        for (int i = 0; i < namesOfFiles.length; i++) {

            String csvFile = "/Users/nikitaborodulin/Desktop/csv/" + namesOfFiles[i];
            BufferedReader br;
            String line;
            String cvsSplitBy = "\\^";

            TreeMap<String, Tuple> indexMap = new TreeMap<>();

            List<Multimap<String, String>> otherMaps = new ArrayList<>();

            Table table;

            br = new BufferedReader(new FileReader(csvFile));
            br.readLine();
            j = 0;
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
                        j++;
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
                        indexMap.put(parsedLine[0], author);

                        nameMap.put(parsedLine[1], parsedLine[0]);
                        j++;
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
                        j++;
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
                        indexMap.put(parsedLine[0], publication);

                        titleMap.put(parsedLine[2], parsedLine[0]);
                        yearMap.put(parsedLine[3], parsedLine[0]);
                        j++;
                    }
                    otherMaps.add(titleMap);
                    otherMaps.add(yearMap);

                    table = new Table(indexMap, otherMaps);
                    tables.add(table);
                    break;
            }
        }

        Kryo kryo = new Kryo();
        JavaSerializer serializer = new JavaSerializer();
        kryo.register(TreeMultimap.class, serializer);
        Output output = new Output(new FileOutputStream("db.txt"));
        kryo.writeObject(output, tables);
        output.close();

        // Debug
//        Input input = new Input(new FileInputStream("db.txt"));
//        List<Table> new_tables = new ArrayList<>();
//        new_tables = kryo.readObject(input, tables.getClass());
//        input.close();
    }

}
