package de.idrinth.name_generator;

import de.idrinth.name_generator.creation.Generation;
import de.idrinth.name_generator.implementation.FirstNameLoader;
import de.idrinth.name_generator.implementation.LastNameLoader;
import de.idrinth.name_generator.implementation.MultiLanguageData;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class API {

    private final DataProvider firstNames;
    private final DataProvider lastNames;

    public static void main(String[] args) throws IOException, InterruptedException {
        new Generation().run();
    }
    public static String[] languages() {
        try {
            InputStream stream = API.class.getResourceAsStream("/languages.txt");
            return IOUtils.toString(stream, "UTF-8").split("\n");
        } catch (IOException ex) {
            return new String[0];
        }
    }

    public API(String ...languages) {
        this(
            new MultiLanguageData(new FirstNameLoader(), languages),
            new MultiLanguageData(new LastNameLoader(), languages)
        );
    }
    public API(DataProvider firstNames, DataProvider lastNames) {
        this.lastNames = lastNames;
        this.firstNames = firstNames;
    }
    
    private String makeName(DataProvider provider)
    {
        if (provider.isEmpty()) {
            throw new IndexOutOfBoundsException("Dataprovider is empty");
        }
        StringBuilder name = new StringBuilder();
        while (true) {
            String temp = provider.getNext(name.toString()).get();
            if (!temp.isEmpty()) {
                name.setCharAt(0, String.valueOf(name.charAt(0)).toUpperCase().charAt(0));
                return name.toString();
            }
            name.append(temp);
        }
    }

    public String makeFirstName() {
        return makeName(firstNames);
    }

    public String makeLastName() {
        return makeName(lastNames);
    }

    public String makeFullName() {
        switch ((int) Math.floor(Math.random() * 4)) {
            case 1:
                return makeFirstName() + " " + makeFirstName() + " " + makeLastName();
            case 2:
                return makeFirstName() + " " + makeFirstName() + " " + makeFirstName() + " " + makeLastName();
            case 3:
                return makeFirstName() + " " + makeFirstName() + " " + makeFirstName() + " " + makeFirstName() + " " + makeLastName();
            default:
                return makeFirstName() + " " + makeLastName();
        }
    }

    public void addFirstName(String name) {
        firstNames.parseString(name);
    }

    public void addFirstNames(List<String> names) {
        names.forEach(this::addFirstName);
    }
    public void addFirstNames(String ...names) {
        for (String name : names) {
            addFirstName(name);
        }
    }

    public void addLastName(String name) {
        lastNames.parseString(name);
    }

    public void addLastNames(List<String> names) {
        names.forEach(this::addLastName);
    }
    public void addLastNames(String ...names) {
        for (String name : names) {
            addLastName(name);
        }
    }
}
