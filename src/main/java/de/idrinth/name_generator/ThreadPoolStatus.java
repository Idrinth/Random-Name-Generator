package de.idrinth.name_generator;

import java.util.concurrent.ExecutorService;

public interface ThreadPoolStatus extends ExecutorService {
    int getWaiting();
    boolean isIdle();
}
