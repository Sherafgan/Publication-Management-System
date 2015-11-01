package Main.Model;

import java.util.ArrayList;
import java.util.List;

public interface Model {

    void addition(String entity, ArrayList<String> attributes);

    List<Publication> getPublicationsOn(String attribute, String value);

    List<Participant> getParticipantsOn(String attribute, String value);
}