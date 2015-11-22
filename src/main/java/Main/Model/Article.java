package Main.Model;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class Article extends Publication implements Tuple {
    public String journal;
    public String month;
    public String volume;
    public String number;

    public Article() {
    }

    public Article(String id, String title, String year, String url, String journal, String month, String volume, String number) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.url = url;
        this.journal = journal;
        this.month = month;
        this.volume = volume;
        this.number = number;
    }

    public Article(String[] attributes) {
        //this.id = attributes[0];
        this.journal = attributes[2];
        this.month = attributes[3];
        this.volume = attributes[4];
        this.number = attributes[5];
    }
}
