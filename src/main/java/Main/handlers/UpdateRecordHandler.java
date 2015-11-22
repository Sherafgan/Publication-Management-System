package Main.handlers;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         22.11.15
 */
public class UpdateRecordHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String updateEntity = request.queryParams("updateEntity");
        if (updateEntity.equals("1")) {
            String nameValue = request.queryParams("nameValue");
            String homepageURLValue = request.queryParams("homepageURLValue");
            //TODO: implement update of record in 'Author'-s
        } else {
            String publicationDeterminer = request.queryParams("publicationDeterminer");

            String titleValue = request.queryParams("titleValue");
            String yearValue = request.queryParams("yearValue");

            if (publicationDeterminer.equals("1")) {
                String journalValue = request.queryParams("journalValue");
                String monthValue = request.queryParams("monthValue");
                String publisherValue = request.queryParams("publisherValue");
                //TODO: implement update of record in 'Article'-s
            } else {
                String isbnValue = request.queryParams("isbnValue");
                //TODO: implement update of record in 'Book'-s
            }
        }
        return null;
    }
}
