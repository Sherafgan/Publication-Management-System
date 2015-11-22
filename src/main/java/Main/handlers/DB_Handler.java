package Main.handlers;

import DBMS.DBMS;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @author Sherafgan Kandov
 *         21.11.15
 */
public class DB_Handler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        DBMS.load();
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, String> m = request.params();
//
//            String entity = request.queryParams("entity");
//            String atr = request.queryParams("attribute");
//            String search = request.queryParams("search");
//            Map<String, String> map = new HashMap<>();
//            map.put("entity", entity);
//            map.put("atr", atr);
//            map.put("search", search);
//            Map<String, String> urlParams = Collections.unmodifiableMap(map);

            //Answer answer = DBMS.search(entity, atr, search);


            //Answer answer = process(urlParams);
            response.status(200);
            response.type("text/xml");
            response.body("OK");
            return response;
        } catch (Exception e) {
            response.status(400);
            response.body(e.getMessage());
            return e.getMessage();
        }

    }
}
