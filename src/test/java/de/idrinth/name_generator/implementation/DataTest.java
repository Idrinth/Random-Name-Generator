package de.idrinth.name_generator.implementation;

import org.junit.Test;
import static org.junit.Assert.*;

public class DataTest {
    private String getLongRandomString() {
        String[] letters = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzäöüß".split("");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<500;i++) {
            sb.append(letters[(int) Math.floor(Math.random()*letters.length)]);
        }
        return sb.toString();
    }

    @Test
    public void testParseString() {
        System.out.println("parseString");
        Data instance = new Data();
        instance.parseString(getLongRandomString());
        assertTrue(true);
    }

    @Test
    public void testGetNext() {
        System.out.println("getNext");
        Data instance = new Data();
        assertTrue(NameCharacter.class.isInstance(instance.getNext("")));
    }
}
