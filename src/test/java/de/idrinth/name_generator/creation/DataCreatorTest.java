package de.idrinth.name_generator.creation;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataCreatorTest {
    @Test
    public void testWrite() throws Exception {
        System.out.println("write");
        File output = null;
        DataCreator instance = new DataCreator();
        instance.write(output);
        fail("The test case is a prototype.");
    }
    private class WriterImpl extends Writer {

        @Override
        public void write(char[] chars, int i, int i1) throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void flush() throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void close() throws IOException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    }
}
