package de.idrinth.name_generator.services;

import de.idrinth.name_generator.DataProvider;

public class WaitingService {

    private WaitingService() {}

    public static void waitTillReady(DataProvider data) throws InterruptedException {
        while (!data.isReady()) {
            Thread.sleep(50 + data.getRemaining());
        }
    }
}
