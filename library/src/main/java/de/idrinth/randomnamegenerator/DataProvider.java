package de.idrinth.randomnamegenerator;

public interface DataProvider {
    NameCharacterProvider getNext(String name);

    void parseString(String name);

    boolean isEmpty();
}
