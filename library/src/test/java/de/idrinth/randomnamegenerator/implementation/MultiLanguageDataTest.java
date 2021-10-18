package de.idrinth.randomnamegenerator.implementation;


import de.idrinth.randomnamegenerator.NameCharacterProvider;
import de.idrinth.randomnamegenerator.NoData;
import org.junit.Test;
import static org.junit.Assert.*;

public class MultiLanguageDataTest {
    @Test
    public void testThrowsOnEmptyData()
    {
        MultiLanguageData instance = new MultiLanguageData(new FirstNameLoader());
        assertThrows(NoData.class, () -> instance.getNext(""));
    }

    @Test
    public void testGetNext()
    {
        MultiLanguageData instance = new MultiLanguageData(new FirstNameLoader(), "en", "de");
        NameCharacterProvider provider = instance.getNext("");
        assertNotNull(provider);
    }
}
