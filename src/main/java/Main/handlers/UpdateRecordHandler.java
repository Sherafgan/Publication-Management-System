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
 *         22.11.15
 */
public class UpdateRecordHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, String> params = new HashMap<>();
        String updateID = request.queryParams("updateIDEntry");
        String updateEntity = request.queryParams("updateEntity");
        params.put("id",updateID);
        params.put("entity",updateEntity);
        if (updateEntity.equals("1")) {
            String name = request.queryParams("nameValue");
            String homepage = request.queryParams("homepageURLValue");
            params.put("name",name);
            params.put("homepage",homepage);
        } else {
            String publicationDeterminer = request.queryParams("publicationDeterminer");
            String title = request.queryParams("titleValue");
            String year = request.queryParams("yearValue");
            params.put("publicationDeterminer",publicationDeterminer);
            params.put("title",title);
            params.put("year",year);
            if (publicationDeterminer.equals("1")) {
                String journal = request.queryParams("journalValue");
                String month = request.queryParams("monthValue");
                params.put("journal",journal);
                params.put("month",month);

            } else {
                String publisher = request.queryParams("publisherValue");
                String isbn = request.queryParams("isbnValue");
                params.put("publisher",publisher);
                params.put("isbn",isbn);
            }
        }
        if (DBMS.update(params)) {
            response.type("text");
            response.status(200);
            response.body("OK");
            return response;
        }
        else {
            response.type("text");
            response.status(404);
            response.body("Not found");
            return response;
        }
    }
}
