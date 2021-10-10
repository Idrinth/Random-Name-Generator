package de.idrinth.name_generator;

import java.io.InputStream;

public interface NameLoader {
    public InputStream load(String language);
}
