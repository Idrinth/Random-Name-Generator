package de.idrinth.name_generator.service;

import de.idrinth.name_generator.ExpectedCostRunnable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoundedCacheThreadPoolExecutorTest {
    @Test
    public void testGetWaiting() {
        System.out.println("getWaiting + isIdle");
        BoundedCacheThreadPoolExecutor instance = new BoundedCacheThreadPoolExecutor(1);
        assertEquals(0, instance.getWaiting());
        assertTrue(instance.isIdle());
        int max = 100 * Runtime.getRuntime().availableProcessors();
        for(int i = 0; i < max; i++) {
            instance.submit(new ExpectedCostRunnableImpl());
        }
        assertFalse(instance.isIdle());
        assertTrue(0 < instance.getWaiting());
        instance.shutdown();
    }

    @Test
    public void testSubmit() {
        System.out.println("submit");
        BoundedCacheThreadPoolExecutor instance = new BoundedCacheThreadPoolExecutor(1);
        Future result = instance.submit(new ExpectedCostRunnableImpl());
        assertTrue(Future.class.isInstance(result));
        instance.shutdown();
    }
    private class ExpectedCostRunnableImpl implements ExpectedCostRunnable {
        @Override
        public int getExpectedCost() {
            return 1;
        }
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(BoundedCacheThreadPoolExecutorTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
