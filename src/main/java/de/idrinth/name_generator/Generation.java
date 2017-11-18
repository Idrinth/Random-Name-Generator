package de.idrinth.name_generator;

import de.idrinth.name_generator.creation.DataCreator;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class Generation {
    public static void main(String[] args) throws IOException {
        new Generation().run();
    }
    private void run() throws IOException {
        for(File folder:new File(getClass().getResource("/sources").getFile()).listFiles()) {
            DataCreator data = new DataCreator();
            for(File file:folder.listFiles()) {
                try {
                    for(Object name:FileUtils.readLines(file, "utf-8")) {
                        System.out.println("adding "+name);
                        data.parseString((String) name);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
            data.write(new File(getClass().getResource("/"+folder.getName()+".json").getFile()));
        }
    }
}
