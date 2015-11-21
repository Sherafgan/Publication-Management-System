package Main.Model;

import lombok.Data;

@Data
public class Publication implements Tuple {
    //private String pub_id;
    public String title;
    public String year;
    public String url;

    public Publication() {
    }

    public Publication(String title, String year, String url) {
        //this.pub_id = pub_id;
        this.title = title;
        this.year = year;
        this.url = url;
    }

    public Publication(String[] parsedLine) {
        //this.pub_id = parsedLine[0];
        this.title = parsedLine[2];
        this.year = parsedLine[3];
        this.url = parsedLine[4];
    }

}