package de.idrinth.randomnamegenerator;

import de.idrinth.randomnamegenerator.shared.BoundedCacheThreadPoolExecutor;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import static org.junit.Assert.*;

public class APITest {

    private static final String[] NAMES = "Hans\nHugo\nGerd\nAnna\nHera\nAnnabelle\nFranz\nFranziskus\nGuido\nMarkus\nMark\nAlbert\nJenny\nHolger\nFrederik\nAlexander\nAndreas\nBjörn\nManfred\nGotthelm".split("\n");

    @Test
    public void testMakeName() {
        System.out.println("makeName");
        API instance = new API();
        instance.addFirstNames(NAMES);
        assertFalse(instance.makeFirstName().isEmpty());
    }

    @Test
    public void testMakeMultiName() {
        System.out.println("makeName multi");
        API instance = new API();
        instance.addFirstNames(NAMES);
        instance.addLastNames(NAMES);
        assertFalse(instance.makeFullName().isEmpty());
    }

    @Test
    public void testMain() throws NoSuchMethodException {
        System.out.println("main");
        Method method = API.class.getMethod("main", String[].class);
        assertNotNull(method);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    public void testAddName() {
        System.out.println("addName");
        DataImpl data = new DataImpl();
        API instance = new API(data, data);
        assertEquals(0, data.getAmount());
        instance.addFirstName(NAMES[0]);
        assertEquals(1, data.getAmount());
        instance.addFirstName(NAMES[1]);
        assertEquals(2, data.getAmount());
        instance.addFirstName(NAMES[2]);
        assertEquals(3, data.getAmount());
    }

    @Test
    public void testAddNameList() {
        System.out.println("addNameList");
        DataImpl data = new DataImpl();
        API instance = new API(data,data);
        assertEquals(0, data.getAmount());
        instance.addFirstNames(NAMES);
        assertEquals(20, data.getAmount());
    }

    @Test
    public void testMakeNameRandomness() throws InterruptedException {
        System.out.println("makeName - randomness check");
        IncrementList map = new IncrementList();
        API api = new API();
        ExecutorService exe = new BoundedCacheThreadPoolExecutor(5);
        for(int i=0;i<1000;i++) {
            exe.submit(new ThreaddedSingleNameGen(api, map));
        }
        exe.shutdown();
        exe.awaitTermination(5, TimeUnit.MINUTES);
        int found = map.numDuplicates();
        System.out.println("found "+found+" of 5 allowed duplicates in 1,000");
        assertTrue(found<5);
        System.out.println("found "+map.getMultiNames()+" spaces of 0 allowed");
        assertTrue(map.getMultiNames() == 0);
    }

    @Test
    public void testMakeMultiNameRandomness() throws InterruptedException {
        System.out.println("makeName - multi - randomness check");
        IncrementList map = new IncrementList();
        API api = new API();
        ExecutorService exe = new BoundedCacheThreadPoolExecutor(5);
        for(int i=0;i<1000;i++) {
            exe.submit(new ThreaddedMultiNameGen(api, map));
        }
        exe.shutdown();
        exe.awaitTermination(5, TimeUnit.MINUTES);
        int found = map.numDuplicates();
        System.out.println("found "+found+" of 2 allowed duplicates in 1,000");
        assertTrue(found<2);
        System.out.println("found "+map.getMultiNames()+" multi-names of 90-150 expected");
        assertTrue(map.getMultiNames() > 89);
        assertTrue(map.getMultiNames() < 151);
    }

    private class ThreaddedSingleNameGen extends ThreaddedNameGen {
        public ThreaddedSingleNameGen(API api, IncrementList map) {
            super(api, map);
        }
        @Override
        public void run() {
            map.increment(api.makeFirstName());
        }
    }

    private class ThreaddedMultiNameGen extends ThreaddedNameGen {
        public ThreaddedMultiNameGen(API api, IncrementList map) {
            super(api, map);
        }
        @Override
        public void run() {
            String name = api.makeFullName();
            map.increment(name);
            int spaces = name.replaceAll("[^ ]","").length();
            for(int i=0;i<spaces;i++) {
                map.increment("__count");
            }
        }
    }

    abstract private class ThreaddedNameGen implements Runnable {
        protected final API api;
        protected volatile IncrementList map;
        public ThreaddedNameGen(API api, IncrementList map) {
            this.api = api;
            this.map = map;
            this.map.put("__count", 0);
        }
    }

    private class IncrementList extends ConcurrentHashMap<String, Integer> {
        public synchronized void increment(String key) {
            put(key, containsKey(key)?get(key)+1:1);
        }
        public int getMultiNames() {
            return get("__count");
        }
        public int numDuplicates() {
            int amount = -1*Math.max(0,get("__count")-1);
            return values().stream().map((i) -> i-1).reduce(amount, Integer::sum);
        }
    }

    private class DataImpl implements DataProvider {

        private int amount;

        public int getAmount() {
            return amount;
        }

        @Override
        public NameCharacterProvider getNext(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void parseString(String name) {
            amount++;
        }

        @Override
        public boolean isEmpty() {
            return amount == 0;
        }
    }
}
