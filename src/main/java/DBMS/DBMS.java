package DBMS;

import Main.Answer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.google.common.collect.TreeMultimap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
                break;

        }
        return null;

//    public static DBMS getInstance(){
//        return instance;
//    }


    }
}
