package de.idrinth.randomnamegenerator.implementation;

import de.idrinth.randomnamegenerator.NameCharacterProvider;
import de.idrinth.randomnamegenerator.shared.IncrementableHashMap;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class NameCharacter implements NameCharacterProvider {

    private BigDecimal chance = BigDecimal.ZERO;
    private BigInteger total = BigInteger.ZERO;
    private final IncrementableHashMap list = new IncrementableHashMap();
    private final Random rand = ThreadLocalRandom.current();

    @Override
    public void add(Character c, BigInteger amount) {
        total = total.add(amount);
        list.increment(c, amount);
    }

    @Override
    public void addEndChance(BigDecimal chance) {
        this.chance = chance.add(this.chance);
    }

    @Override
    public String get() {
        if (total.compareTo(BigInteger.ZERO) == 0 || chance.compareTo(BigDecimal.valueOf(Math.random())) >= 0) {
            return "";//the end
        }
        BigDecimal target = BigDecimal.valueOf(rand.nextDouble()).multiply(BigDecimal.valueOf(total.longValue()));
        BigDecimal now = BigDecimal.ZERO;
        for (String key : list.keySet()) {
            now = now.add(BigDecimal.valueOf(list.get(key).longValue()));
            if (now.compareTo(target) >= 0) {
                return key;//found one
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return get();
    }
}
