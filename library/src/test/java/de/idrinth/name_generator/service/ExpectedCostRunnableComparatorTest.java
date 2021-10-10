package de.idrinth.name_generator.service;

import de.idrinth.name_generator.ExpectedCostRunnable;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExpectedCostRunnableComparatorTest {
    @Test
    public void testCompare() {
        System.out.println("compare");
        ExpectedCostRunnableComparator instance = new ExpectedCostRunnableComparator();
        assertTrue(0 == instance.compare(new RunnableImpl(), new RunnableImpl()));//=
        assertTrue(0 == instance.compare(new ECRunnableImpl(8), new ECRunnableImpl(8)));// 
        assertTrue(0 > instance.compare(new ECRunnableImpl(1), new RunnableImpl()));//<
        assertTrue(0 < instance.compare(new RunnableImpl(), new ECRunnableImpl(2)));//>
        assertTrue(0 > instance.compare(new ECRunnableImpl(3), new ECRunnableImpl(7)));//<
        assertTrue(0 < instance.compare(new ECRunnableImpl(9), new ECRunnableImpl(1)));//>

        ExpectedCostRunnableComparator instance2 = new ExpectedCostRunnableComparator(false);
        assertTrue(0 == instance2.compare(new RunnableImpl(), new RunnableImpl()));
        assertTrue(0 == instance2.compare(new ECRunnableImpl(8), new ECRunnableImpl(8)));
        assertTrue(0 > instance2.compare(new ECRunnableImpl(1), new RunnableImpl()));
        assertTrue(0 < instance2.compare(new RunnableImpl(), new ECRunnableImpl(2)));
        assertTrue(0 < instance2.compare(new ECRunnableImpl(3), new ECRunnableImpl(7)));
        assertTrue(0 > instance2.compare(new ECRunnableImpl(9), new ECRunnableImpl(1)));
    }
    private class RunnableImpl implements Runnable{
        @Override
        public void run() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    private class ECRunnableImpl extends RunnableImpl implements ExpectedCostRunnable{
        private final int expectedCost;
        public ECRunnableImpl(int expectedCost) {
            this.expectedCost = expectedCost;
        }
        @Override
        public int getExpectedCost() {
            return expectedCost;
        }
    }
}
