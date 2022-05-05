package ch.project.library.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DBConnector {
    public DBConnector(){
    }

    public Connection getConnection(){
        try{
            Class.forName("com.oracle.jdbc");  
            return DriverManager.getConnection("jdbc:mysql://localhost:88/library_management", "user", "");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
