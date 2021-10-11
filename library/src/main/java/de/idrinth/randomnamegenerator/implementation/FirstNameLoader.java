package de.idrinth.randomnamegenerator.implementation;

import de.idrinth.randomnamegenerator.NameLoader;
import java.io.InputStream;

public class FirstNameLoader implements NameLoader
{
    public InputStream load(String language) {
        return this.getClass().getResourceAsStream("/parsed/" + language + "-first.json");
    }
}
