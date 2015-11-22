package Main.handlers;

import DBMS.DBMS;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class additionHandler implements Route {


    public Object handle(Request request, Response response) throws Exception {
        try {
            Map<String, String> map = new HashMap<>();
            String entity = request.queryParams("entity");
            map.put("entity", entity);
            if (entity.equals("1")) {
                String name = request.queryParams("name");
                String homepage = request.queryParams("homepageURL");
                map.put("name", name);
                map.put("homepage", homepage);
            } else {
                String title = request.queryParams("title");
                String year = request.queryParams("year");
                String journal = request.queryParams("journal");
                String month = request.queryParams("month");
                String publisher = request.queryParams("publisher");
                String isbn = request.queryParams("isbn");

                map.put("title", title);
                map.put("year", year);
                if (!journal.isEmpty() && !month.isEmpty() && publisher.isEmpty() && isbn.isEmpty()) {
                    map.put("journal", journal);
                    map.put("month", month);
                }
                else if(journal.isEmpty() && month.isEmpty() && !publisher.isEmpty() && !isbn.isEmpty())
                    map.put("publisher", publisher);
                    map.put("isbn", isbn);
                }
            DBMS.insert(map,true);
            response.type("text");
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
