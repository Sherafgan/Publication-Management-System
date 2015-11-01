package Main;

import Main.Model.Model;
import Main.handlers.Handler;
import Main.sql2omodel.Sql2oModel;
import com.beust.jcommander.JCommander;
import org.sql2o.Sql2o;
import org.sql2o.quirks.PostgresQuirks;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.logging.Logger;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by nikitaborodulin on 28/10/15.
 */
public class PMSService {

    private static final Logger logger = Logger.getLogger(PMSService.class.getCanonicalName());

    public static void main(String[] args) {
        Spark.staticFileLocation("webapp");
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);

        logger.finest("Options.debug = " + options.debug);
        logger.finest("Options.database = " + options.database);
        logger.finest("Options.dbHost = " + options.dbHost);
        logger.finest("Options.dbUsername = " + options.dbUsername);
        logger.finest("Options.dbPort = " + options.dbPort);
        logger.finest("Options.servicePort = " + options.servicePort);

        //port(options.servicePort);

        Sql2o sql2o = new Sql2o("jdbc:postgresql://" + options.dbHost + ":" + options.dbPort + "/" + options.database,
                options.dbUsername, options.dbPassword, new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                //converters.put(Integer.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);


        post("/author", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String attribute = request.queryParams("attribute");
                String value = request.queryParams("value");
                return "author";
            }
        });


        get("/alive", new Handler(model));

    }
}
