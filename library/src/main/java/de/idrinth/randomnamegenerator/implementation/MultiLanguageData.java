package de.idrinth.randomnamegenerator.implementation;

import de.idrinth.randomnamegenerator.Configuration;
import de.idrinth.randomnamegenerator.DataProvider;
import de.idrinth.randomnamegenerator.NameCharacterProvider;
import de.idrinth.randomnamegenerator.NameLoader;
import de.idrinth.randomnamegenerator.NoData;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MultiLanguageData implements DataProvider {
    private final HashMap<String, DataProvider> sets;
    private final Random rand = ThreadLocalRandom.current();
    private String key;
    private static final String UNKNOWN = "--unknown--";

    public MultiLanguageData(NameLoader loader, String ...languages) {
        this(loader, new DefaultConfiguration(), languages);
    }
    public MultiLanguageData(NameLoader loader, Configuration config, String ...languages) {
        sets = new HashMap<>();
        sets.put(UNKNOWN, new Data(loader, config));
        for (String language : languages) {
            sets.put(language, new Data(loader, config, language));
        }
    }

    @Override
    public NameCharacterProvider getNext(String name) {
        if (isEmpty()) {
            throw new NoData();
        }
        if (sets.size() == 1) {
            key = UNKNOWN;
        } else if (key == null || name.isEmpty()) {
            do {
                key = sets.keySet().toArray(new String[sets.size()])[rand.nextInt(sets.size() - 1)];
            } while (sets.get(key).isEmpty());
        }
        return sets.get(key).getNext(name);
    }

    @Override
    public void parseString(String name) {
        sets.get(UNKNOWN).parseString(name);
    }

    @Override
    public boolean isEmpty() {
        return sets.values().stream().noneMatch(list -> (!list.isEmpty()));
    }
}
