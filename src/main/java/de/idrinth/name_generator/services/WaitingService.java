package de.idrinth.name_generator.services;

import de.idrinth.name_generator.DataProvider;

public class WaitingService {
    public static void waitTillReady(DataProvider data) {
        while(!data.isReady()) {
            try {
                Thread.sleep(50+data.getRemaining());
            } catch (InterruptedException ex) {
            }
        }
    }
}
