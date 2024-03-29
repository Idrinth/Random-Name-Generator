package de.idrinth.randomnamegenerator;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface NameCharacterProvider {

    void add(Character c, BigInteger amount);

    void addEndChance(BigDecimal chance);

    String get();
}
