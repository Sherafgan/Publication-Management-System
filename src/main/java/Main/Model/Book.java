package Main.Model;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class Book extends Publication implements Tuple {
    //public String id;
    public String publisher;
    public String ISBN;

    public Book() {
    }

    public Book(String id, String title, String year, String url, String publisher, String ISBN) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.url = url;
        this.publisher = publisher;
        this.ISBN = ISBN;
    }

    public Book(String[] parsedLine) {
        //this.id = parsedLine[0];
        this.publisher = parsedLine[2];
        this.ISBN = parsedLine[3];
    }
}
