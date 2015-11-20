package DBMS;

import Main.Answer;
import Main.Model.Author;
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
                Multimap<String, String> authorsName = tables.get(1).otherMaps.get(0);
                List<Author> result = new ArrayList<>();
                Collection<String> authorsID = authorsName.get(search);
                if (!authorsID.isEmpty()) {
                    Tuple tuple;
                    for (String s : authorsID) {
                        tuple = authorsIndex.get(s);
                        result.add((Author) tuple);
                    }
                }
                return Answer.ok(dataToJson(result));
            case "2":
                break;
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
