package Main.Model;

import lombok.Data;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class Publication {
    private UUID pub_uuid;
    private String title;
    private Integer year;
    private URL URL;
    private List<String> categories;
}