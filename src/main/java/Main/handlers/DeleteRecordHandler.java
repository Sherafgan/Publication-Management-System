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
public class DeleteRecordHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String deletionEntity = request.queryParams("deletionEntity");
        String tupleIdToDelete = request.queryParams("tupleID");
        if (DBMS.delete(deletionEntity, tupleIdToDelete, true)) {
            response.type("text");
            response.status(200);
            response.body("OK");
            return response;
        } else {
            response.type("text");
            response.status(404);
            response.body("Not found");
            return response;
        }
    }
}
