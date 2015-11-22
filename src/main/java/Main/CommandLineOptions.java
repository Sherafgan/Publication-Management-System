package Main;

import com.beust.jcommander.Parameter;

/**
 * @author Nikita Borodulin
 * @author Sherafgan Kandov
 *         20.11.15
 */
class CommandLineOptions {

    @Parameter(names = "--debug")
    boolean debug = false;

    @Parameter(names = {"--service-port"})
    Integer servicePort = 4567;

    @Parameter(names = {"--database"})
    String database = "PMS";

    @Parameter(names = {"--db-host"})
    String dbHost = "localhost";

    @Parameter(names = {"--db-username"})
    String dbUsername = "nikitaborodulin";

    @Parameter(names = {"--db-password"})
    String dbPassword = "1234";

    @Parameter(names = {"--db-port"})
    Integer dbPort = 5432;
}