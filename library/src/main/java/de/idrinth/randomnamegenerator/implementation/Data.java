package de.idrinth.randomnamegenerator.implementation;

import de.idrinth.randomnamegenerator.Configuration;
import de.idrinth.randomnamegenerator.DataProvider;
import de.idrinth.randomnamegenerator.NameCharacterProvider;
import de.idrinth.randomnamegenerator.NameLoader;
import de.idrinth.randomnamegenerator.shared.BoundedCacheThreadPoolExecutor;
import de.idrinth.randomnamegenerator.shared.ExpectedCostRunnable;
import de.idrinth.randomnamegenerator.shared.IncrementableHashMap;
import de.idrinth.randomnamegenerator.shared.ThreadPoolStatus;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

public class Data implements DataProvider {
    protected final IncrementableHashMap starters = new IncrementableHashMap();

    protected final IncrementableHashMap one = new IncrementableHashMap();
    protected final IncrementableHashMap two = new IncrementableHashMap();
    protected final IncrementableHashMap three = new IncrementableHashMap();
    protected final IncrementableHashMap four = new IncrementableHashMap();

    protected final IncrementableHashMap length = new IncrementableHashMap();
    protected BigDecimal count = BigDecimal.ZERO;
    protected final Configuration config;

    protected final ThreadPoolStatus exe = new BoundedCacheThreadPoolExecutor(10);

    public Data() {
        this(null, new DefaultConfiguration());
    }
    public Data(Configuration config) {
        this(null, config);
    }
    public Data(NameLoader loader, String ...languages) {
        this(loader, new DefaultConfiguration(), languages);
    }
    public Data(NameLoader loader, Configuration config, String ...languages) {
        this.config = config;
        for (String language : languages) {
            addJSONtoData(loader.load(language));
        }
    }

    final protected void addJSONtoData(InputStream source) {
        if (source == null) {
            return;
        }
        JSONObject result;
        try {
            result = new JSONObject(IOUtils.toString(source,"utf-8"));
            SchemaLoader.load(new JSONObject(this.getClass().getResourceAsStream("/schema.json"))).validate(result);
        } catch(ValidationException|IOException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        addDataOfJSONObject(result, "one", one);
        addDataOfJSONObject(result, "two", two);
        addDataOfJSONObject(result, "three", three);
        addDataOfJSONObject(result, "four", four);
        addDataOfJSONObject(result, "length", length);
        addDataOfJSONObject(result, "starters", starters);
        count = count.add(result.getBigDecimal("count"));
    }

    private void addDataOfJSONObject(JSONObject result, String property, IncrementableHashMap map) {
        if (result.has(property)) {
            result.getJSONObject(property).keySet().forEach((key) -> {
                map.increment(key, result.getJSONObject(property).getBigInteger(key));
            });
        }
    }

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

    @Override
    public synchronized void parseString(String name) {
        String input = getValidatedString(name);
        if (input.isEmpty()) {
            return;
        }
        addString(input);
    }

    @Override
    public NameCharacterProvider getNext(String name) {
        NameCharacter result = new NameCharacter();
        length.keySet().stream().filter((l) -> (Integer.parseInt(l)*(1+Math.round(Math.random())) <= name.length())).forEachOrdered((l) -> {
            result.addEndChance(BigDecimal.valueOf(length.get(l).longValue()).divide(count, 30, RoundingMode.HALF_EVEN));
        });
        if (name.length() == 0) {
            starters.keySet().forEach((c) -> {
                result.add(c.charAt(0), starters.get(c).multiply(BigInteger.valueOf(config.getMultiplierStarters())));
            });
        }
        addFromMapToResult(one,result,1,name, config.getMultiplierOne());
        addFromMapToResult(two,result,2,name, config.getMultiplierTwo());
        addFromMapToResult(three,result,3,name, config.getMultiplierThree());
        addFromMapToResult(four,result,4,name, config.getMultiplierFour());
        return result;
    }
    private void addFromMapToResult(IncrementableHashMap map, NameCharacterProvider result, int length, String name, int multiplier) {
        if (name.length() < length-1) {
            return;
        }
        map.keySet().stream().filter((c) -> (length==1 || name.endsWith(c.substring(0, length-1)))).forEachOrdered((c) -> {
            result.add(
                c.charAt(length-1),
                map.get(c).multiply(BigInteger.valueOf(multiplier))
            );
        });
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

    @Override
    public boolean isEmpty() {
        return one.isEmpty() && two.isEmpty() && three.isEmpty() && four.isEmpty() && starters.isEmpty();
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
}
