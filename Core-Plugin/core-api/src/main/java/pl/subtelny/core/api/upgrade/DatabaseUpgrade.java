package pl.subtelny.core.api.upgrade;

public interface DatabaseUpgrade {

    void execute();

    int order();

}
