package ch.project.library.db;
import java.sql.*;

public class DBCommands {
    private Connection db;

    public DBCommands(){
        DBConnector connector = new DBConnector();
        db = connector.getConnection();
    }

    public void test(){
        try{
            Statement statement = db.createStatement();
            ResultSet rs = statement.executeQuery("select * from author");
            while(rs.next()){
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
