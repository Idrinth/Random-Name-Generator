package de.idrinth.randomnamegenerator.plugin;

import java.math.BigInteger;

public interface IncrementableList {

    void increment(String key, BigInteger amount);

    void increment(char key, BigInteger amount);

    void increment(int key, BigInteger amount);

    void increment(String key, int amount);

    void increment(char key, int amount);

    void increment(int key, int amount);

    void increment(String key);

    void increment(char key);

    void increment(int key);

    BigInteger retrieve(int key);

    BigInteger retrieve(char key);

    BigInteger retrieve(String key);

}
