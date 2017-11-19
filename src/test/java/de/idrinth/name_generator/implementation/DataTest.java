package de.idrinth.name_generator.implementation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        Data instance = new Data(false);
        instance.parseString(getLongRandomString());
        assertNotNull(instance.getNext("").get());
        assertNotEquals("", instance.getNext("").get());
    }

    @Test
    public void testGetNext() {
        System.out.println("getNext");
        Data instance = new Data(false);
        instance.parseString(getLongRandomString());
        assertTrue(NameCharacter.class.isInstance(instance.getNext("")));
    }

    @Test
    public void testInitialisationFromFile() {
        System.out.println("initialisation from file");
        DataAccess instance = new DataAccess();
        assertNotNull(instance);
        instance.getCounts().forEach((check) -> {
            assertTrue(check);
        });
    }
    private class DataAccess extends Data {
        public List<Boolean> getCounts() {
            List<Boolean> l = new ArrayList<>();
            l.add(starters.size() > 0);
            l.add(length.size() > 0);
            l.add(count.compareTo(BigDecimal.ZERO) > 0);

            l.add(one.size() > 0);
            l.add(two.size() > 0);
            l.add(three.size() > 0);

            return l;
        }
    }
}
