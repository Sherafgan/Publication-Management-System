package Main.Model;

/**
 * Created by nikitaborodulin on 31/10/15.
 */
public class Article extends Publication implements Tuple {
    String journal;
    String month;
    String volume;
    String number;

    public Article(String[] attributes) {
        this.journal = attributes[0];
        this.month = attributes[1];
        this.volume = attributes[2];
        this.number = attributes[3];
    }
}
