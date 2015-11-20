package DBMS;

import Main.Answer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.google.common.collect.Multimap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class DBMS {
    private static final DBMS instance = new DBMS();
    private static Multimap publications;

    // конструктор private, чтобы не было возможности создать экземпляр класса извне.
    private DBMS() {
        List tables = new ArrayList<>();

        Kryo kryo = new Kryo();
        try {
            Input input = new Input(new FileInputStream("file.txt"));
            tables = kryo.readObject(input, tables.getClass());
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Answer search(String entity, String atr, String search) {
        //TODO choose the right map, get objects from the table, to create json answer
        return null;
    }


//    public static DBMS getInstance(){
//        return instance;
//    }


}
