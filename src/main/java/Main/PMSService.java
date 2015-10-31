package Main;

import Main.Model.Model;
import Main.handlers.GetSinglePublicationHandler;
import Main.sql2omodel.Sql2oModel;
import com.beust.jcommander.JCommander;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.logging.Logger;

import static spark.Spark.*;

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
                converters.put(Integer.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);

        /*FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setTemplateLoader(new ClassTemplateLoader(PMSService.class, "/"));
        cfg.setDefaultEncoding("UTF-8");
        freeMarkerEngine.setConfiguration(cfg);*/

        // get publication on journal (using HTTP get method)

       // get("entity/:atr/:numb/:srch", new GetSinglePublicationHandler(model));

//        post("pub", new Route() {
//            @Override
//            public Object handle(Request request, Response response) throws Exception {
//                String attribute = request.queryParams("attribute");
//                String value = request.queryParams("value");
//                new GetSinglePublicationHandler(model,attribute,value);
//                return "pub";
//            }
//        });

        post("/author", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String attribute = request.queryParams("attribute");
                String value = request.queryParams("value");
                return "author";
            }
        });

        //get("/publication/:title", new GetSinglePublicationHandler(model));

//        get("/publication/:title", (request, response) -> {
//            return "Hello: " + request.params(":title");
//        });


//        get("/mainIndex.html/alive", (req, res) -> {
//            try {
//                int entity = Integer.parseInt(req.queryParams("entity"));
//                int atr = Integer.parseInt(req.queryParams("atr"));
//                String search = req.queryParams("search");
//                if (entity == 1)  { // author
//
//                }
//                else if (entity == 2) { // publication
//
//                }
//                return "You requested a publication by " + entity + " and number="
//                        + atr;
//            } catch (Exception e) {
//                halt(502, "Error, check your parameters!");
//                return "";
//            }
//        });

        get("/mainIndex.html/alive", new GetSinglePublicationHandler(model));

        get("/alive", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "ok";
            }
        });
    }
}
