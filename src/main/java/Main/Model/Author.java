package Main.Model;

import lombok.Data;

@Data
public class Author implements Tuple {
    private String name;
    private String homepage;

    public Author() {
    }

    public Author(String name, String homepage) {
        this.name = name;
        this.homepage = homepage;
    }

    public Author(String[] parsedLine) {
        this.name = parsedLine[1];
        this.homepage = parsedLine[2];
    }
}