package Main.handlers;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikitaborodulin on 30/10/15.
 */
public class additionHandler implements Route {

    protected void processImpl(Map<String, String> urlParams) {
        String entity;
        ArrayList<String> values = new ArrayList<String>();
        entity = urlParams.get("entity");
        if (entity.equals("1")) {
            values.add(urlParams.get("name"));
        } else {
            values.add(urlParams.get("title"));
            values.add(urlParams.get("year"));
            values.add(urlParams.get("journal"));
            values.add(urlParams.get("month"));
            values.add(urlParams.get("publisher"));
            values.add(urlParams.get("isbn"));
        }

    }

    public final void process(Map<String, String> urlParams) {
        processImpl(urlParams);
    }

    public Object handle(Request request, Response response) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            Map<String, String> m = request.params();
            String entity = request.queryParams("entity");
            String entity2 = request.params("entity");
            map.put("entity", entity);
            if (entity.equals("1")) {
                String name = request.queryParams("name");
                map.put("name", name);
            } else {
                String title = request.queryParams("title");
                String year = request.queryParams("year");
                String journal = request.queryParams("journal");
                String month = request.queryParams("month");
                String publisher = request.queryParams("publisher");
                String isbn = request.queryParams("isbn");
                if (!journal.isEmpty() && !publisher.isEmpty() || !journal.isEmpty() && !isbn.isEmpty() || !month.isEmpty() && !publisher.isEmpty() || !month.isEmpty() && !isbn.isEmpty()) {
                    map.put("title", title);
                    map.put("year", year);
                    map.put("journal", journal);
                    map.put("month", month);
                    map.put("publisher", publisher);
                    map.put("isbn", isbn);
                } else {
                    return null;
                }
            }
            Map<String, String> urlParams = Collections.unmodifiableMap(map);
            process(urlParams);
            response.status(200);
            response.body("OK");
            return response;
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return e.getMessage();
        }

    }
}
