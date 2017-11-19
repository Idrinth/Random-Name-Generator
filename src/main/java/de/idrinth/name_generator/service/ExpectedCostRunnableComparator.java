package de.idrinth.name_generator.service;

import de.idrinth.name_generator.ExpectedCostRunnable;
import java.util.Comparator;

public class ExpectedCostRunnableComparator implements Comparator<Runnable> {
    private final boolean lowestCostFirst;

    public ExpectedCostRunnableComparator(boolean lowestCostFirst) {
        this.lowestCostFirst = lowestCostFirst;
    }
    public ExpectedCostRunnableComparator() {
        this(true);
    }

    @Override
    public int compare(Runnable t, Runnable t1) {
        int costT = getExpectedCost(t);
        int costT1 = getExpectedCost(t1);
        if(costT == costT1) {
            return 0;
        }
        if(costT<0) {
            return 1;
        }
        if(costT1<0) {
            return -1;
        }
        return (costT - costT1)*(lowestCostFirst?1:-1);
    }
    private int getExpectedCost(Runnable t) {
        if(ExpectedCostRunnable.class.isInstance(t)) {
            return ((ExpectedCostRunnable) t).getExpectedCost();
        }
        return -1;
    }
}