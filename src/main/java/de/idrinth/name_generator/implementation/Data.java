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
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

public class Data implements DataProvider {
    protected final IncrementableHashMap starters = new IncrementableHashMap();

    protected final IncrementableHashMap one = new IncrementableHashMap();
    protected final IncrementableHashMap two = new IncrementableHashMap();
    protected final IncrementableHashMap three = new IncrementableHashMap();
    protected final IncrementableHashMap four = new IncrementableHashMap();

    protected final IncrementableHashMap length = new IncrementableHashMap();
    private BigDecimal count = BigDecimal.ZERO;

    protected final ExecutorService exe = Executors.newFixedThreadPool(10);
    protected final List<Future> promises = new LinkedList<>();

    public Data() {
        JSONtoData(this.getClass().getResourceAsStream("/parsed/en.json"));
    }
    final protected void JSONtoData(InputStream source) {
        if(source == null) {
            return;
        }
        JSONObject result = new JSONObject(source);
        SchemaLoader.load(new JSONObject(this.getClass().getResourceAsStream("/schema.json"))).validate(result);
        if(result.has("one")) {
            result.getJSONObject("one").keySet().forEach((key) -> {
                one.increment(key, result.getJSONObject("one").getBigInteger(key));
            });
        }
        if(result.has("two")) {
            result.getJSONObject("two").keySet().forEach((key) -> {
                two.increment(key, result.getJSONObject("two").getBigInteger(key));
            });
        }
        if(result.has("three")) {
            result.getJSONObject("three").keySet().forEach((key) -> {
                three.increment(key, result.getJSONObject("three").getBigInteger(key));
            });
        }
        if(result.has("four")) {
            result.getJSONObject("four").keySet().forEach((key) -> {
                four.increment(key, result.getJSONObject("four").getBigInteger(key));
            });
        }
        if(result.has("length")) {
            result.getJSONObject("length").keySet().forEach((key) -> {
                length.increment(key, result.getJSONObject("length").getBigInteger(key));
            });
        }
        if(result.has("starters")) {
            result.getJSONObject("starters").keySet().forEach((key) -> {
                starters.increment(key, result.getJSONObject("starters").getBigInteger(key));
            });
        }
        if(result.has("count")) {
            count = result.getBigDecimal("count");
        }
    }

    @Override
    public void addString(String name) {
        name = name.trim();
        if(name.isEmpty()) {
            return;
        }
        count = count.add(BigDecimal.ONE);
        name = name.toLowerCase();
        starters.increment(name.charAt(0));
        length.increment(name.length());
        //Parsing
        promises.add(exe.submit(new IncrementalListFiller(4,four,name)));
        promises.add(exe.submit(new IncrementalListFiller(3,three,name)));
        promises.add(exe.submit(new IncrementalListFiller(2,two,name)));
        promises.add(exe.submit(new IncrementalListFiller(1,one,name)));
    }
    @Override
    public synchronized void parseString(String name) {
        addString(name);
        WaitingService.waitTillReady(this);
    }
    @Override
    public NameCharacterProvider getNext(String name) {
        NameCharacter result = new NameCharacter();
        length.keySet().stream().filter((l) -> (Integer.parseInt(l) <= name.length())).forEachOrdered((l) -> {
            result.addEndChance(BigDecimal.valueOf(length.get(l).longValue()).divide(count));
        });
        one.keySet().forEach((c) -> {
            result.add(c.charAt(0), one.get(c));
        });
        if(name.length()==0) {
            starters.keySet().forEach((c) -> {
                result.add(c.charAt(0), starters.get(c).multiply(BigInteger.valueOf(16)));
            });
        } else {
            two.keySet().stream().filter((c) -> (name.endsWith(String.valueOf(c.charAt(0))))).forEachOrdered((c) -> {
                result.add(c.charAt(1), two.get(c).multiply(BigInteger.TEN));
            });
            if(name.length() > 1) {
                three.keySet().stream().filter((c) -> (name.endsWith(String.valueOf(c.charAt(0)+c.charAt(1))))).forEachOrdered((c) -> {
                    result.add(c.charAt(2), three.get(c).multiply(BigInteger.TEN).multiply(BigInteger.TEN));
                });
                if(name.length() > 2) {
                    four.keySet().stream().filter((c) -> (name.endsWith(String.valueOf(c.charAt(0)+c.charAt(1)+c.charAt(2))))).forEachOrdered((c) -> {
                        result.add(c.charAt(3), four.get(c).multiply(BigInteger.TEN).multiply(BigInteger.TEN).multiply(BigInteger.TEN));
                    });
                }
            }
        }
        return result;
    }

    @Override
    public synchronized boolean isReady() {
        try {
            promises.removeIf((Future f)->{return f.isDone();});
            return promises.stream().noneMatch((p) -> (!p.isDone()));
        } catch(ConcurrentModificationException e) {
            return false;
        }
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
            isInner = length==1;
        }

        @Override
        public void run() {
            if(isInner) {
                while(string.length() >= length) {
                    map.increment(string.substring(0, length));
                    string = string.substring(length);
                }
                return;
            }
            while(string.length() >= length) {
                IncrementalListFiller filler = new IncrementalListFiller(length, map, string);
                filler.isInner = true;
                promises.add(exe.submit(filler));
                string = string.substring(1);
            }
        }
    }
}
