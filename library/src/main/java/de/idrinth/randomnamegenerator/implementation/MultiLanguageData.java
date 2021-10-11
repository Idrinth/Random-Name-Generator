package de.idrinth.randomnamegenerator.implementation;

import de.idrinth.randomnamegenerator.Configuration;
import de.idrinth.randomnamegenerator.DataProvider;
import de.idrinth.randomnamegenerator.NameCharacterProvider;
import de.idrinth.randomnamegenerator.NameLoader;
import java.util.HashMap;

public class MultiLanguageData implements DataProvider {
    private final HashMap<String, DataProvider> sets;

    public MultiLanguageData(NameLoader loader, String ...languages) {
        this(loader, new DefaultConfiguration(), languages);
    }
    public MultiLanguageData(NameLoader loader, Configuration config, String ...languages) {
        sets = new HashMap<>();
        sets.put("--unknown--", new Data(loader, config));
        for (String language : languages) {
            sets.put(language, new Data(loader, config, language));
        }
    }
    @Override
    public NameCharacterProvider getNext(String name) {
        String key;
        do {
            key = sets.keySet().toArray(new String[sets.size()])[(int) Math.floor(Math.random() * sets.size())];
        } while (sets.get(key).isEmpty());
        return sets.get(key).getNext(name);
    }

    @Override
    public void parseString(String name) {
        sets.get("--unknown--").parseString(name);
    }

    @Override
    public boolean isEmpty() {
        return sets.values().stream().noneMatch(list -> (!list.isEmpty()));
    }
}
