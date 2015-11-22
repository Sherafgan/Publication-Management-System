package Main.handlers;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class DeleteTupleHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String tupleIdToDelete = request.queryParams("tupleID");
        //TODO implement deletion of tuple
        return null;
    }
}
