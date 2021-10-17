package de.idrinth.randomnamegenerator;

import java.io.InputStream;

public interface NameLoader {
    public InputStream load(String language);
}
