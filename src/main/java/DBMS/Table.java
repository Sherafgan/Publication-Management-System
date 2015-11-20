package DBMS;

import Main.Model.Tuple;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.TreeMap;

/**
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class Table {
    private TreeMap<String, Tuple> indexMap;
    private List<Multimap<String, String>> otherMaps;

    public Table(TreeMap<String, Tuple> indexMap, List<Multimap<String, String>> otherMaps) {
        this.indexMap = indexMap;
        this.otherMaps = otherMaps;
    }
}
