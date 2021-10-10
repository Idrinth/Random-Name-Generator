package de.idrinth.name_generator.implementation;

import de.idrinth.name_generator.NameLoader;
import java.io.InputStream;

public class FirstNameLoader implements NameLoader
{
    public InputStream load(String language) {
        return this.getClass().getResourceAsStream("/parsed/" + language + "-first.json");
    }
}
