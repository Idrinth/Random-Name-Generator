package de.idrinth.name_generator.implementation;

import de.idrinth.name_generator.DataProvider;
import de.idrinth.name_generator.NameCharacterProvider;
import de.idrinth.name_generator.services.WaitingService;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    protected final ExecutorService exe = Executors.newCachedThreadPool();
    protected final List<Future> promises = new LinkedList<>();

    public Data() {
        this(true);
    }

    public Data(boolean loadData) {
        if (loadData) {
            addJSONtoData(this.getClass().getResourceAsStream("/parsed/en.json"));
        }
    }

    final protected void addJSONtoData(InputStream source) {
        if (source == null) {
            return;
        }
        JSONObject result = new JSONObject(source);
        SchemaLoader.load(new JSONObject(this.getClass().getResourceAsStream("/schema.json"))).validate(result);
        addDataOfJSONObject(result, "one", one);
        addDataOfJSONObject(result, "two", two);
        addDataOfJSONObject(result, "three", three);
        addDataOfJSONObject(result, "four", four);
        addDataOfJSONObject(result, "length", length);
        addDataOfJSONObject(result, "starters", starters);
    }

    private void addDataOfJSONObject(JSONObject result, String property, IncrementableHashMap map) {
        if (result.has(property)) {
            result.getJSONObject(property).keySet().forEach((key) -> {
                map.increment(key, result.getJSONObject("starters").getBigInteger(key));
            });
        }
    }

    private String getValidatedString(String input) {
        return input.trim().toLowerCase();
    }

    @Override
    public void addString(String name) {
        String input = getValidatedString(name);
        if (input.isEmpty()) {
            return;
        }
        count = count.add(BigDecimal.ONE);
        starters.increment(input.charAt(0));
        length.increment(input.length());
        //Parsing
        queue(exe.submit(new IncrementalListFiller(4, four, input)));
        queue(exe.submit(new IncrementalListFiller(3, three, input)));
        queue(exe.submit(new IncrementalListFiller(2, two, input)));
        queue(exe.submit(new IncrementalListFiller(1, one, input)));
    }

    @Override
    public synchronized void parseString(String name) {
        addString(name);
        try {
            WaitingService.waitTillReady(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public NameCharacterProvider getNext(String name) {
        NameCharacter result = new NameCharacter();
        length.keySet().stream().filter((l) -> (Integer.parseInt(l) <= name.length())).forEachOrdered((l) -> {
            result.addEndChance(BigDecimal.valueOf(length.get(l).longValue()).divide(count));
        });
        if (name.length() == 0) {
            starters.keySet().forEach((c) -> {
                result.add(c.charAt(0), starters.get(c).multiply(BigInteger.valueOf(1000)));
            });
        }
        addFromMapToResult(one,result,1,name);
        addFromMapToResult(two,result,2,name);
        addFromMapToResult(three,result,3,name);
        addFromMapToResult(four,result,4,name);
        return result;
    }
    private void addFromMapToResult(IncrementableHashMap map, NameCharacterProvider result, int length, String name) {
        if (name.length() < length-1) {
            return;
        }
        map.keySet().stream().filter((c) -> (length==1 || name.endsWith(c.substring(0, length-1)))).forEachOrdered((c) -> {
            result.add(c.charAt(length-1), map.get(c).multiply(BigInteger.TEN.pow(length-1)));
        });
    }

    @Override
    public synchronized boolean isReady() {
        try {
            promises.removeIf((Future f) -> {
                return f.isDone();
            });
            return promises.stream().noneMatch((p) -> (!p.isDone()));
        } catch (ConcurrentModificationException e) {
            return false;
        }
    }
    private synchronized void queue(Future future) {
        promises.add(future);
    }

    @Override
    public synchronized int getRemaining() {
        return promises.size();
    }

    private class IncrementalListFiller implements Runnable {

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
                queue(exe.submit(filler));
                string = string.substring(1);
            }
        }
    }
}
