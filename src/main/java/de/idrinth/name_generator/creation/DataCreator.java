package de.idrinth.name_generator.creation;

import de.idrinth.name_generator.implementation.Data;
import de.idrinth.name_generator.implementation.IncrementableHashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class DataCreator extends Data {
    public DataCreator() {
        super();
        //no data preloaded
        one.clear();
        two.clear();
        three.clear();
        four.clear();
        length.clear();
        starters.clear();
    }
    public void write(File output) throws IOException {
        JSONObject result = new JSONObject();
        result.append("one", listToJsonObject(one));
        result.append("two", listToJsonObject(two));
        result.append("three", listToJsonObject(three));
        result.append("four", listToJsonObject(four));
        result.append("length", listToJsonObject(length));
        result.append("starters", listToJsonObject(starters));
        result.write(new FileWriter(output)).close();
    }
    private JSONObject listToJsonObject(IncrementableHashMap map) {
        JSONObject result = new JSONObject();
        map.keySet().forEach((key) -> {
            result.put(key, map.retrieve(key).longValue());
        });
        return result;
    }
}
