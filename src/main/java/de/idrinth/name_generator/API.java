package de.idrinth.name_generator;

import de.idrinth.name_generator.creation.Generation;
import de.idrinth.name_generator.implementation.Data;
import de.idrinth.name_generator.implementation.MultiLanguageData;
import java.io.IOException;
import java.util.List;

public class API {

    private final DataProvider data;

    public static void main(String[] args) throws IOException, InterruptedException {
        new Generation().run();
        API api = new API();
        for(int i=0;i<1000;i++) {
            System.out.println(api.makeName());
        }
    }

    public API(String ...languages) {
        this.data = languages.length > 1 ? new MultiLanguageData(languages) : new Data(languages);
    }
    public API(boolean fill) {
        this.data = fill ? new MultiLanguageData("en", "de") : new Data();
    }

    public API(DataProvider data) {
        this.data = data;
    }

    public String makeName() {
        StringBuilder name = new StringBuilder();
        while (true) {
            String temp = data.getNext(name.toString()).get();
            if (!temp.isEmpty()) {
                name.setCharAt(0, String.valueOf(name.charAt(0)).toUpperCase().charAt(0));
                return name.toString();
            }
            name.append(temp);
        }
    }

    public String makeName(boolean multi) {
        String name = makeName();
        if(multi) {
            for(int i=1;i<4;i++) {
                if(Math.random() < Math.pow(0.1, i)) {
                    name += " " + makeName();
                }
            }
        }
        return name;
    }

    public void addName(String name) {
        data.parseString(name);
    }

    public void addNameList(List<String> names) {
        names.forEach(this::addName);
    }
    public void addNames(String ...names) {
        for (String name : names) {
            addName(name);
        }
    }
}
