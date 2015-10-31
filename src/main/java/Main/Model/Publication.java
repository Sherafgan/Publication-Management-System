package Main.Model;

import lombok.Data;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Publication {
    private int pub_id;
    private String title;
    private String year;
    private String url;
}