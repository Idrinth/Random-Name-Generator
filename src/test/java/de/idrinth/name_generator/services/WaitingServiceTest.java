package de.idrinth.name_generator.services;

import de.idrinth.name_generator.DataProvider;
import de.idrinth.name_generator.NameCharacterProvider;
import org.junit.Test;
import static org.junit.Assert.*;

public class WaitingServiceTest {

    @Test
    public void testWaitTillReady() throws InterruptedException {
        System.out.println("waitTillReady");
        assertTrue(checkDuration(0));
        assertTrue(checkDuration(5));
        assertTrue(checkDuration(10));
        assertTrue(checkDuration(15));
    }

    private boolean checkDuration(int remaining) throws InterruptedException {
        long r = 10000000;
        DataProvider data = new DataImpl(remaining);
        long duration = 0;
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            WaitingService.waitTillReady(data);
            long endTime = System.nanoTime();
            duration += (endTime - startTime);
        }
        return (duration < (50 + remaining) * r * 1.1 && duration > (50 + remaining) * r * 0.9);
    }

    private class DataImpl implements DataProvider {

        private boolean ready = false;
        private final int rem;

        public DataImpl(int rem) {
            this.rem = rem;
        }

        @Override
        public NameCharacterProvider getNext(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void parseString(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void addString(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getRemaining() {
            return rem;
        }

        @Override
        public boolean isReady() {
            ready = !ready;
            return !ready;
        }
    }
}
