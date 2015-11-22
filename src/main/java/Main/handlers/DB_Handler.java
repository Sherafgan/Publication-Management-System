package Main.handlers;

import DBMS.DBMS;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class DB_Handler implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        DBMS.load();
        try {
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
