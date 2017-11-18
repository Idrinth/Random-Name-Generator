package de.idrinth.name_generator.creation;

import java.io.IOException;
import java.io.Writer;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataCreatorTest {
    @Test
    public void testWrite() throws Exception {
        System.out.println("write");
        WriterImpl output = new WriterImpl();
        DataCreator instance = new DataCreator();
        instance.write(output);
        assertTrue(output.wasClosed);
        assertTrue(output.writtenLength > 0);
    }
    private class WriterImpl extends Writer {
        public int writtenLength = 0;
        public boolean wasClosed = false;
        @Override
        public void write(char[] chars, int i, int i1) throws IOException {
            writtenLength += chars.length;
        }

        @Override
        public void flush() throws IOException {
            //couldn't care less
        }

        @Override
        public void close() throws IOException {
            wasClosed = true;
        }
    }
}
