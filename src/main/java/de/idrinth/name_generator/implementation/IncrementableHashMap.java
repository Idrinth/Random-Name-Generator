package de.idrinth.name_generator.implementation;

import de.idrinth.name_generator.IncrementableList;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class IncrementableHashMap extends ConcurrentHashMap<String, BigInteger> implements IncrementableList {
    @Override
    public synchronized void increment(String key, BigInteger amount) {
        BigInteger now = containsKey(key) ? get(key) : BigInteger.ZERO;
        put(key, now.add(amount));
    }
    @Override
    public void increment(char key, BigInteger amount) {
        increment(String.valueOf(key), amount);
    }
    @Override
    public void increment(int key, BigInteger amount) {
        increment(String.valueOf(key), amount);
    }
    @Override
    public synchronized void increment(String key, int amount) {
        increment(key, BigInteger.valueOf(amount));
    }
    @Override
    public void increment(char key, int amount) {
        increment(String.valueOf(key), BigInteger.valueOf(amount));
    }
    @Override
    public void increment(int key, int amount) {
        increment(String.valueOf(key), BigInteger.valueOf(amount));
    }
    @Override
    public void increment(String key) {
        increment(key, BigInteger.ONE);
    }
    @Override
    public void increment(char key) {
        increment(String.valueOf(key), BigInteger.ONE);
    }
    @Override
    public void increment(int key) {
        increment(String.valueOf(key), BigInteger.ONE);
    }
    @Override
    public BigInteger retrieve(int key) {
        return retrieve(String.valueOf(key));
    }
    @Override
    public BigInteger retrieve(char key) {
        return retrieve(String.valueOf(key));
    }
    @Override
    public BigInteger retrieve(String key) {
        if(!this.containsKey(key)) {
            return BigInteger.ZERO;
        }
        return super.get(key);
    }
}