package Main.Model;

import lombok.Data;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
@Data
public class Publication implements Tuple {
    public String title;
    public String year;
    public String url;

    public Publication() {
    }

    public Publication(String title, String year, String url) {
        this.title = title;
        this.year = year;
        this.url = url;
    }

    public Publication(String[] parsedLine) {
        this.title = parsedLine[2];
        this.year = parsedLine[3];
        this.url = parsedLine[4];
    }

}