package Main.handlers;

import Main.Answer;
import Main.Model.Model;
import Main.Model.Participant;
import Main.Model.Publication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by nikitaborodulin on 30/10/15.
 */
public class GetSinglePublicationHandler implements Route {

    protected Model model;


    public GetSinglePublicationHandler(Model model) {
        this.model = model;
    }

    protected Answer processImpl(Map<String, String> urlParams) {
//        if (!urlParams.containsKey(":title")) {
//            throw new IllegalArgumentException();
//        }
        String value, entity, attribute;

        try {
            //st = UUID.fromString(urlParams.get(":journal"));
            entity = urlParams.get("entity");
            attribute = urlParams.get("atr");
            value = urlParams.get("search");
        } catch (IllegalArgumentException e) {
            return new Answer(404);
        }
        switch (entity) {
            case "1":
                List<Participant> authors = model.getParticipantsOn(attribute, value);
                return Answer.ok(dataToJson(authors));
            case "2":
                List<Publication> publications = model.getPublicationsOn(attribute, value);
                return Answer.ok(dataToJson(publications));
            default:
                return new Answer(404);
        }
    }

    public final Answer process(Map<String, String> urlParams) {
        return processImpl(urlParams);
    }

    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException from a StringWriter?");
        }
    }

    public Object handle(Request request, Response response) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            //Map<String, String> map = new HashMap<String, String>();
            String entity = request.queryParams("entity");
            String atr = request.queryParams("atr");
            String search = request.queryParams("search");
            //Map<String, String> urlParams = request.params();
            Map<String, String> map = new HashMap<>();
            map.put("entity",entity);
            map.put("atr",atr);
            map.put("search",search);
            Map<String, String> urlParams = Collections.unmodifiableMap(map);
            Answer answer = process(urlParams);
            response.status(answer.getCode());
            response.type("application/json");
            response.body(answer.getBody());
            return answer.getBody();
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return e.getMessage();
        }

    }
}
