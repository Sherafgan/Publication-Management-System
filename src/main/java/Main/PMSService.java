package Main;

import Main.handlers.*;
import spark.Spark;

import java.util.logging.Logger;

import static spark.Spark.get;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
public class PMSService {

    private static final Logger logger = Logger.getLogger(PMSService.class.getCanonicalName());

    public static void main(String[] args) {
        Spark.staticFileLocation("webapp");

        get("/alive", new Handler());

        get("/survive", new additionHandler());

        get("/load_db", new DB_Handler());

        get("/deleteRecord", new DeleteRecordHandler());

        get("/updateRecord", new UpdateRecordHandler());
    }
}
