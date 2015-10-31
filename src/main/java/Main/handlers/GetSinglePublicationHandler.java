package Main.handlers;

import Main.Answer;
import Main.Model.Model;
import Main.Model.Publication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by nikitaborodulin on 30/10/15.
 */
public class GetSinglePublicationHandler implements Route {

    protected Model model;
    protected String attribute;
    protected String value;

    public GetSinglePublicationHandler(Model model) {
        this.model = model;
    }

    protected Answer processImpl(Map<String, String> urlParams) {
        if (!urlParams.containsKey(":title")) {
            throw new IllegalArgumentException();
        }
        String string;
        try {
            //st = UUID.fromString(urlParams.get(":journal"));
            string = urlParams.get((":title"));
        } catch (IllegalArgumentException e) {
            return new Answer(404);
        }

        Optional<Publication> publ = model.getPublicationsOn(string);
        if (!publ.isPresent()) {
            return new Answer(404);
        }
        return Answer.ok(dataToJson(publ.get()));
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
            Map<String, String> urlParams = request.params();
            //Map<String, String> urlParams = Collections.unmodifiableMap(map);
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
