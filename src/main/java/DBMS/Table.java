package DBMS;

import Main.Model.Tuple;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.TreeMap;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class Table {
    public TreeMap<String, Tuple> indexMap;
    public List<Multimap<String, String>> otherMaps;

    public Table(TreeMap<String, Tuple> indexMap, List<Multimap<String, String>> otherMaps) {
        this.indexMap = indexMap;
        this.otherMaps = otherMaps;
    }

    public Table() {
    }

    public void setIndexMap(TreeMap<String, Tuple> indexMap) {
        this.indexMap = indexMap;
    }

    public void setOtherMaps(List<Multimap<String, String>> otherMaps) {
        this.otherMaps = otherMaps;
    }
}
