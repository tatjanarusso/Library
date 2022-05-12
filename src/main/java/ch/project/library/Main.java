package ch.project.library;
import java.io.IOException;

import ch.project.library.UI.Tui;
import ch.project.library.db.*;

public class Main {
    public static void main(String args[]) throws IOException  
    {
        Tui ui = new Tui();
        for (String string : args) {
            if(string.equals("--isAdmin")){
                ui.runAsAdmin();
            } 
        }
        if(args.length == 0) {
            ui.run();
        }
    }  
}
