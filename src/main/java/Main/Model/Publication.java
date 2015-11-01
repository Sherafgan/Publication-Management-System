package Main.Model;

import lombok.Data;

@Data
public class Publication {
    private int pub_id;
    private String title;
    private String year;
    private String url;
}