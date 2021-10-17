package de.idrinth.randomnamegenerator.implementation;

import de.idrinth.randomnamegenerator.NameLoader;
import java.io.InputStream;

public final class LastNameLoader implements NameLoader
{
    @Override
    public InputStream load(String language) {
        return this.getClass().getResourceAsStream("/parsed/" + language + "-last.json");
    }
}
