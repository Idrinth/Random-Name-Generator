package de.idrinth.name_generator.implementation;

import de.idrinth.name_generator.Configuration;

public class DefaultConfiguration implements Configuration{
    @Override
    public int getMultiplierStarters() {
        return 10000;
    }

    @Override
    public int getMultiplierOne() {
        return 1;
    }

    @Override
    public int getMultiplierTwo() {
        return 8;
    }

    @Override
    public int getMultiplierThree() {
        return 27;
    }

    @Override
    public int getMultiplierFour() {
        return 64;
    }
}
