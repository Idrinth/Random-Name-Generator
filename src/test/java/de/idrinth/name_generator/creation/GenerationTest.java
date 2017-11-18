package de.idrinth.name_generator.creation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

@Ignore("too expensive to test righ now")
public class GenerationTest {

    @Test
    public void testRun() throws Exception {
        System.out.println("run");
        new Generation().run();
        fail("The test case is a prototype.");
    }
}
