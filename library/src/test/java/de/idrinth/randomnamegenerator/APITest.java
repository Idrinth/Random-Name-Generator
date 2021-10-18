package de.idrinth.randomnamegenerator;

import de.idrinth.randomnamegenerator.shared.BoundedCacheThreadPoolExecutor;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import static org.junit.Assert.*;

public class APITest {

    private static final String[] NAMES = "Hans\nHugo\nGerd\nAnna\nHera\nAnnabelle\nFranz\nFranziskus\nGuido\nMarkus\nMark\nAlbert\nJenny\nHolger\nFrederik\nAlexander\nAndreas\nBjörn\nManfred\nGotthelm".split("\n");

    @Test
    public void testMakeFirstName() throws InterruptedException {
        System.out.println("makeFirstName");
        API instance = new API();
        instance.addFirstNames(NAMES);
        assertFalse(instance.makeFirstName().isEmpty());
    }

    @Test
    public void testMakeLastName() throws InterruptedException {
        System.out.println("makeLastName");
        API instance = new API();
        instance.addLastNames(NAMES);
        assertFalse(instance.makeLastName().isEmpty());
    }

    @Test
    public void testMakeFullName() throws InterruptedException {
        System.out.println("makeFullName");
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
    public void testMainExecution() throws NoSuchMethodException {
        System.out.println("main");
        PrintStream out = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        API.main(new String[0]);
        System.setOut(out);
        assertFalse(outContent.toString().isEmpty());
    }

    @Test
    public void testLanguages() {
        System.out.println("main");
        assertArrayEquals(new String[]{"de", "en"}, API.languages());
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
    public void testAddFirstNamesList() {
        System.out.println("addFirstNamesList");
        DataImpl data = new DataImpl();
        API instance = new API(data,data);
        assertEquals(0, data.getAmount());
        instance.addFirstNames(NAMES);
        assertEquals(20, data.getAmount());
    }

    @Test
    public void testAddFirstNamesListList() {
        System.out.println("addFirstNamesList - List");
        DataImpl data = new DataImpl();
        API instance = new API(data,data);
        assertEquals(0, data.getAmount());
        List list = new ArrayList<String>();
        list.addAll(Arrays.asList(NAMES));
        instance.addFirstNames(list);
        assertEquals(20, data.getAmount());
    }

    @Test
    public void testAddLastNameList() {
        System.out.println("addLAstNameList");
        DataImpl data = new DataImpl();
        API instance = new API(data,data);
        assertEquals(0, data.getAmount());
        instance.addLastNames(NAMES);
        assertEquals(20, data.getAmount());
    }

    @Test
    public void testAddLastNamesListList() {
        System.out.println("addLastNamesList - List");
        DataImpl data = new DataImpl();
        API instance = new API(data,data);
        assertEquals(0, data.getAmount());
        List list = new ArrayList<String>();
        list.addAll(Arrays.asList(NAMES));
        instance.addLastNames(list);
        assertEquals(20, data.getAmount());
    }

    @Test
    public void testMakeFirstNameRandomness() throws InterruptedException {
        System.out.println("makeName - randomness check");
        IncrementList map = new IncrementList();
        API api = new API();
        ExecutorService exe = new BoundedCacheThreadPoolExecutor(5);
        for(int i=0;i<1000;i++) {
            exe.submit(new ThreaddedFirstNameGen(api, map));
        }
        exe.shutdown();
        exe.awaitTermination(5, TimeUnit.MINUTES);
        int found = map.numDuplicates();
        System.out.println("found "+found+" of 5 allowed duplicates in 1,000");
        assertTrue(found<5);
    }

    @Test
    public void testMakeLastNameRandomness() throws InterruptedException {
        System.out.println("makeName - randomness check");
        IncrementList map = new IncrementList();
        API api = new API();
        ExecutorService exe = new BoundedCacheThreadPoolExecutor(5);
        for(int i=0;i<1000;i++) {
            exe.submit(new ThreaddedLastNameGen(api, map));
        }
        exe.shutdown();
        exe.awaitTermination(5, TimeUnit.MINUTES);
        int found = map.numDuplicates();
        System.out.println("found "+found+" of 5 allowed duplicates in 1,000");
        assertTrue(found<5);
    }

    @Test
    public void testMakeFullNameRandomness() throws InterruptedException {
        System.out.println("makeFullName - randomness check");
        IncrementList map = new IncrementList();
        API api = new API();
        ExecutorService exe = new BoundedCacheThreadPoolExecutor(5);
        for(int i=0;i<1000;i++) {
            exe.submit(new ThreaddedFullNameGen(api, map));
        }
        exe.shutdown();
        exe.awaitTermination(5, TimeUnit.MINUTES);
        int found = map.numDuplicates();
        System.out.println("found "+found+" of 2 allowed duplicates in 1,000");
        assertTrue(found<2);
    }

    private class ThreaddedFirstNameGen extends ThreaddedNameGen {
        public ThreaddedFirstNameGen(API api, IncrementList map) {
            super(api, map);
        }
        @Override
        public void run() {
            map.increment(api.makeFirstName());
        }
    }

    private class ThreaddedLastNameGen extends ThreaddedNameGen {
        public ThreaddedLastNameGen(API api, IncrementList map) {
            super(api, map);
        }
        @Override
        public void run() {
            map.increment(api.makeLastName());
        }
    }

    private class ThreaddedFullNameGen extends ThreaddedNameGen {
        public ThreaddedFullNameGen(API api, IncrementList map) {
            super(api, map);
        }
        @Override
        public void run() {
            map.increment(api.makeFullName());
        }
    }

    abstract private class ThreaddedNameGen implements Runnable {
        protected final API api;
        protected volatile IncrementList map;
        public ThreaddedNameGen(API api, IncrementList map) {
            this.api = api;
            this.map = map;
        }
    }

    private class IncrementList extends ConcurrentHashMap<String, Integer> {
        public synchronized void increment(String key) {
            put(key, containsKey(key)?get(key)+1:1);
        }
        public int numDuplicates() {
            int amount = 0;
            for (int value : values()) {
                amount = amount + value - 1;
            }
            return amount;
        }
    }

    private class DataImpl implements DataProvider {

        private int amount;

        public int getAmount() {
            return amount;
        }

        @Override
        public NameCharacterProvider getNext(String name) {
            throw new UnsupportedOperationException("Not supported.");
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
