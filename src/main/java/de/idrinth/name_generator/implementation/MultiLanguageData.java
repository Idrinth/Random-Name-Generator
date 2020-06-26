package de.idrinth.name_generator.implementation;

import de.idrinth.name_generator.DataProvider;
import de.idrinth.name_generator.NameCharacterProvider;
import java.util.HashMap;

public class MultiLanguageData implements DataProvider {
    private final HashMap<String, DataProvider> sets;
    public MultiLanguageData(String ...languages) {
        sets = new HashMap<>();
        sets.put("--unknown--", new Data());
        for (String language : languages) {
            sets.put(language, new Data(language));
        }
    }
    @Override
    public NameCharacterProvider getNext(String name) {
        String key = sets.keySet().toArray(new String[sets.size()])[(int) Math.floor(Math.random() * sets.size())];
        return sets.get(key).getNext(name);
    }

    @Override
    public void parseString(String name) {
        sets.get("--unknown--").parseString(name);
    }
}
