package de.idrinth.name_generator.implementation;

import java.math.BigInteger;
import org.junit.Test;
import static org.junit.Assert.*;

public class IncrementableHashMapTest {

    private static final String STRING_KEY = "s";
    private static final Character CHAR_KEY = 's';
    private static final int INT_KEY = 7;

    @Test
    public void testIncrementStringBigInteger() {
        System.out.println("increment String BigInteger");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(STRING_KEY, BigInteger.ONE);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(STRING_KEY, BigInteger.TEN);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrementCharBigInteger() {
        System.out.println("increment char BigInteger");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(CHAR_KEY, BigInteger.ONE);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(CHAR_KEY, BigInteger.TEN);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrementIntBigInteger() {
        System.out.println("increment int BigInteger");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(INT_KEY, BigInteger.ONE);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(INT_KEY, BigInteger.TEN);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrementStringInt() {
        System.out.println("increment String int");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(STRING_KEY, 1);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(STRING_KEY, 10);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrementCharInt() {
        System.out.println("increment char int");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(CHAR_KEY, 1);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(CHAR_KEY, 10);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrementIntInt() {
        System.out.println("increment int int");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(INT_KEY, 1);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(INT_KEY, 10);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.valueOf(11)) == 0);
    }

    @Test
    public void testIncrementString() {
        System.out.println("increment String");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(STRING_KEY);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(STRING_KEY);
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.valueOf(2)) == 0);
    }

    @Test
    public void testIncrementChar() {
        System.out.println("increment char");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(CHAR_KEY);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(CHAR_KEY);
        assertTrue(instance.retrieve(CHAR_KEY).compareTo(BigInteger.valueOf(2)) == 0);
    }

    @Test
    public void testIncrementInt() {
        System.out.println("increment int");
        IncrementableHashMap instance = new IncrementableHashMap();
        instance.increment(INT_KEY);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.ONE) == 0);
        instance.increment(INT_KEY);
        assertTrue(instance.retrieve(INT_KEY).compareTo(BigInteger.valueOf(2)) == 0);
    }

    @Test
    public void testRecieveUnknown() {
        System.out.println("recieve unknown");
        IncrementableHashMap instance = new IncrementableHashMap();
        assertTrue(instance.retrieve(STRING_KEY).compareTo(BigInteger.ZERO) == 0);
    }
}
