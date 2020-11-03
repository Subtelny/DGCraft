package pl.subtelny.islands.event;

public interface IslandEventListener<T extends IslandEvent> {

    void handle(T event);

}
