package Main;

import com.google.common.collect.Multimap;

/**
 * Created by nikitaborodulin on 18/11/15.
 */
public class DBMS {
    private static final DBMS instance = new DBMS();
    private static Multimap publications;

    // конструктор private, чтобы не было возможности создать экземпляр класса извне.
    private DBMS(){
        //TODO deserialize data to trees
    }

    public static Answer search(String entity, String atr, String search) {
        //TODO choose the right map, get objects from the table, to create json answer
        return null;
    }


//    public static DBMS getInstance(){
//        return instance;
//    }



}
