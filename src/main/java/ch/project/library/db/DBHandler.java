package ch.project.library.db;

import java.sql.*;

public class DBHandler {
    private Connection db;
    public DBHandler(Connection database){
        db = database;
    }

    public ResultSet executeCommand(String command) {
        Statement statement;
        try {
            statement = db.createStatement();
            return statement.executeQuery(command);
        } catch (SQLException e) {
            System.out.println("A error occured while trying to communictae with the database");
            e.printStackTrace();
            return null;
        }
        
    }

    public void executeCommandUpdate(String command) {
        Statement statement;
        try {
            statement = db.createStatement();
            statement.executeUpdate(command);
        } catch (SQLException e) {
            System.out.println("A error occured while trying to communictae with the database");
            e.printStackTrace();
        }
        
    }

}
