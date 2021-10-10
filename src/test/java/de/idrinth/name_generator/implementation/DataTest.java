package de.idrinth.name_generator.implementation;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataTest {

    private String getLongRandomString() {
        String[] letters = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzäöüß".split("");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            sb.append(letters[(int) Math.floor(Math.random() * letters.length)]);
        }
        return sb.toString();
    }

    @Test
    public void testParseString() {
        System.out.println("parseString");
        Data instance = new Data(new FirstNameLoader(), "en");
        instance.parseString(getLongRandomString());
        assertNotNull(instance.getNext("").get());
        assertNotEquals("", instance.getNext("").get());
    }

    @Test
    public void testGetNext() {
        System.out.println("getNext");
        Data instance = new Data(new FirstNameLoader(), "en");
        instance.parseString(getLongRandomString());
        assertTrue(NameCharacter.class.isInstance(instance.getNext("")));
    }

    @Test
    public void testInitialisationFromFile() {
        System.out.println("initialisation from file");
        DataAccess instance = new DataAccess();
        assertNotNull(instance);
        assertTrue(instance.hasStarter());
        assertTrue(instance.hasLength());
        assertTrue(instance.hasCount());
        assertTrue(instance.hasOne());
        assertTrue(instance.hasTwo());
        assertTrue(instance.hasThree());
        assertTrue(instance.hasFour());
    }
    private class DataAccess extends Data {
        public DataAccess() {
            super(new FirstNameLoader(), "en");
        }
        public boolean hasStarter() {
            return starters.size() > 0;
        }
        public boolean hasLength() {
            return length.size() > 0;
        }
        public boolean hasCount() {
            return count.compareTo(BigDecimal.ZERO) > 0;
        }
        public boolean hasOne() {
            return one.size() > 0;
        }
        public boolean hasTwo() {
            return two.size() > 0;
        }
        public boolean hasThree() {
            return three.size() > 0;
        }
        public boolean hasFour() {
            return four.size() > 0;
        }
    }
}
