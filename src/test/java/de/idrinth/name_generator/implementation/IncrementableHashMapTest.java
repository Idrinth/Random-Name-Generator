package de.idrinth.name_generator.implementation;

import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

public class IncrementableHashMapTest {
    private static final String STRING_KEY = "s";
    private static final Character CHAR_KEY = 's';
    private static final int INT_KEY = 7;

    @Test
    public void testIncrement_String_BigInteger() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(STRING_KEY, BigInteger.ONE);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(STRING_KEY, BigInteger.TEN);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrement_char_BigInteger() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(CHAR_KEY, BigInteger.ONE);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(CHAR_KEY, BigInteger.TEN);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrement_int_BigInteger() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(INT_KEY, BigInteger.ONE);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(INT_KEY, BigInteger.TEN);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrement_String_int() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(STRING_KEY, 1);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(STRING_KEY, 10);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrement_char_int() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(CHAR_KEY, 1);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(CHAR_KEY, 10);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrement_int_int() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(INT_KEY, 1);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(INT_KEY, 10);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrement_String() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(STRING_KEY);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(STRING_KEY);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.valueOf(2)) == 0);
    }

    @Test
    public void testIncrement_char() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(CHAR_KEY);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(CHAR_KEY);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.valueOf(2)) == 0);
    }

    @Test
    public void testIncrement_int() {
        System.out.println("increment");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(INT_KEY);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(INT_KEY);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.valueOf(2)) == 0);
    }
}
