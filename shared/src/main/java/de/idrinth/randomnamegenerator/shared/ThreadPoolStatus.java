package de.idrinth.randomnamegenerator.shared;

import java.util.concurrent.ExecutorService;

public interface ThreadPoolStatus extends ExecutorService {
    int getWaiting();
    boolean isIdle();
}
