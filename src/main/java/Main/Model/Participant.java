package Main.Model;

import lombok.Data;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Data
public class Participant {
    private UUID pub_uuid;
    private String name;
    private Integer year;
    private URL homepage;
    private List<String> categories;
}