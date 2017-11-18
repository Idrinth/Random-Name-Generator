package de.idrinth.name_generator.creation;

import de.idrinth.name_generator.services.WaitingService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class Generation {
    public void run() throws IOException {
        for(File folder:new File(getClass().getResource("/sources").getFile()).listFiles()) {
            DataCreator data = new DataCreator();
            for(File file:folder.listFiles()) {
                try {
                    for(Object name:FileUtils.readLines(file, "utf-8")) {
                        data.addString((String) name);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
            WaitingService.waitTillReady(data);
            File output = new File(getClass().getResource("/parsed/"+folder.getName()+".json").getFile());
            Writer writer = new FileWriter(output);
            data.write(writer);
        }
    }
}
