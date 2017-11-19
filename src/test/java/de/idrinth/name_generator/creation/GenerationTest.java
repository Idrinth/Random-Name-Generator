package de.idrinth.name_generator.creation;

import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

public class GenerationTest {
    @Test
    public void testRun() throws Exception {
        System.out.println("run");
        File file = new File("./src/main/resources/parsed/en.json");
        long pre = file.lastModified();
        new Generation().run();
        assertTrue(file.lastModified()>pre);
        assertTrue(file.length() > 0);
    }
}
