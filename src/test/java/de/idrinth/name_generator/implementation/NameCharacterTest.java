package de.idrinth.name_generator.implementation;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

public class NameCharacterTest {
    @Test
    public void testAddEndChance() {
        System.out.println("all");
        NameCharacter instance = new NameCharacter();
        instance.add('s', BigInteger.ONE);
        instance.addEndChance(BigDecimal.ONE);
        assertEquals("", instance.get());
        assertEquals("", instance.toString());
        instance.addEndChance(BigDecimal.ONE.negate());
        assertEquals("s", instance.get());
        assertEquals("s", instance.toString());
        instance.add('s', BigInteger.ONE.negate());
        assertEquals("", instance.get());
        assertEquals("", instance.toString());
    }
}
