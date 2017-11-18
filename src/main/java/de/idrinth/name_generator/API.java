package de.idrinth.name_generator;

import de.idrinth.name_generator.creation.Generation;
import de.idrinth.name_generator.implementation.Data;
import de.idrinth.name_generator.services.WaitingService;
import java.io.IOException;
import java.util.List;

public class API {
    private final DataProvider data;
    public static void main(String[] args) throws IOException {
        new Generation().run();
    }

    public API() {
        this(new Data());
    }
    public API(DataProvider data) {
        this.data = data;
    }
    public String makeName() {
        StringBuilder name = new StringBuilder();
        while(true) {
            String temp = data.getNext(name.toString()).get();
            if(temp.isEmpty()) {
                return name.toString();
            }
            name.append(temp);
        }
    }
    public void addName(String name) {
        data.parseString(name);
    }
    public void addNameList(List<String> names) {
        names.forEach((name) -> {
            data.addString(name);
        });
        WaitingService.waitTillReady(data);
    }
}
