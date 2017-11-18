package de.idrinth.name_generator;

import de.idrinth.name_generator.implementation.Data;
import java.util.List;

public class API {
    private final DataProvider data;

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
                System.out.println(name);
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
            data.parseString(name);
        });
    }
}
