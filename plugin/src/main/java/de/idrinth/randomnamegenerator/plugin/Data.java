package de.idrinth.randomnamegenerator.plugin;

import de.idrinth.randomnamegenerator.shared.BoundedCacheThreadPoolExecutor;
import de.idrinth.randomnamegenerator.shared.ExpectedCostRunnable;
import de.idrinth.randomnamegenerator.shared.IncrementableHashMap;
import de.idrinth.randomnamegenerator.shared.ThreadPoolStatus;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

public class Data {
    protected final IncrementableHashMap starters = new IncrementableHashMap();

    protected final IncrementableHashMap one = new IncrementableHashMap();
    protected final IncrementableHashMap two = new IncrementableHashMap();
    protected final IncrementableHashMap three = new IncrementableHashMap();
    protected final IncrementableHashMap four = new IncrementableHashMap();

    protected final IncrementableHashMap length = new IncrementableHashMap();
    protected BigDecimal count = BigDecimal.ZERO;

    protected final ThreadPoolStatus exe = new BoundedCacheThreadPoolExecutor(10);

    private String getValidatedString(String input) {
        return input.trim().toLowerCase();
    }

    private void addString(String input) {
        count = count.add(BigDecimal.ONE);
        starters.increment(input.charAt(0));
        length.increment(input.length());
        //Parsing
        exe.submit(new IncrementalListFiller(4, four, input));
        exe.submit(new IncrementalListFiller(3, three, input));
        exe.submit(new IncrementalListFiller(2, two, input));
        exe.submit(new IncrementalListFiller(1, one, input));
    }

    public synchronized void parseString(String name) {
        String input = getValidatedString(name);
        if (input.isEmpty()) {
            return;
        }
        addString(input);
    }

    public void await() {
        while(!exe.isIdle()) {
            try {
                TimeUnit.MICROSECONDS.sleep(100000+exe.getWaiting());
            } catch (InterruptedException ex) {
                Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class IncrementalListFiller implements ExpectedCostRunnable {

        private final int length;
        private final IncrementableHashMap map;
        private String string;
        private boolean isInner;

        public IncrementalListFiller(int length, IncrementableHashMap map, String string) {
            this.length = length;
            this.map = map;
            this.string = string;
            isInner = length == 1;
        }
        @Override
        public int getExpectedCost() {
            return string.length()*(isInner?length:length+1);
        }

        @Override
        public void run() {
            if (isInner) {
                while (string.length() >= length) {
                    map.increment(string.substring(0, length));
                    string = string.substring(length);
                }
                return;
            }
            while (string.length() >= length) {
                IncrementalListFiller filler = new IncrementalListFiller(length, map, string);
                filler.isInner = true;
                exe.submit(filler);
                string = string.substring(1);
            }
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

    private void addQuotedUtf8KeyChar(char c, StringBuilder sb)
    {
        if ((int) c > 128) {
            String hhhh = Integer.toHexString(c);
            sb.append("\\u").append("0000", 0, 4 - hhhh.length()).append(hhhh);
            return;
        }
        sb.append(c);
    }
    private String quoteUtf8Key(String in)
    {
        StringBuilder sb = new StringBuilder();
        for (char c : in.toCharArray()) {
            addQuotedUtf8KeyChar(c, sb);
        }
        return sb.toString();
    }
    private JSONObject listToJsonObject(IncrementableHashMap map) {
        JSONObject result = new JSONObject();
        map.keySet().forEach((key) -> {
            result.put(
                quoteUtf8Key(key),
                map.retrieve(key)
            );
        });
        return result;
    }
}
