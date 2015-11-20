package Main.Model;

import lombok.Data;

@Data
public class Publication implements Tuple {
    private String pub_id;
    protected String title;
    protected String year;
    protected String url;

    public Publication(String[] parsedLine) {
        this.pub_id = parsedLine[0];
        this.title = parsedLine[2];
        this.year = parsedLine[3];
        this.url = parsedLine[4];
    }

    public Publication() {
    }
}