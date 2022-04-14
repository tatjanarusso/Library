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
            Class.forName("com.mysql.jdbc.Driver");  
            return DriverManager.getConnection("jdbc:mysql://localhost:88/library_management", "user", "");
        } catch(ClassNotFoundException e){
            System.out.println("fuuuuuuuuuuck");
        } catch(SQLException e){
            System.out.println("fuck sql/tatjana");
        }
        return null;
    }
}
