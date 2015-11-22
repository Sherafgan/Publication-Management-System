package Main.Model;

import lombok.Data;

@Data
public class Author implements Tuple {
    public String id;
    public String name;
    public String homepage;

    public Author() {
    }

    public Author(String id, String name, String homepage) {
        this.id = id;
        this.name = name;
        this.homepage = homepage;
    }

    public Author(String[] parsedLine) {
        this.id = parsedLine[0];
        this.name = parsedLine[1];
        this.homepage = parsedLine[2];
    }
}