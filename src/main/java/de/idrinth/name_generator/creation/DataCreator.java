package de.idrinth.name_generator.creation;

import de.idrinth.name_generator.implementation.Data;
import de.idrinth.name_generator.implementation.IncrementableHashMap;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class DataCreator extends Data {
    public DataCreator() {
        super(false);//no data preloaded
    }
    @Override
    public void await() {
        super.await();
        exe.shutdown();
        try {
            exe.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException ex) {
            Logger.getLogger(DataCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void write(Writer output) throws IOException {
        JSONObject result = new JSONObject();
        result.put("one", listToJsonObject(one));
        result.put("two", listToJsonObject(two));
        result.put("three", listToJsonObject(three));
        result.put("four", listToJsonObject(four));
        result.put("length", listToJsonObject(length));
        result.put("starters", listToJsonObject(starters));
        result.put("count", count);
        result.write(output).close();
    }

    private JSONObject listToJsonObject(IncrementableHashMap map) {
        JSONObject result = new JSONObject();
        map.keySet().forEach((key) -> {
            result.put(key, map.retrieve(key));
        });
        return result;
    }
}
