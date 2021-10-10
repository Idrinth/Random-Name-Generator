package de.idrinth.randomnamegenerator.plugin;

import java.util.concurrent.ExecutorService;

public interface ThreadPoolStatus extends ExecutorService {
    int getWaiting();
    boolean isIdle();
}
