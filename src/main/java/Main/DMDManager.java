package Main;

import spark.Request;
import spark.Response;

import java.sql.*;

/**
 * Created by Darina on 15.10.2015.
 */
public class DMDManager {
    public Connection conn;
    private Statement st;
    public DMDManager() {
        try {
            String user = "nikitaborodulin";
            String passwd = "1234";
            String dbName = "PMS";
            String uri = "jdbc:postgresql://localhost:5432/" + dbName;
            conn = DriverManager.getConnection(uri,user,passwd);
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPublication(Request req, Response resp) {
        String type = req.params(":type");
        String number = req.params(":number");
        return "You requested a publication by " + type + " and number="
                + number;
    }

    public String getCategories(Request req, Response resp) {
        String request = "SELECT journal FROM article limit 10;";
        return executeQuery(request);

        /*String type = req.params(":type");
        String number = req.params(":number");
        return "You requested a publication by " + type + " and number="
                + number;*/
    }

    public String executeQuery(String query) {
        try {
            ResultSet rs = st.executeQuery(query);
            String result = "";
            while (rs.next()) {
                result += rs.getString("journal");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
