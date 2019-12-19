package api.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "postgres";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/demo",
                POSTGRES_USER, POSTGRES_PASSWORD);
    }

    public void executeSQL(String SQL) {
        Connection connect = null;
        try {
            connect = connect();

            Statement statement = connect.createStatement();

            statement.execute(SQL);
            statement.close();
            statement.isClosed();
            connect.close();
            connect.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDataFromTable(String TABLE_NAME) {
        executeSQL("TRUNCATE " + TABLE_NAME + " CASCADE");
    }
}


