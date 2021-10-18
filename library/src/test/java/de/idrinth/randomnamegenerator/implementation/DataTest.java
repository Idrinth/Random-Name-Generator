package de.idrinth.randomnamegenerator.implementation;

import de.idrinth.randomnamegenerator.NoData;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataTest {

    @Test
    public void testThrowsOnEmptyData()
    {
        Data instance = new Data();
        assertThrows(NoData.class, () -> instance.getNext(""));
    }

    @Test
    public void testLoadingOfNoneExistantLanguage()
    {
        Data instance = new Data(new FirstNameLoader(), "bullshit");
        assertTrue(instance.isEmpty());
    }

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
        Data instance = new Data(new LastNameLoader(), "en");
        instance.parseString(getLongRandomString());
        assertTrue(NameCharacter.class.isInstance(instance.getNext("")));
    }

    @Test
    public void testInitialisationFromFile() {
        System.out.println("initialisation from file");
        Data instance = new Data(new LastNameLoader(), "en");
        assertFalse(instance.isEmpty());
    }
}
