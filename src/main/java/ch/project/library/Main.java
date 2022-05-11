package ch.project.library;
import java.io.IOException;

import ch.project.library.UI.Tui;
import ch.project.library.db.*;

public class Main {
    public static void main(String args[]) throws IOException  
    {  
        Tui ui = new Tui();
        ui.run();
    }  
}
