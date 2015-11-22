package Main.Model;

/**
 * Created by nikitaborodulin on 01/11/15.
 */
public class Book extends Publication implements Tuple {
    public String publisher;
    public String ISBN;

    public Book() {
    }

    public Book(String title, String year, String url, String publisher, String ISBN) {
        this.title = title;
        this.year = year;
        this.url = url;
        this.publisher = publisher;
        this.ISBN = ISBN;
    }

    public Book(String[] parsedLine) {
        this.publisher = parsedLine[2];
        this.ISBN = parsedLine[3];
    }
}
