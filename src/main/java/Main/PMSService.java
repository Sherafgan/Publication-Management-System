package Main;

import Main.handlers.*;

import Main.handlers.GetSinglePublicationHandler;
import com.beust.jcommander.JCommander;
import com.fasterxml.jackson.databind.util.JSONPObject;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;

import Main.Model.Model;
import Main.sql2omodel.Sql2oModel;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.staticFileLocation;

import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;


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

        post("superman", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                //Object item = request.attribute("z");
                String calibri = request.queryParams("calibri");
                return "ok";
            }
        });

                get("/publication/:title", new GetSinglePublicationHandler(model));

//        get("/publication/:title", (request, response) -> {
//            return "Hello: " + request.params(":title");
//        });


        get("/publ/:type/:number", (req, res) -> {
            try {
                Integer type = Integer.parseInt(req.params(":type"));
                int number = Integer.parseInt(req.params(":number"));
                return "You requested a publication by " + type + " and number="
                        + number;
            } catch (Exception e) {
                halt(502, "Error, check your parameters!");
                return "";
            }
        });

        get("/alive", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "ok";
            }
        });
    }
}
