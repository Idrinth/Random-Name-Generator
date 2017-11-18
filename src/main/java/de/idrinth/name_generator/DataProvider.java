package de.idrinth.name_generator;

public interface DataProvider {

    NameCharacterProvider getNext(String name);

    void parseString(String name);

    void addString(String name);

    int getRemaining();

    boolean isReady();
}
