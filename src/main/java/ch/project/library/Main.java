package ch.project.library;
import ch.project.library.db.*;

public class Main {
    public static void main(String args[])  
    {  
        System.out.println("Application is Startting....");
        DBCommands commands = new DBCommands();
        commands.test();
    }  
}
