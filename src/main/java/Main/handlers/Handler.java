package Main.handlers;

import DBMS.DBMS;
import Main.Answer;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class Handler implements Route {

    public Object handle(Request request, Response response) throws Exception {
        try {
            String entity = request.queryParams("entity");
            String atr = request.queryParams("attribute");
            String search = request.queryParams("search");

            Answer answer = DBMS.search(entity, atr, search);

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
