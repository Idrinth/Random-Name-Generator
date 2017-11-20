package de.idrinth.name_generator.creation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.io.FileUtils;

public class Generation {

    public void run() throws IOException, InterruptedException {
        for (File folder : new File("./sources").listFiles()) {
            System.out.println("Started folder "+folder);
            DataCreator data = new DataCreator();
            for (File file : folder.listFiles()) {
                for (Object name : FileUtils.readLines(file, "utf-8")) {
                    data.parseString((String) name);
                }
            }
            System.out.println("Added all from folder "+folder);
            data.await();
            System.out.println("Ready to write "+folder);
            File output = new File("./src/main/resources/parsed/" + folder.getName() + ".json");
            Writer writer = new FileWriter(output);
            data.write(writer);
            System.out.println("Written "+folder);
        }
    }
}
