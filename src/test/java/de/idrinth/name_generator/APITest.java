package de.idrinth.name_generator;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;

public class APITest {

    private static final String[] NAMES = "Hans\nHugo\nGerd\nAnna\nHera\nAnnabelle\nFranz\nFranziskus\nGuido\nMarkus\nMark\nAlbert\nJenny\nHolger\nFrederik\nAlexander\nAndreas\nBjörn\nManfred\nGotthelm".split("\n");

    @Test
    public void testMakeName() {
        System.out.println("makeName");
        API instance = new API();
        instance.addNameList(Arrays.asList(NAMES));
        for (int i = 0; i < 1000; i++) {
            assertFalse(instance.makeName().isEmpty());
        }
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
        API instance = new API(data);
        assertEquals(0, data.getAmount());
        instance.addName(NAMES[0]);
        assertEquals(1, data.getAmount());
        instance.addName(NAMES[1]);
        assertEquals(2, data.getAmount());
        instance.addName(NAMES[2]);
        assertEquals(3, data.getAmount());
    }

    @Test
    public void testAddNameList() {
        System.out.println("addNameList");
        DataImpl data = new DataImpl();
        API instance = new API(data);
        assertEquals(0, data.getAmount());
        instance.addNameList(Arrays.asList(NAMES));
        assertEquals(20, data.getAmount());
    }

    private class DataImpl implements DataProvider {

        private int amount;

        public int getAmount() {
            return amount;
        }

        @Override
        public NameCharacterProvider getNext(String name) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void parseString(String name) {
            amount++;
        }
    }
}
