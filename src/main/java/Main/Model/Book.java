package Main.Model;

/**
 * Created by nikitaborodulin on 01/11/15.
 */
public class Book extends Publication implements Tuple {
    String publisher;
    String ISBN;

    public Book(String[] parsedLine) {
        this.publisher = parsedLine[1];
        this.ISBN = parsedLine[2];
        this.url = parsedLine[3];
    }
}
