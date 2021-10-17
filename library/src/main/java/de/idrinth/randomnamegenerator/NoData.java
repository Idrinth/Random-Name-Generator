package de.idrinth.randomnamegenerator;

public class NoData extends RuntimeException
{
    public NoData() {
        super("Can't generate Names without Data.");
    }
}
