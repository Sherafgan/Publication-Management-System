package Main.Model;

import lombok.Data;

@Data
public class Author implements Tuple {
    private String name;
    private String homepage;

    public Author() {
    }

    public Author(String[] parsedLine) {
        this.name = parsedLine[1];
        this.homepage = parsedLine[2];
    }
}