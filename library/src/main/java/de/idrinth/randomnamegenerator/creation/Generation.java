package de.idrinth.name_generator.creation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;

public class Generation {
    public void run() throws IOException, InterruptedException {
        FileUtils.writeStringToFile(new File("./src/main/resources/languages.txt"), "", "UTF-8");
        for (File folder : new File("./sources").listFiles()) {
            boolean added = false;
            System.out.println("Started folder "+folder);
            for (File innerFolder : folder.listFiles()) {
                DataCreator data = new DataCreator();
                for (File file : innerFolder.listFiles()) {
                    for (Object name : FileUtils.readLines(file, StandardCharsets.UTF_8)) {
                        data.parseString((String) name);
                    }
                }
                System.out.println("Added all from folder " + innerFolder);
                data.await();
                System.out.println("Ready to write "+folder);
                File output = new File("./src/main/resources/parsed/" + folder.getName() + "-" + innerFolder.getName() + ".json");
                Writer writer = new FileWriter(output);
                data.write(writer);
                System.out.println("Written "+folder);
                added = true;
            }
            if (added) {
                FileUtils.writeStringToFile(new File("./src/main/resources/languages.txt"), folder.getName() + "\n", "UTF-8", true);
            }
        }
    }
}
