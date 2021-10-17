package de.idrinth.randomnamegenerator.plugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "source-generator", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class Generation implements org.apache.maven.plugin.Mojo {
    @Parameter(defaultValue = "${project.build.directory}", readonly = true)
    private File target;
    private void run() throws IOException, InterruptedException {
        new File(target + "/classes/parsed").mkdirs();
        FileUtils.writeStringToFile(new File(target + "/classes/languages.txt"), "", "UTF-8");
        for (File folder : new File(target + "/../sources").listFiles()) {
            boolean added = false;
            log.info("Started folder "+folder);
            for (File innerFolder : folder.listFiles()) {
                Data data = new Data();
                for (File file : innerFolder.listFiles()) {
                    for (Object name : FileUtils.readLines(file, StandardCharsets.UTF_8)) {
                        data.parseString((String) name);
                    }
                }
                log.info("Added all from folder " + innerFolder);
                data.await();
                log.info("Ready to write "+folder);
                File output = new File(target + "/classes/parsed/" + folder.getName() + "-" + innerFolder.getName() + ".json");
                Writer writer = new FileWriter(output);
                data.write(writer);
                log.info("Written "+folder);
                added = true;
            }
            if (added) {
                FileUtils.writeStringToFile(new File(target + "/classes/languages.txt"), folder.getName() + "\n", "UTF-8", true);
            }
        }
    }
    private Log log;

    @Override
    public void execute() {
        try {
            run();
        } catch (IOException|InterruptedException ex) {
            log.error(ex);
        }
    }

    @Override
    public void setLog(Log log) {
        this.log = log;
    }

    @Override
    public Log getLog() {
        return log;
    }
}
